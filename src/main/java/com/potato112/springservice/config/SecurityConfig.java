package com.potato112.springservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().antMatchers("/some_service_to_pass/path").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new PlainTextPasswordEncoder();
    }

    private static class PlainTextPasswordEncoder implements PasswordEncoder {

        @Override
        public String encode(CharSequence rawPass) {
            return rawPass.toString();
        }

        @Override
        public boolean matches(CharSequence rawPass, String encodedPass) {
            return rawPass.toString().equals(encodedPass);
        }
    }
}
