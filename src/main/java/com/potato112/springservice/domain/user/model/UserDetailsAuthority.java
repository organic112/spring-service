package com.potato112.springservice.domain.user.model;

import lombok.Data;

@Data
public class UserDetailsAuthority {

    private UserDetailsVO userDetailsVO;
    private boolean accountNonExpired;
    private boolean credentialsNonExpired;
    private boolean enabled;

}
