package com.potato112.springservice.domain.user.api;


import com.potato112.springservice.domain.user.UserRepository;
import com.potato112.springservice.domain.user.UserService;
import com.potato112.springservice.repository.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

@Slf4j
abstract class SaveUserService {

    private UserRepository userRepository;
    private UserService userService;

    public SaveUserService(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    protected UserVo save(UserVo userVo) {

        UserMapper userMapper = new UserMapper();
        User user = userMapper.mapToEntity(userVo);

        User saved;
        if (userVo.getId() == null) {

            log.info("create new user (no id)");
            saved = createNewUser(user);
        } else {
            log.info("update user with provided Id");
            saved = updateExistingUser(user);
        }
        return userMapper.mapToVo(saved);
    }

    private User updateExistingUser(User user) {

        User saved;
        Optional<User> existingUserOptional = userRepository.findById(user.getId());
        existingUserOptional.ifPresent(existingUser -> existingUser.setPassword(existingUser.getPassword()));
        // FIXME updateGroup
        saved = userRepository.save(user);
        return saved;
    }

    private User createNewUser(User user) {

        String newPassword = StringUtils.EMPTY;

        if (StringUtils.isEmpty(user.getPassword())) {
            newPassword = userService.generateRandomPass();
            String newPassHash = userService.generateHashedPass(newPassword);
            user.setPassword(newPassHash);
        }
        User saved = userRepository.save(user);
        // FIXME sendEmail(user, newPassword);
        return saved;
    }
}
