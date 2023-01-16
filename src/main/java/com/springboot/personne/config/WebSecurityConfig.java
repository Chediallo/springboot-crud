/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.springboot.personne.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;



/**
 *
 * @author Mohamed
 */

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .authorizeHttpRequests((authorize) -> 
                        authorize.mvcMatchers(HttpMethod.GET, "/api/**").permitAll()
                        .anyRequest().authenticated()
                ).httpBasic(Customizer.withDefaults());
        
        
        return http.build();
    }
    
    @Bean
    public UserDetailsService userDetailsService(){
        
        
        UserDetails admin = User.builder()
                .username("Mohamed")
                .password(passwordEncoder().encode("hn"))
                .roles("USER")
                .build();
        
        UserDetails user = User.builder()
                .username("Admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();
        
        return new InMemoryUserDetailsManager(admin,user);
    }
    
}
