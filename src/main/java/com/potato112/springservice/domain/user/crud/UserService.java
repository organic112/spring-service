package com.potato112.springservice.domain.user.crud;


import com.potato112.springservice.domain.common.search.OffsetResponseVo;
import com.potato112.springservice.domain.user.model.authorize.UserVo;
import com.potato112.springservice.domain.user.model.views.UserFormParametersVo;
import com.potato112.springservice.domain.user.model.search.UserSearchVo;
import com.potato112.springservice.domain.user.model.authorize.UserDetailsAuthority;
import com.potato112.springservice.domain.user.model.views.UserOverviewResponseVo;

import java.util.Optional;

public interface UserService {

    Optional<UserDetailsAuthority> findByUserName(String userName);

    //TODO other methods

    // Optional<UserVo> getUser(String id)
    // UserContext getUserContext();
    // Collection<UserVo> getAll();
    OffsetResponseVo<UserOverviewResponseVo> getUsers(UserSearchVo hasPageable);

    UserFormParametersVo getUserFormParameters();

    String generateRandomPass();

    String generateHashedPass(String newPassword);

    UserVo updateUser(UserVo userDto);

    // void deleteUsers(Set<String> ids);
    // void resetPassword(String emailAddress);
    // String generateRandomPassword();
    // String generateHashedPassword(String password);
    // UserPortfolioVo getUserProfile(String userId);
}
