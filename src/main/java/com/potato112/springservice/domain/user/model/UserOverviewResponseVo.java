package com.potato112.springservice.domain.user.model;

import com.potato112.springservice.domain.user.UserStatus;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface UserOverviewResponseVo {

    String getId();
    String getEmail();
    String getFirstName();
    String getLastName();

    // TODO implement getGroups();
    //@Value("#{target.organizations != null ? target.organizations.split(\"&&\") : {}}")
    @Value("#{{}}")
    List<String> getGroups();
    String getPhone();
    UserStatus getLocked();
}
