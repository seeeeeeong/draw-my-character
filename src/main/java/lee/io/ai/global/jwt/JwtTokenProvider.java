package lee.io.ai.global.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lee.io.ai.domain.member.entity.Member;
import lee.io.ai.global.exception.ErrorCode;
import lee.io.ai.global.exception.unauthorized.UnAuthorizedException;
import lee.io.ai.global.jwt.dto.TokenDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Component
@RequiredArgsConstructor
@PropertySource("classpath:application-oauth.yml")
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private long accessTokenTime;

    @Value("${jwt.refresh.expiration}")
    private long refreshTokenTime;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaim(accessToken);

        Long memberId = (Long) claims.get("MEMBER_ID");

        if (claims.get("ROLE") == null || !StringUtils.hasText(claims.get("ROLE").toString())) {
            throw new UnAuthorizedException(ErrorCode.UNAUTHORIZED);
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("ROLE").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(memberId, accessToken, authorities);
    }


    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            parseClaim(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            throw new UnAuthorizedException(ErrorCode.MALFORMED_JWT);
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            throw new UnAuthorizedException(ErrorCode.EXPIRED_JWT);
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            throw new UnAuthorizedException(ErrorCode.UNSUPPORTED_JWT);
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            throw new UnAuthorizedException(ErrorCode.ILLEGAL_JWT);
        }
    }

    // 인증 정보로 토큰 생성
    public TokenDto createToken(Member member) {
        Claims claims = Jwts.claims();

        claims.put("MEMBER_ID", member.getId());
        claims.put("ROLE", member.getRole());

        Date now = new Date();

        // Access Token 생성
        String accessToken = Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + accessTokenTime))
            .signWith(getSigningKey(), SignatureAlgorithm.HS512)
            .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setIssuedAt(now)
            .setExpiration(new Date(now.getTime() + refreshTokenTime))
            .signWith(getSigningKey(), SignatureAlgorithm.HS512)
            .compact();

        return TokenDto.builder()
            .memberId(member.getId())
            .grantType("Bearer")
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .expiresIn(formatExpirationTime(getExpirationTime(accessToken)))
            .build();
    }

    // 토큰 만료 시간 반환
    private Date getExpirationTime(String token) {
        Claims claims = parseClaim(token);
        return claims.getExpiration();
    }

    // 만료 시간 포맷팅
    private String formatExpirationTime(Date expirationTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        return sdf.format(expirationTime);
    }

    // Claims 파싱
    private Claims parseClaim(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    // accessToken 추출
    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(refreshToken -> refreshToken.startsWith("Bearer "))
                .map(refreshToken -> refreshToken.replace("Bearer ", ""));
    }

    // refreshToken 추출
    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(refreshHeader))
                .filter(refreshToken -> refreshToken.startsWith("Bearer "))
                .map(refreshToken -> refreshToken.replace("Bearer ", ""));
    }

    // accessToken, refreshToken 전송
    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        setAccessTokenHeader(response, accessToken);
        setRefreshTokenHeader(response, refreshToken);
    }

    // accessToken 헤더 설정
    public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
        response.setHeader(accessHeader, accessToken);
    }


    // refreshToken 헤더 설정
    public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
        response.setHeader(refreshHeader, refreshToken);
    }


    // 서명 키 반환
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
}
