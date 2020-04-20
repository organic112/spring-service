package com.potato112.springservice.domain.user.model.authorize;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
// import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
public class UserVo {

    public static final class AttributeName {

        private AttributeName() {
        }

        public static final String ID = "id";
        public static final String EMAIL = "email";
        public static final String FIRST_NAME = "firstName";
        public static final String LAST_NAME = "lastName";
        public static final String USER_GROUPS = "userGroups";
        public static final String PHONE = "phone";
        public static final String LOCKED = "locked";
    }

    private String id;
    @Size(max = 60)
    @Email(message = "provide valid email")
    @NotEmpty(message = "Email cannot be empty")
    private String email;

    @NotEmpty(message = "first name should not be empty")
    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Valid
    @Size(min = 1, message = "Please add at least one Group")
    private List<UserGroupVO> userGroups;

    @Size(max = 20)
    //@Pattern(regexp = "") TODO Add regex for phone number
    private String phone;

    private UserStatus locked;

    @Size(max = 128)
    private String password;

}
