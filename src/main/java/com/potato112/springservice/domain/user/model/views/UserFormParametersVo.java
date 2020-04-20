package com.potato112.springservice.domain.user.model.views;

import com.potato112.springservice.domain.user.model.authorize.GroupType;
import com.potato112.springservice.domain.user.model.authorize.Roles;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class UserFormParametersVo implements Serializable {


    private Map<GroupType, List<Roles>> availableRolesPerType;

}
