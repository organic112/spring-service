package com.potato112.springservice.domain.user.api;


import com.potato112.springservice.domain.common.search.OffsetResponseVo;
import com.potato112.springservice.domain.user.model.authorize.UserDetailsAuthority;
import com.potato112.springservice.domain.user.model.search.UserSearchVo;
import com.potato112.springservice.domain.user.model.views.UserFormParametersVo;
import com.potato112.springservice.domain.user.model.views.UserOverviewResponseVo;
import com.potato112.springservice.domain.user.model.authorize.UserVo;
import com.potato112.springservice.domain.user.crud.CreateUserService;
import com.potato112.springservice.domain.user.crud.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

/**
 * Class stores all endpoints related to User domain
 * <p>
 * Example endpoint valid url: localhost:8081/api/v1/user/login/username=admin
 */
@RestController
@RequestMapping(value = UserApi.ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class UserApi {

    static final String ENDPOINT = "/api/v1/user";
    private final UserService userService;
    private final CreateUserService createUserService;


    @GetMapping(value = "/login/{username}")
    public UserDetailsAuthority getUserByName(@PathVariable("username") String userName) {

        System.out.println("request in user api (with param)! userName:" + userName);

        Optional<UserDetailsAuthority> user = userService.findByUserName(userName);
        return user.orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping
    public String createUser(@RequestBody @Valid UserVo userVo) {

        return createUserService.createUser(userVo);
    }

    @GetMapping
    OffsetResponseVo<UserOverviewResponseVo> getUsers(@RequestParam Map<String, String> allParams) {

        UserSearchVo userSearchVo = new UserSearchVo(allParams);
        return userService.getUsers(userSearchVo);
    }

    @GetMapping(value = "/create-parameters")
    public UserFormParametersVo getUserParameters() {

        System.out.println("CREATE PARAMETERS!");
        return createUserService.getUserParameters();
    }

    /*@PostMapping
    public String createUser(@RequestMapping @Valid UserVo user) {
        return userService.createUser(user);
    }*/

    // FIXME rest of methods implementation
    // getUsers
    // getUser
    // updateUser
    // deleteUsers
    // resetPassword
    // getUsersParameters
    // getUserProfile
    // updateUserProfile

}



