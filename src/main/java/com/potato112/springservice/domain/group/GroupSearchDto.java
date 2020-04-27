package com.potato112.springservice.domain.group;

import com.potato112.springservice.domain.common.search.OffsetSearchDto;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

@Getter
public class GroupSearchDto extends OffsetSearchDto {

    private static final String USER_GROUP_NAME_FIELD = "groupName";


    private String groupName;


    public GroupSearchDto(Map<String, String> params) {
        super(params);
        groupName = getStringOrNull(params, USER_GROUP_NAME_FIELD);
    }

    private String getStringOrNull(Map<String, String> params, String key) {
        if (StringUtils.isNotBlank(params.get(key))) {
            return params.get(key);
        }
        return null;
    }

/*    private String checkIfUserStatusIsValidEnum(String userStatusValue) {
        if (userStatusValue != null && EnumUtils.isValidEnum(UserStatus.class, userStatusValue)) {

            return userStatusValue;
        }
        return null;
    }*/
}
