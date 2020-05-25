package com.potato112.springservice.domain.user.context;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interceptor handling Request scope for 'UserContextService'
 */
@Component
@AllArgsConstructor
public class UserContextInterceptor implements HandlerInterceptor {

    private UserContextRequest userContextRequest;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String contextLogin = request.getHeader("UserLoginContext");

        System.out.println("ECHO context login Interceptor: " + contextLogin);

        // TODO investigate line below as eventual security issue
        if (contextLogin == null || contextLogin.equals("")) {
            return true;
        }
        System.out.println("SET user context login: " + contextLogin);
        userContextRequest.setUserLogin(contextLogin);
        return true;
    }
}
