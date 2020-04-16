package com.potato112.springservice.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@ComponentScan({"com.potato112.springservice"})
public class AppConfig implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {


        System.out.println("RUNNING SPRING INTEGRATION TESTS MODE...");
    }
}
