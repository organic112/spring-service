package com.potato112.springservice.domain.user.model.authorize;

import lombok.Data;


@Data
public class UserDetailsAuthority {

    private UserDetailsDto userDetailsDto;
    private boolean accountNotExpired;
    private boolean accountNotLocked;
    private boolean credentialsNotExpired;
    private boolean enabled;

}
