package com.potato112.springservice.domain.user.crud;

import com.potato112.springservice.domain.user.model.views.UserFormParametersVo;
import com.potato112.springservice.domain.user.model.authorize.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Slf4j
@Service
public class CreateUserDatabaseService extends SaveUserService implements CreateUserService {


    public CreateUserDatabaseService(UserRepository userRepository, UserService userService) {
        super(userRepository, userService);
    }

    @Override
    public String createUser(UserDto userDto) {

        log.info("FIXME implement -> create user in db with");
        log.info("email:"+ userDto.getEmail());
        log.info("firstName:"+ userDto.getFirstName());
        log.info("lastName:"+ userDto.getLastName());

        //TODO add user validator
        UserDto saved = save(userDto);

        System.out.println("CREATED user id: " + saved.getId());

        return saved.getId();
    }

    @Override
    public UserFormParametersVo getUserParameters() {

        // FIXME
        UserFormParametersVo vo = new UserFormParametersVo();
        vo.setAvailableRolesPerType(new HashMap<>());
        return vo;
    }
}
