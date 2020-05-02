package com.potato112.springservice.domain.user.crud;

import com.potato112.springservice.domain.user.model.views.UserFormParametersVo;
import com.potato112.springservice.domain.user.model.authorize.UserDto;


/**
 *  Methods for Database service implementation
 */
public interface CreateUserService {

    String createUser(UserDto userDto);

    UserFormParametersVo getUserParameters();
}
