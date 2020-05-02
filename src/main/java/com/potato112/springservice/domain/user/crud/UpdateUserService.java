package com.potato112.springservice.domain.user.crud;

import com.potato112.springservice.domain.user.model.authorize.UserDto;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserService extends SaveUserService {

    public UpdateUserService(UserRepository userRepository, UserService userService) {
        super(userRepository, userService);
    }


    @Override
    public UserDto save(UserDto userDto) {
        //TODO some validation

        return super.save(userDto);
    }
}
