package com.knu.uniswap.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knu.uniswap.common.security.MemberUserDetailsService;
import com.knu.uniswap.common.security.handler.Http401Handler;
import com.knu.uniswap.common.security.handler.LoginFailureHandler;
import com.knu.uniswap.common.security.handler.LoginSuccessHandler;
import com.knu.uniswap.common.security.handler.LogoutSuccessHandler;
import com.knu.uniswap.member.domain.MemberRepository;
import com.knu.uniswap.common.security.filter.MemberAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;
    private final String defaultFilterProcessesUrl = "/api/members/login";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(c -> c.disable())
            .formLogin(f -> f.disable())
            .httpBasic(h -> h.disable())
            .addFilterBefore(memberAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .logout(logout -> logout
                .logoutUrl("/api/members/logout")
                .logoutSuccessHandler(new LogoutSuccessHandler(objectMapper)))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/api/members/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/emails/**").permitAll()
                .requestMatchers("/api/**").authenticated()
                .anyRequest().permitAll())
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(new Http401Handler(objectMapper)));

        return http.build();
    }

    @Bean
    public MemberAuthenticationFilter memberAuthenticationFilter() {
        MemberAuthenticationFilter filter = new MemberAuthenticationFilter(defaultFilterProcessesUrl, objectMapper);

        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(new LoginSuccessHandler(objectMapper));
        filter.setAuthenticationFailureHandler(new LoginFailureHandler(objectMapper));
        filter.setSecurityContextRepository(new HttpSessionSecurityContextRepository());

        return filter;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(provider);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new MemberUserDetailsService(memberRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
