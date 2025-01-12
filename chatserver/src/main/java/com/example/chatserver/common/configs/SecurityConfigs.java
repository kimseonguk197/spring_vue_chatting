package com.example.chatserver.common.configs;

import com.example.chatserver.common.auth.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class SecurityConfigs {
    @Autowired
    private JwtAuthFilter jwtAuthFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))  // Spring Security에서 CORS 설정 추가
                .csrf(AbstractHttpConfigurer::disable)  // CSRF 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)  // HTTP Basic 비활성화
                // 2. 요청 인가 처리 (리소스에 접근하기 전에 인증된 사용자만 접근 허용)
                .authorizeHttpRequests(authorizeRequests ->
                                authorizeRequests
//                                        .requestMatchers("/**").permitAll()
                                        .requestMatchers("/member/create",
                                                "/", "/doLogin", "/refresh-token",
                                                "/product/list",
                                                "/ws/**",
                                                "/member/reset-password").permitAll()
                                        .anyRequest().authenticated()
                )
                // 세션을 사용하지 않는 설정 추가
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
//                로그인시 사용자는 서버로부터 토큰을 발급받고,
//                매요청마다 해당 토큰을 http header 넣어 요청
//                아래 코드는 사용자로부터 받아온 토큰이 정상인지 아닌지를 검증하는 코드
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // 허용할 출처(origin)를 설정. 여기서는 로컬 개발 환경의 8081 포트를 허용
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); //Arrays.asList("*")
        configuration.setAllowedMethods(Arrays.asList("*")); // 모든 HTTP 메서드 허용
        configuration.setAllowedHeaders(Arrays.asList("*")); // 모든 헤더 허용
        configuration.setAllowCredentials(true);  // 자격 증명 허용
        // 특정 URL 패턴에 대해 위에서 설정한 CORS 정책을 등록
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 URL에 대해 CORS 설정 적용
        // 구성된 CORS 설정을 반환
        return source;
    }


}
