package com.potato112.springservice.domain.user.model.authorize;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class UserGroupVO implements Serializable {

    private String id;
    private GroupType groupType;
    private List<Roles> roles = new ArrayList<>();

}
