package com.example.config;

import com.example.filter.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements  WebMvcConfigurer {

    @Bean
    JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }

    public void addCorsMappings(CorsRegistry registry) {
        //允许全部请求跨域
        registry.addMapping("/**");

    }

    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器
        registry.addInterceptor(jwtInterceptor()).excludePathPatterns("/login");
    }
}
