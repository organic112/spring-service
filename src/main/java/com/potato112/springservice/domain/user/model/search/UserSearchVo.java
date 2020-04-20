package com.potato112.springservice.domain.user.model.search;

import com.potato112.springservice.domain.common.search.OffsetSearchVo;
import com.potato112.springservice.domain.user.model.authorize.UserStatus;
import lombok.Getter;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

@Getter
public class UserSearchVo extends OffsetSearchVo {


    private static final String USER_EMAIL_FIELD = "email";

    private String email;
    private String firstName;
    private String lastName;
    private String groups;
    private String phone;
    private String locked;


    public UserSearchVo(Map<String, String> params) {
        super(params);

        email = getStringOrNull(params, USER_EMAIL_FIELD);
        firstName = getStringOrNull(params, "firstName");
        lastName = getStringOrNull(params, "lastName");
        //groups = getStringOrNull(params, "groups");
        phone = getStringOrNull(params, "phone");
        String lockedValue = getStringOrNull(params, "locked");
        locked = checkIfUserStatusIsValidEnum(lockedValue);
    }

    private String getStringOrNull(Map<String, String> params, String key) {
        if (StringUtils.isNotBlank(params.get(key))) {
            return params.get(key);
        }
        return null;
    }

    private String checkIfUserStatusIsValidEnum(String userStatusValue) {
        if (userStatusValue != null && EnumUtils.isValidEnum(UserStatus.class, userStatusValue)) {

            return userStatusValue;
        }
        return null;
    }


}
