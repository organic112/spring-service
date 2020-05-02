package com.potato112.springservice.domain.user.crud;


import com.potato112.springservice.domain.common.search.OffsetQueryInfoVo;
import com.potato112.springservice.domain.common.search.OffsetResponseVo;
import com.potato112.springservice.domain.user.api.GroupDto;
import com.potato112.springservice.domain.user.api.GroupPermissionDto;
import com.potato112.springservice.domain.user.model.UserMapper;
import com.potato112.springservice.domain.user.model.search.UserSpecification;
import com.potato112.springservice.domain.user.model.views.UserFormParametersVo;
import com.potato112.springservice.domain.user.model.search.UserSearchVo;
import com.potato112.springservice.domain.user.model.authorize.*;
import com.potato112.springservice.domain.user.model.views.UserOverviewResponseVo;
import com.potato112.springservice.repository.entities.auth.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class DBUserService implements UserService {

    private final UserRepository userRepository;

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
    public Optional<UserVo> getUser(String id) {

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

    private UserDetailsAuthority getMockedUserAuthorityVo(){

        UserDetailsAuthority userDetailsAuthority = new UserDetailsAuthority();

        GroupPermissionDto groupPermissionVO = new GroupPermissionDto();
        groupPermissionVO.setViewName(ViewName.USER_VIEW);
        groupPermissionVO.setCanCreate(true);
        groupPermissionVO.setCanUpdate(true);
        groupPermissionVO.setCanDelete(true);

        GroupPermissionDto groupPermissionVO2 = new GroupPermissionDto();
        groupPermissionVO2.setViewName(ViewName.FOO_OVERVIEW_VIEW);
        groupPermissionVO2.setCanCreate(true);
        groupPermissionVO2.setCanUpdate(true);
        groupPermissionVO2.setCanDelete(true);

        List<GroupPermissionDto> permissionVOS = Arrays.asList(groupPermissionVO, groupPermissionVO2);

        GroupDto userGroupVO = new GroupDto();
        userGroupVO.setGroupPermissions(permissionVOS);

        UserDetailsVO userDetailsVO = new UserDetailsVO();
        userDetailsVO.setEmail("admin"); //test@email.com FIXME @ causes problem in argument
        userDetailsVO.setPassword("98ACDA0612B5263009C0E9F605F6844B8DAFF5AE");
        userDetailsVO.setFirstName("admin");
        userDetailsVO.setLastName("admin");
        userDetailsVO.setSelectedOrganizationId("aaabbbcccddd"); // FIXME
        userDetailsVO.setUserGroups(Arrays.asList(userGroupVO));

        userDetailsAuthority.setUserDetailsVO(userDetailsVO);
        log.info("Try for user login(e-mail): " + userDetailsVO.getEmail());

        return userDetailsAuthority;
    }

    @Override
    public UserVo updateUser(UserVo userDto) {
        User user = new UserMapper().mapToEntity(userDto);
        userRepository.save(user);
        return userDto;
    }

    @Override
    public UserFormParametersVo getUserFormParameters() {
        return null;
    }

    @Override
    public String generateRandomPass() {
        // FIXME
        return "TODO_RANDOM_PASS_STRING";
    }

    @Override
    public String generateHashedPass(String newPassword) {
        // FIXME
        return "TODO_RANDOM_HASHED_PASS";
    }

    // TODO add the rest of methods
}
