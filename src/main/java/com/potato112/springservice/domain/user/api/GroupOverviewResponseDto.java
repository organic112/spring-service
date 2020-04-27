package com.potato112.springservice.domain.user.api;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

/**
 * Dto contains api response information for Groups Overview
 */
public interface GroupOverviewResponseDto {

    String getId();

    String getGroupName();

    @Value("#{{}}")
    List<String> getGroupPermissions();
    //private boolean isActive;
}
