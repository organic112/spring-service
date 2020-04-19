package com.potato112.springservice.domain.user;

import com.potato112.springservice.domain.user.model.GroupType;
import com.potato112.springservice.domain.user.model.Roles;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class UserFormParametersVo implements Serializable {


    private Map<GroupType, List<Roles>> availableRolesPerType;

}
