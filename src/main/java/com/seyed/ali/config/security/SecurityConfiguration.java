package com.seyed.ali.config.security;

import com.seyed.ali.config.security.entrypoints.CustomBearerTokenAccessDeniedHandler;
import com.seyed.ali.config.security.entrypoints.CustomBearerTokenAuthenticationEntryPoint;
import com.seyed.ali.config.security.entrypoints.CustomHttpBasicAuthenticationEntryPoint;
import com.seyed.ali.model.entity.UserDetailsImpl;
import com.seyed.ali.repository.LogUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private @Value("${api.endpoint.base-url}") String baseUrl;

    private final CustomHttpBasicAuthenticationEntryPoint httpBasicAuthenticationEntryPoint;
    private final CustomBearerTokenAccessDeniedHandler tokenAccessDeniedHandler;
    private final CustomBearerTokenAuthenticationEntryPoint tokenAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(STR."\{this.baseUrl}/auth").permitAll()
                        .requestMatchers(antMatcher("/h2-console/**")).permitAll()
                        // Disallow everything else. Always a good idea to put this as last.
                        .anyRequest().authenticated()
                )
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable) // This is for H2 browser console access.
                )
                .httpBasic(httpBasicConfigurer -> httpBasicConfigurer
                        .authenticationEntryPoint(this.httpBasicAuthenticationEntryPoint)
                )
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer
                        .jwt(Customizer.withDefaults())
                        .authenticationEntryPoint(this.tokenAuthenticationEntryPoint)
                        .accessDeniedHandler(this.tokenAccessDeniedHandler)
                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(STATELESS)
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12); // 2^12 hashing iteration
    }

    @Bean
    public UserDetailsService userDetailsService(LogUserRepository logUserRepository) {
        return username -> logUserRepository.findByUsername(username)
                .map(UserDetailsImpl::new)
                .orElseThrow(() -> new UsernameNotFoundException(STR."user \{username} was not found!"));
    }

}
