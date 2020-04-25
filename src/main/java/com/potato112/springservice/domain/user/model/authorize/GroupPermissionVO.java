package com.potato112.springservice.domain.user.model.authorize;

import com.potato112.springservice.repository.entities.auth.ViewName;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode
public class GroupPermissionVO {

    private String id;

    //private UserGroup userGroupId;

    private ViewName viewName;

    private boolean canCreate;

    private boolean canUpdate;

    private boolean canDelete;

}
