package com.potato112.springservice.domain.user.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class GroupVO implements Serializable {

    private String id;
    private String groupName;
    private String parentGroupId;
    private String address;
}
