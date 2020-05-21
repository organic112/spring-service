package com.potato112.springservice.domain.user;

import com.potato112.springservice.domain.common.Validator;
import com.potato112.springservice.domain.user.crud.UserRepository;
import com.potato112.springservice.domain.user.model.authorize.UserDto;
import com.potato112.springservice.repository.entities.auth.User;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class UserValidator implements Validator<UserDto> {


    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void validate(UserDto user) {

        String validateUserId = user.getId();
        Optional<User> userOp = userRepository.findByEmail(user.getEmail());

        boolean userAlreadyExist = userOp.isPresent();

        if (userAlreadyExist) {
            User fetchedUser = userOp.get();
            if ((null == validateUserId) || (!validateUserId.equals(fetchedUser.getId()))) {
                Map<String, String> fieldErrors = Collections.singletonMap(UserDto.AttributeName.EMAIL, "Current user e-mail already exists");
                throw new SysValidationException("Validation error, provide correct data", fieldErrors);
            }
            // TODO eventual other Object validations
        }
    }
}
