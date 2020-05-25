package com.potato112.springservice.domain.user.context;


import com.potato112.springservice.domain.user.model.authorize.UserDto;
import lombok.Data;

@Data
public class UserContext {

    private UserDto userDto;

    public String getContextUserLogin() {
        return userDto.getEmail();
    }
}
