package com.potato112.springservice.domain.user.api;


import com.potato112.springservice.domain.user.UserService;
import com.potato112.springservice.domain.user.model.UserDetailsAuthority;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(value = "/login/{username}")
    public UserDetailsAuthority getUserByName(@PathVariable ("username") String userName) {

        System.out.println("request in user api (with param)!");

        Optional<UserDetailsAuthority> user = userService.findByUserName(userName);
        return user.orElseThrow(() -> new RuntimeException("User not found"));
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



