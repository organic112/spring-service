package com.potato112.springservice.domain.user.api;

import com.potato112.springservice.domain.user.CreateUserService;
import com.potato112.springservice.domain.user.UserFormParametersVo;
import com.potato112.springservice.domain.user.UserRepository;
import com.potato112.springservice.domain.user.UserService;
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
    public String createUser(UserVo userVo) {

        log.info("FIXME implement -> create user in db with");
        log.info("email:"+userVo.getEmail());
        log.info("firstName:"+userVo.getFirstName());
        log.info("lastName:"+userVo.getLastName());

        //TODO add user validator
        UserVo saved = save(userVo);
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
