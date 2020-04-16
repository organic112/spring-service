package com.potato112.springservice.domain.user.model;

import com.potato112.springservice.domain.user.UserStatus;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface UserOverviewResponseVo {

    String getId();
    String getEmail();
    String getFirstName();
    String getLastName();

    @Value("#{target.organizations != null ? target.organizations.split(\"&&\") : {}}")
    List<String> getOrganizations();
    String getPhone();
    UserStatus getLocked();
}
