package com.potato112.springservice.domain.user.crud;


import com.potato112.springservice.domain.common.email.EmailService;
import com.potato112.springservice.domain.common.email.MailContent;
import com.potato112.springservice.domain.common.email.PasswordService;
import com.potato112.springservice.domain.common.email.UserMailContent;
import com.potato112.springservice.domain.common.search.OffsetQueryInfoVo;
import com.potato112.springservice.domain.common.search.OffsetResponseVo;
import com.potato112.springservice.domain.user.model.UserMapper;
import com.potato112.springservice.domain.user.model.authorize.UserDetailsAuthority;
import com.potato112.springservice.domain.user.model.authorize.UserDetailsAuthorityMapper;
import com.potato112.springservice.domain.user.model.authorize.UserDto;
import com.potato112.springservice.domain.user.model.search.UserSearchVo;
import com.potato112.springservice.domain.user.model.search.UserSpecification;
import com.potato112.springservice.domain.user.model.views.UserFormParametersVo;
import com.potato112.springservice.domain.user.model.views.UserOverviewResponseVo;
import com.potato112.springservice.repository.entities.auth.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
// @AllArgsConstructor
public class DBUserService implements UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordService passwordService;

    public DBUserService(UserRepository userRepository, EmailService emailService, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordService = passwordService;
    }

    @Override
    public Optional<UserDetailsAuthority> findByUserName(String userName) {

        Optional<User> userById = getByUserName(userName);
        if (userById.isPresent()) {
            UserDetailsAuthority vo = new UserDetailsAuthorityMapper().apply(userById.get());
            return Optional.of(vo);
        }
        return Optional.empty();
        // FIXME
        // return Optional.of(getMockedUserAuthorityVo());
    }

    private Optional<User> getByUserName(String userName) {
        Specification<User> userSpecification = UserSpecification.userByEmail(userName);
        return userRepository.findOne(userSpecification);
    }

    @Override
    public Optional<UserDto> getUser(String id) {
        return userRepository.findById(id).map(user -> new UserMapper().mapToVo(user));
    }

    @Override
    public OffsetResponseVo<UserOverviewResponseVo> getUsers(UserSearchVo searchVo) {

        Pageable pageable = searchVo.getPageable();
        //getUserGrop(UserSearchVo searchVo);
        Page<UserOverviewResponseVo> page = getUsersOverviewItems(pageable, searchVo);
        OffsetQueryInfoVo infoVo = new OffsetQueryInfoVo(pageable.getOffset(), pageable.getPageSize(), page.getTotalElements());
        return new OffsetResponseVo<>(page.getContent(), infoVo);
    }

    Page<UserOverviewResponseVo> getUsersOverviewItems(Pageable pageable, UserSearchVo searchVo) {

        //Fixme some logic related to groups
        Page<UserOverviewResponseVo> page;

//        FIXME add params
        page = userRepository.getAllUsersForOverview(
                searchVo.getEmail(),
                searchVo.getFirstName(),
                searchVo.getLastName(),
                searchVo.getGroups(),
                searchVo.getPhone(),
                searchVo.getLocked(),
                pageable);

        // page = userRepository.getAllUsersForOverview();
        return page;
    }

    @Override
    public void resetPassword(String emailAddress) {

        Optional<User> byUserName = getByUserName(emailAddress);

        if (!byUserName.isPresent()) {
            return;
        }

        String newPassword = generateRandomPass();
        String newPasswordHash = generateHashedPass(newPassword);

        User user = byUserName.get();
        user.setPassword(newPasswordHash);
        userRepository.save(user);

        MailContent mailContent = UserMailContent.builder()
                .subject("Sys Reset pass Title")
                .email(emailAddress)
                .password(newPassword)
                .build();
        emailService.sendSimpleMessage(mailContent);
    }

    @Override
    public UserFormParametersVo getUserFormParameters() {
        return null;
    }

    @Override
    public String generateRandomPass() {
        return passwordService.generateRandomPassword();
    }

    @Override
    public String generateHashedPass(String newPassword) {
        return passwordService.generateHashedPassword(newPassword);
    }
}
