package com.potato112.springservice.config;


import com.potato112.springservice.domain.user.context.LoginContextInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private LoginContextInterceptor loginContextInterceptor;

    public WebConfig(LoginContextInterceptor loginContextInterceptor) {
        this.loginContextInterceptor = loginContextInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(loginContextInterceptor);
    }
}

