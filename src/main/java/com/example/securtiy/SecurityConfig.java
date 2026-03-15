package com.example.securtiy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/css/**", "/js/**").permitAll() 
                .anyRequest().authenticated() 
            )
            .formLogin(form -> form
                .loginPage("/login") 
                .defaultSuccessUrl("/", true) 
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll());
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        // Aapka ID: naino | Password: naino123
        String encodedPassword = passwordEncoder().encode("vivek1234"); 

        UserDetails user = User.withUsername("vivek") 
            .password(encodedPassword)
            .roles("ADMIN")
            .build();
            
        return new InMemoryUserDetailsManager(user);
    }
}