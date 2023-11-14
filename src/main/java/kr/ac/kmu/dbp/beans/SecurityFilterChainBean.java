package kr.ac.kmu.dbp.beans;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityFilterChainBean {

    @Autowired
    private final CorsConfigurationSource corsConfigurationSource;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource);

        httpSecurity
                .formLogin()
                .loginProcessingUrl("/api/login")
                .usernameParameter("account")
                .passwordParameter("password")
                .defaultSuccessUrl("/", false);

        httpSecurity
                .logout()
                .logoutUrl("/api/logout");

        httpSecurity
                .authorizeHttpRequests()
                .anyRequest().permitAll();

        return httpSecurity.build();
    }
}
