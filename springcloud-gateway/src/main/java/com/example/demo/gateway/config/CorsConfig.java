package com.example.demo.gateway.config;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.setAllowCredentials(true); //允许cookies跨域
        corsConfiguration.setAllowedOrigins(Arrays.asList("*")); //允许的域 上线后可以改成域名
        corsConfiguration.setAllowedHeaders(Arrays.asList("*")); //允许的头
        corsConfiguration.setAllowedMethods(Arrays.asList("*")); //允许的头
        corsConfiguration.setMaxAge(300L); //超时时间

        source.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter();
    }
}
