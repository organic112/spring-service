package com.potato112.springservice.domain.user.api;


import com.potato112.springservice.domain.user.CreateUserService;
import com.potato112.springservice.domain.user.UserService;
import com.potato112.springservice.domain.user.model.UserDetailsAuthority;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * Example endpoint, valid url: localhost:8081/api/v1/user/login/username=admin
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

        System.out.println("request in user api (with param)!");

        Optional<UserDetailsAuthority> user = userService.findByUserName(userName);
        return user.orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PostMapping
    public String createUser(@RequestBody @Valid UserVo userVo) {

        return createUserService.createUser(userVo);
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



