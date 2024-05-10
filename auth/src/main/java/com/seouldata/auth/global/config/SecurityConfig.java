package com.seouldata.auth.global.config;

import com.seouldata.auth.global.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.httpBasic().disable()
                .cors().and()
                .csrf().disable()
                .authorizeRequests(requests -> requests
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()  // preflight 로 보내는 요청

                        // swagger 요청은 모두 허용
                        .requestMatchers("/v3/api-docs/**", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/swagger/**", "swagger-ui/**").permitAll()

                        // member 요청은 일부 허용
                        .requestMatchers(HttpMethod.GET, "/member/login/kakao").permitAll()
                        .requestMatchers(HttpMethod.GET, "/member/login/google").permitAll()
                        .requestMatchers(HttpMethod.GET, "/member/nickname").permitAll()
                        .requestMatchers(HttpMethod.GET, "/member/nickname/duplicate").permitAll()
                        .requestMatchers(HttpMethod.POST, "/member").permitAll()
                        .requestMatchers(HttpMethod.PATCH, "/member/notification").permitAll()
                        .requestMatchers(HttpMethod.GET, "/member/createInfo").permitAll()
                        .requestMatchers(HttpMethod.GET, "/member/status").permitAll()

                        // 그 외 요청은 모두 인증 필요
                        .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 사용하지 않으므로 STATELESS 설정
                )
                .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class)
                .build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://seoulsoul.site", "http://localhost:3000", "http://localhost:80")); // 허용할 origin 지정
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")); // 허용할 메서드 지정
        configuration.setAllowedHeaders(Arrays.asList("*")); // 허용할 헤더 지정
        configuration.setAllowCredentials(true); // credentials 허용 여부 설정
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}