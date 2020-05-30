package com.potato112.springservice.domain.user.context;

import com.potato112.springservice.domain.user.model.authorize.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
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
    private static final String NOT_REQUEST_SCOPE_USER_NAME_ANONYMOUS = "not-request-scope-anonymous";
    private static final String REQUEST_SCOPE_USER_NAME_ANONYMOUS = "request-scope-anonymous";


    // FIXME in Tests request scope is not null but user login is null
    // service fails in unit tests

    /**
     * When request scope is available and user login is not present in throws exception
     *
     * @return
     */
    public UserContext getStrictRequestUserContext() {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();

        boolean isRequestScopeAvailable = null != requestAttributes;
        UserContext userContext;

        System.out.println("ECHO_REQUEST01 isRequestScopeAvailable:" + isRequestScopeAvailable);

        if (!isRequestScopeAvailable){
            userContext = getStrictRequestUserContext(NOT_REQUEST_SCOPE_USER_NAME_ANONYMOUS);
            return userContext;
        }

        System.out.println("requestAttributes session id: " + requestAttributes.getSessionId());
        System.out.println("requestAttributes names: " + requestAttributes.getAttributeNames(0).toString() );
        //System.out.println("requestAttributes names: " + requestAttributes.get );

        if (null == userContextRequestHolder.getUserContext()) {

            String userLogin = userContextRequest.getUserLogin();
            if (null == userLogin) {
                throw new IllegalStateException("User login is missing in user context");
            }
            userContext = getStrictRequestUserContext(userLogin);
            userContextRequestHolder.setUserContext(userContext);

        }
        return userContextRequestHolder.getUserContext();
    }

    /**
     * Allows to persist entity in application context when user is not provided i request scope
     *
     * @return
     */
    public UserContext getNotStrictRequestUserContext() {

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        boolean isRequestScopeAvailable = null != requestAttributes;
        UserContext userContext;

        if (!isRequestScopeAvailable) {
            userContext = getStrictRequestUserContext(NOT_REQUEST_SCOPE_USER_NAME_ANONYMOUS);
            return userContext;
        }

        if (null == userContextRequestHolder.getUserContext()) {

            String userLogin = userContextRequest.getUserLogin();
            if (null == userLogin) {
                return getStrictRequestUserContext(REQUEST_SCOPE_USER_NAME_ANONYMOUS);
            }
            userContext = getStrictRequestUserContext(userLogin);
            userContextRequestHolder.setUserContext(userContext);
        }
        return userContextRequestHolder.getUserContext();
    }

    private UserContext getStrictRequestUserContext(String userLogin) {

        // FIXME get user from database with all UserDto info
        // UserContext = userService.getByLogin(userLogin)

        UserDto userDto = new UserDto();
        userDto.setEmail(userLogin);

        UserContext userContext = new UserContext();
        userContext.setUserDto(userDto);
        return userContext;
    }
}
