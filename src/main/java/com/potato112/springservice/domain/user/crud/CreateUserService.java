package com.potato112.springservice.domain.user.crud;

import com.potato112.springservice.domain.user.model.views.UserFormParametersVo;
import com.potato112.springservice.domain.user.model.authorize.UserVo;


/**
 *  Methods for Database service implementation
 */
public interface CreateUserService {

    String createUser(UserVo userVo);

    UserFormParametersVo getUserParameters();
}
