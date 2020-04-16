package com.potato112.springservice.domain.user;


import com.potato112.springservice.domain.user.model.UserDetailsAuthority;

import java.util.Optional;

public interface UserService {

    Optional<UserDetailsAuthority> findByUserName(String userName);

    //TODO other methods

    // Optional<UserVo> getUser(String id)
    // UserContext getUserContext();
    // Collection<UserVo> getAll();
    // OffsetResponseVo<UserOverviewResponseVo> getUsers(UserSearchVo hasPageable);
    // void deleteUsers(Set<String> ids);
    // void resetPassword(String emailAddress);
    // String generateRandomPassword();
    // String generateHashedPassword(String password);
    // UserPortfolioVo getUserProfile(String userId);
}
