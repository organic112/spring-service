package com.potato112.springservice.domain.common.email;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserMailContent implements MailContent {

    private String subject;
    private String firstAndLastName;
    private String email;
    private String password;
}
