package com.potato112.springservice.domain.user.crud;


import com.potato112.springservice.domain.user.model.UserMapper;
import com.potato112.springservice.domain.user.model.authorize.UserDto;
import com.potato112.springservice.repository.entities.auth.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * Create and update User service
 * TODO rename to: PersistUserService
 */
@Slf4j
public abstract class SaveUserService {

    protected final UserRepository userRepository;
    private UserService userService;

    public SaveUserService(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    /**
     * Create or update existing user
     */
    protected UserDto save(UserDto userDto) {

        UserMapper userMapper = new UserMapper();
        User user = userMapper.mapToEntity(userDto);

        User saved;
        if (userDto.getId() == null) {

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

        existingUserOptional.ifPresent(value -> System.out.println("Fetched existing user " + value.getId()));

        existingUserOptional.ifPresent(existingUser -> user.setPassword(existingUser.getPassword()));
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
