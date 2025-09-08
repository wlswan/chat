package com.example.chat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf((csrf) -> csrf.disable());

        http.authorizeHttpRequests((auth) ->
                        auth.requestMatchers("/chat/**", "/ws/**").authenticated()
                        .anyRequest().permitAll());

        http.formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/",true)// 우리가 만든 페이지 사용
                .permitAll()
        );
        return http.build();
    }

    @Bean
    public UserDetailsService users() {
        return new InMemoryUserDetailsManager(
                User.withDefaultPasswordEncoder()
                        .username("user1")
                        .password("1234")
                        .roles("USER")
                        .build(),
                User.withDefaultPasswordEncoder()
                        .username("user2")
                        .password("1234")
                        .roles("USER")
                        .build()
        );
    }
}

