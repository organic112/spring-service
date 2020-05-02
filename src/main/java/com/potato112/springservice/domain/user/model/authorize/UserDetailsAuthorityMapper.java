package com.potato112.springservice.domain.user.model.authorize;

import com.potato112.springservice.domain.user.api.GroupDto;
import com.potato112.springservice.domain.user.model.GroupMapper;
import com.potato112.springservice.repository.entities.auth.User;
import com.potato112.springservice.repository.entities.auth.UserGroup;
import com.potato112.springservice.repository.entities.auth.UserGroupMapping;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;


public class UserDetailsAuthorityMapper implements Function<User, UserDetailsAuthority> {

    public UserDetailsAuthority apply(@NotNull User user) {

        UserDetailsAuthority userDetailsAuthority = new UserDetailsAuthority();
        userDetailsAuthority.setUserDetailsVO(mapToUserDetailsVo(user));
        userDetailsAuthority.setEnabled(UserStatus.ENABLED == user.getLocked());
        return userDetailsAuthority;
    }

    private UserDetailsVO mapToUserDetailsVo(User user) {

        UserDetailsVO userDetailsVO = new UserDetailsVO();
        userDetailsVO.setId(user.getId());
        userDetailsVO.setFirstName(user.getFirstName());
        userDetailsVO.setLastName(user.getLastName());
        userDetailsVO.setEmail(user.getEmail());
        userDetailsVO.setPassword(user.getPassword());
        List<GroupDto> userGroups = getUserGroupsVO(user);
        userDetailsVO.setUserGroups(userGroups);

        return userDetailsVO;
    }

    private List<GroupDto> getUserGroupsVO(User user) {

        // get groups
        List<UserGroup> userGroups = user.getUserGroupMappings().stream()
                .map(UserGroupMapping::getUserGroup)
                .collect(Collectors.toList());

        // map to vo
        return userGroups.stream()
                .map(group -> new GroupMapper().mapToVo(group))
                .collect(Collectors.toList());
    }
}
