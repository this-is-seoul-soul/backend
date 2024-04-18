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

                        // 그 외 요청은 모두 인증 필요
                        .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 사용하지 않으므로 STATELESS 설정
                )
                .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class)
                .build();
    }

}