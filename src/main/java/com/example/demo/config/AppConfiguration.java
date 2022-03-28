package com.example.demo.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
public class AppConfiguration {
    @Autowired
    SecurityConfiguration securityConfigurer;

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return securityConfigurer.authenticationManagerBean();
    }
}