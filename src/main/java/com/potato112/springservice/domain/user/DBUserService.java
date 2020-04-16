package com.potato112.springservice.domain.user;


import com.potato112.springservice.domain.user.model.*;
import com.potato112.springservice.repository.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

/*        Optional<User> userById = getByUserName(userName);
        if (userById.isPresent()) {
            UserDetailsAuthority vo = new UserDetailsAuthorityMapper().apply(userById.get());
            return Optional.of(vo);
        }
        return Optional.empty();*/
        // FIXME
        return Optional.of(getUserServiceMockedAuthorityByName());
    }

    // FIXME MOCKED AUTHORITIES
    private UserDetailsAuthority getUserServiceMockedAuthorityByName() {

        UserDetailsAuthority userDetailsAuthority = new UserDetailsAuthority();
        UserGroupVO userGroupVO = new UserGroupVO();
        userGroupVO.setGroupType(GroupType.OWNER);
        List<Roles> roles = Arrays.asList(Roles.ADMIN, Roles.MANAGER, Roles.USER);
        userGroupVO.setRoles(roles);
        UserDetailsVO userDetailsVO = new UserDetailsVO();
        userDetailsVO.setEmail("admin"); //test@email.com
        userDetailsVO.setPassword("98ACDA0612B5263009C0E9F605F6844B8DAFF5AE");
        userDetailsVO.setFirstName("admin");
        userDetailsVO.setLastName("admin");
        userDetailsVO.setUserGroups(Arrays.asList(userGroupVO));
        userDetailsVO.setSelectedOrganizationId("aaabbbcccddd"); // FIXME
        userDetailsAuthority.setUserDetailsVO(userDetailsVO);
        log.info("Try for user login(e-mail): " + userDetailsVO.getEmail());
        return userDetailsAuthority;
    }

/*    private Optional<User> getByUserName(String userName) {
        Specification<User> userSpecification = UserSpecification.userByEmail(userName);
        return userRepository.findOne(userSpecification);
    }*/

    // TODO add the rest of methods


}
