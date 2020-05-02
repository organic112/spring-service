package com.potato112.springservice.domain.user.model;

import com.potato112.springservice.domain.common.SysMapper;
import com.potato112.springservice.domain.user.model.authorize.UserDto;
import com.potato112.springservice.repository.entities.auth.User;

public class UserMapper implements SysMapper<User, UserDto> {

    @Override
    public UserDto mapToVo(User user) {

        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setPhone(user.getPhone());
        userDto.setLocked(user.getLocked());

        // TODO implement Grops mapping
        //List<UserGroupMapping> userGroupMappings = user.getUserGroups();

        return userDto;
    }

    @Override
    public User mapToEntity(UserDto modelVo) {

        User user = new User();
        user.setId(modelVo.getId());
        user.setFirstName(modelVo.getFirstName());
        user.setLastName(modelVo.getLastName());
        user.setEmail(modelVo.getEmail());
        user.setPassword(modelVo.getPassword()); // FIXME check when create random pass, when update existing password fetched from db
        user.setPhone(modelVo.getPhone());
        user.setLocked(modelVo.getLocked());

        // TODO implement Grops mapping
        return user;
    }

}
