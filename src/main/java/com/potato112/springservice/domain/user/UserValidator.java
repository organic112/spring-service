package com.potato112.springservice.domain.user;

import com.potato112.springservice.domain.common.Validator;
import com.potato112.springservice.domain.user.crud.UserRepository;
import com.potato112.springservice.domain.user.model.authorize.UserDto;

public class UserValidator implements Validator<UserDto> {


    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void validate(UserDto value) {

        // TODO validation logic
        // throw new SysValidationException();


    }
}
