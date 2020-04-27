package com.potato112.springservice.domain.user.api;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@EqualsAndHashCode
public class GroupPermissionDto {

    private String id;

    private ViewName viewName;

    private boolean canCreate;

    private boolean canUpdate;

    private boolean canDelete;

}
