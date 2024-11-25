package lee.io.ai.global.config;

import lee.io.ai.domain.member.service.MemberService;
import lee.io.ai.global.jwt.JwtAccessDeniedHandler;
import lee.io.ai.global.jwt.JwtAuthenticationEntryPoint;
import lee.io.ai.global.jwt.JwtAuthenticationProcessingFilter;
import lee.io.ai.global.jwt.JwtTokenProvider;
import lee.io.ai.global.jwt.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final TokenService tokenService;
    private final MemberService memberService;

    @Value("${spring.profiles.active}")
    private String profiles;

    /**
     * <p>HttpSecurity 설정</p>
     *
     * @param http HttpSecurity
     * @return SecurityFilterChain
     * @throws Exception throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        return setCsrf(http)
                // 세션을 사용하지 않음
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 요청 인증 설정
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/health_check").permitAll() // Swagger 경로 허용
                        .requestMatchers("/access").permitAll() // 토큰 발급 기능 허용
                        .requestMatchers("/login/oauth2/**").permitAll()
                        .requestMatchers("/ai/**").permitAll()
                        .requestMatchers("/characters/**").permitAll()
                        .anyRequest().authenticated() // 그 외 요청은 인증 필요
                )

                .addFilterBefore(new JwtAuthenticationProcessingFilter(jwtTokenProvider, tokenService, memberService), UsernamePasswordAuthenticationFilter.class)

                // 예외 처리 적용
                .exceptionHandling(exceptionHandling -> {
                    exceptionHandling.authenticationEntryPoint(new JwtAuthenticationEntryPoint());
                    exceptionHandling.accessDeniedHandler(new JwtAccessDeniedHandler());
                })

                .build();
    }

    private HttpSecurity setCsrf(HttpSecurity http) throws Exception {
        if ("local".equals(profiles)) { // 로컬 환경에서 CSRF 기능 제거
            http.csrf(AbstractHttpConfigurer::disable);
        }
        if ("dev".equals(profiles)) { // 개발 환경에서 CSRF 기능 제거
            http.csrf(AbstractHttpConfigurer::disable);
        }
        if ("prod".equals(profiles)) { // 운영 환경에서 SSL 적용 (https + 포트 전환)
            http.requiresChannel(requiresChannel -> requiresChannel.anyRequest().requiresSecure());
        }
        return http;
    }

    /**
     * <p>인증 관리 bean 설정</p>
     *
     * @return AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) {
        return http.getSharedObject(AuthenticationManager.class);
    }

    /**
     * <p>BCryptPasswordEncoder bean 설정</p>
     *
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * <p>RestTemplate bean 설정</p>
     *
     * @return RestTemplate
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}

