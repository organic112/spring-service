package com.potato112.springservice.domain.user.api;

import com.potato112.springservice.domain.user.model.authorize.GroupPermissionVO;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Dto contains api response information for Groups Overview
 */
@Data
public class GroupOverviewResponseDto implements Serializable {

    private String id;
    private String groupName;
    private List<GroupPermissionVO> groupPermissions = new ArrayList<>();




/*
    String getId();

    String getGroupName();

    //@Value("#{{}}")
    List<String> getGroupPermissions();*/
    //private boolean isActive;
}
