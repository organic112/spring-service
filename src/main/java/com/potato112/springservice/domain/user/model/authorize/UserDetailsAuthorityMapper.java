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
        userDetailsAuthority.setUserDetailsDto(mapToUserDetailsVo(user));
        userDetailsAuthority.setEnabled(UserStatus.ENABLED == user.getLocked());
        return userDetailsAuthority;
    }

    private UserDetailsDto mapToUserDetailsVo(User user) {

        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setId(user.getId());
        userDetailsDto.setFirstName(user.getFirstName());
        userDetailsDto.setLastName(user.getLastName());
        userDetailsDto.setEmail(user.getEmail());
        userDetailsDto.setPassword(user.getPassword());
        List<GroupDto> userGroups = getUserGroupsVO(user);
        userDetailsDto.setUserGroups(userGroups);

        return userDetailsDto;
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
