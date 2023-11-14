package kr.ac.kmu.dbp.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityFilterChainBean {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .cors().disable();

        httpSecurity
                .formLogin()
                .loginProcessingUrl("/api/login")
                .usernameParameter("account")
                .passwordParameter("password")
                .successForwardUrl("/");

        httpSecurity
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessUrl("/");

        httpSecurity
                .authorizeHttpRequests()
                .anyRequest().permitAll();

        return httpSecurity.build();
    }
}
