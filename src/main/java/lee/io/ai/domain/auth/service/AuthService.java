package lee.io.ai.domain.auth.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lee.io.ai.domain.auth.client.AuthClient;
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

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthClient authClient;
    private final JwtTokenProvider jwtTokenProvider;

    private final MemberService memberService;
    private final TokenService tokenService;

    @Transactional
    public TokenDto login(String provider, String code) throws JsonProcessingException {
        return switch (provider.toLowerCase()) {
            case "kakao" -> loginKakao(code);
            case "google" -> loginGoogle(code);
//            case "apple" -> loginApple(code);
            default -> throw new BusinessException(ErrorCode.UNSUPPORTED_LOGIN_TYPE);
        };
    }

    private TokenDto loginKakao(String code) throws JsonProcessingException {
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

    private TokenDto loginGoogle(String code) throws JsonProcessingException {
        String googleAccessToken = authClient.getGoogleAccessToken(code);
        Map<String, Object> userInfo = authClient.getGoogleUserInfo(googleAccessToken);

        String email = (String) userInfo.get("email");
        String providerUid = (String) userInfo.get("id");
        Provider provider = Provider.GOOGLE;

        Member member = memberService.getByProviderUid(providerUid, provider);

        if (member == null) {
            member = memberService.createMember(email, providerUid, provider);
        }

        TokenDto tokenDto = createToken(member);

        return tokenDto;
    }

    private TokenDto createToken(Member member) {
        TokenDto tokenDto = jwtTokenProvider.createToken(member);
        tokenService.saveRefreshToken(tokenDto.getMemberId(), tokenDto.getRefreshToken());
        return tokenDto;
    }

}
