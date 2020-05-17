package com.potato112.springservice.domain.user.context;


import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Data
@Component
@RequestScope
public class UserLoginContext {

    private UserContext userContext;
}
