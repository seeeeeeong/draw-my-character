package lee.io.ai.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lee.io.ai.domain.member.entity.Member;
import lee.io.ai.domain.member.service.MemberService;
import lee.io.ai.global.exception.unauthorized.UnAuthorizedException;
import lee.io.ai.global.jwt.dto.TokenDto;
import lee.io.ai.global.jwt.service.TokenService;
import lee.io.ai.global.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    private final TokenService tokenService;
    private final MemberService memberService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String refreshToken = jwtTokenProvider.extractRefreshToken(request)
                    .filter(jwtTokenProvider::validateToken)
                    .orElse(null);

            if (refreshToken != null) {
                checkRefreshTokenAndReIssueAccessToken(response, refreshToken);
                return;
            }

            if (refreshToken == null) {
                checkAccessTokenAndAuthentication(request, response, filterChain);
            }
        } catch (UnAuthorizedException e) {
            handleUnauthorizedException(response, e);
        }
    }

    private void checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
        Member member = memberService.getByRefreshToken(refreshToken);
        TokenDto tokenDto = tokenService.reIssueToken(member);
        jwtTokenProvider.sendAccessAndRefreshToken(response, tokenDto.getAccessToken(), tokenDto.getRefreshToken());
    }

    private void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        jwtTokenProvider.extractAccessToken(request)
                .filter(jwtTokenProvider::validateToken)
                .ifPresent(accessToken -> saveAuthentication(jwtTokenProvider.getAuthentication(accessToken)));

        filterChain.doFilter(request, response);
    }

    private void saveAuthentication(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void handleUnauthorizedException(HttpServletResponse response, UnAuthorizedException e) throws IOException {
        ApiResponse<?> apiResponse = ApiResponse.failure(e.getErrorCode());

        String jsonErrorResponse = new ObjectMapper().writeValueAsString(apiResponse);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(jsonErrorResponse);
    }
}
