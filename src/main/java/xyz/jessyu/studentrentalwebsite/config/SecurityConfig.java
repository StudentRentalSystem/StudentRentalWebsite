package xyz.jessyu.studentrentalwebsite.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import xyz.jessyu.studentrentalwebsite.service.CustomOAuth2Service;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomOAuth2Service customOAuth2Service) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/loginpage**", "/login", "/oauth2/**", "/loadPosts", "/css/**", "/static/js/**").permitAll()
                        .requestMatchers("/collect/**").authenticated()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2->oauth2
                        .loginPage("/loginpage")
                        .userInfoEndpoint(userInfo->userInfo
                                .userService(customOAuth2Service)
                        )
                        .defaultSuccessUrl("/index", true)
                        .permitAll()
                )
                // Logout
                .logout(logout -> logout
                        .logoutUrl("/logout")                     // 處理登出請求
                        .logoutSuccessUrl("/loginpage?logout")    // 登出成功導向
                        .permitAll()
                );

        // 之後要加 token 處理 /collect 相關的請求
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }

}


