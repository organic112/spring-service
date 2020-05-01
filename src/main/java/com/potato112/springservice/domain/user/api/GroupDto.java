package com.potato112.springservice.domain.user.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupDto implements Serializable {

    public static final class AttributeName {
        private AttributeName() {
        }
        public static final String ID = "id";
        public static final String GROUP_NAME = "groupName";
        public static final String GROUP_PERMISSIONS = "groupPermissions";
    }

    private String id;

    @NotEmpty(message = "Group name should not be empty")
    @Size(max = 50)
    private String groupName;

    @Valid
    @Size(min = 0, message = "Please add at least one Group") // fixme  =1
    private List<GroupPermissionDto> groupPermissions = new ArrayList<>();

    boolean isActive;
}
