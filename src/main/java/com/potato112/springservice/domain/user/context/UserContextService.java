package com.potato112.springservice.domain.user.context;

import com.potato112.springservice.domain.user.model.authorize.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;


/**
 * FIXME - not working
 * This service is intended to provide user context information from incoming requests.
 * When any backend operation that requires 'user information' is executed this service
 * should provide 'current user' information to feed that backend operation logic.
 */
@Service
@AllArgsConstructor
public class UserContextService {

    private final UserContextRequest userContextRequest;
    private final UserContextRequestHolder userContextRequestHolder;
    private static final String NOT_REQUEST_SCOPE_USER_NAME = "not-request-scope-user";



    // FIXME in Tests request scope is not null but user login is null
    // service fails in unit tests

    public UserContext getUserContext() {

        boolean isRequestScopeAvailable = null != RequestContextHolder.getRequestAttributes();
        UserContext userContext;

        if (!isRequestScopeAvailable){
            userContext = getUserContext(NOT_REQUEST_SCOPE_USER_NAME);
            userContextRequestHolder.setUserContext(userContext);
        }
        else if (null == userContextRequestHolder.getUserContext()) {
            String userLogin = userContextRequest.getUserLogin();

            if (null == userLogin) {
                throw new IllegalStateException("User login is missing in user context");
            }
            userContext = getUserContext(userLogin);
            userContextRequestHolder.setUserContext(userContext);
        }
        return userContextRequestHolder.getUserContext();
    }

    private UserContext getUserContext(String userLogin) {

        // FIXME get user from database with all UserDto info
        // UserContext = userService.getByLogin(userLogin)

        UserDto userDto = new UserDto();
        userDto.setEmail(userLogin);

        UserContext userContext = new UserContext();
        userContext.setUserDto(userDto);
        return userContext;
    }
}
