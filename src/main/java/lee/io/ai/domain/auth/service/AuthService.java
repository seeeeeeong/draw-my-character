package lee.io.ai.domain.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lee.io.ai.domain.auth.client.AuthClient;
import lee.io.ai.domain.auth.properties.AppleProperties;
import lee.io.ai.domain.member.entity.Member;
import lee.io.ai.domain.member.enums.Provider;
import lee.io.ai.domain.member.service.MemberService;
import lee.io.ai.global.exception.BusinessException;
import lee.io.ai.global.exception.ErrorCode;
import lee.io.ai.global.jwt.JwtTokenProvider;
import lee.io.ai.global.jwt.dto.TokenDto;
import lee.io.ai.global.jwt.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthClient authClient;
    private final JwtTokenProvider jwtTokenProvider;
    private final AppleProperties appleProperties;

    private final MemberService memberService;
    private final TokenService tokenService;

    @Transactional
    public TokenDto login(String provider, String code) throws ParseException, IOException, JOSEException {
        return switch (provider.toLowerCase()) {
            case "kakao" -> loginKakao(code);
            case "google" -> loginGoogle(code);
            case "apple" -> loginApple(code);
            default -> throw new BusinessException(ErrorCode.UNSUPPORTED_LOGIN_TYPE);
        };
    }

    private TokenDto loginKakao(String code) {
        String kakaoAccessToken = authClient.getKakaoAccessToken(code);
        Map<String, Object> userInfo = authClient.getKakaoUserInfo(kakaoAccessToken);

        String email = (String) userInfo.get("email");
        String providerUid = (String) userInfo.get("id");
        Provider provider = Provider.KAKAO;

        Member member = memberService.getByProviderUid(providerUid, provider);

        if (member == null) {
            member = memberService.createMember(email, providerUid, provider);
        }

        TokenDto tokenDto = createToken(member);
        return tokenDto;
    }

    private TokenDto loginGoogle(String code)  {
        String googleAccessToken = authClient.getGoogleAccessToken(code);
        Map<String, Object> userInfo = authClient.getGoogleUserInfo(googleAccessToken);

        String email = (String) userInfo.get("email");
        String providerUid = (String) userInfo.get("sub");
        Provider provider = Provider.GOOGLE;

        Member member = memberService.getByProviderUid(providerUid, provider);

        if (member == null) {
            member = memberService.createMember(email, providerUid, provider);
        }

        TokenDto tokenDto = createToken(member);
        return tokenDto;
    }

    private TokenDto loginApple(String code) throws ParseException, IOException, JOSEException {
        String appleIdToken = authClient.getAppleIdToken(code);

        if (!validateAppleIdToken(appleIdToken)) {
            throw new RuntimeException();
        }

        SignedJWT signedJWT = SignedJWT.parse(appleIdToken);
        JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
        String email = claims.getStringClaim("email");
        String providerUid = claims.getSubject();
        Provider provider = Provider.APPLE;

        Member member = memberService.getByProviderUid(providerUid, provider);

        if (member == null) {
            member = memberService.createMember(email, providerUid, provider);
        }

        TokenDto tokenDto = createToken(member);
        return tokenDto;
    }

    private boolean validateAppleIdToken(String idToken) throws ParseException, JOSEException, IOException {
        SignedJWT signedJWT = SignedJWT.parse(idToken);
        // 1. Apple의 공개 키 조회
        JWKSet jwkSet = JWKSet.load(new URL(appleProperties.getPublicKeyUrl()));
        JWK jwk = jwkSet.getKeyByKeyId(signedJWT.getHeader().getKeyID());
        // 2. 검증을 위한 RSA 공개 키 생성E
        RSAKey rsaKey = (RSAKey) jwk;
        JWSVerifier verifier = new RSASSAVerifier(rsaKey.toRSAPublicKey());
        // 3. JWT 서명 검증
        return signedJWT.verify(verifier);
    }

    private TokenDto createToken(Member member) {
        TokenDto tokenDto = jwtTokenProvider.createToken(member);
        tokenService.saveRefreshToken(tokenDto.getMemberId(), tokenDto.getRefreshToken());
        return tokenDto;
    }

}
