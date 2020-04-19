package com.potato112.springservice.domain.user;

import com.potato112.springservice.domain.user.api.UserVo;


public interface CreateUserService {

    String createUser(UserVo userVo);

    UserFormParametersVo getUserParameters();
}
