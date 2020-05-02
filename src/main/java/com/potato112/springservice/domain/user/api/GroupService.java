package com.potato112.springservice.domain.user.api;


import com.potato112.springservice.domain.common.search.OffsetResponseVo;
import com.potato112.springservice.domain.common.search.OffsetSearchDto;
import com.potato112.springservice.domain.group.GroupSearchDto;
import com.potato112.springservice.repository.entities.auth.UserGroup;

import java.util.Optional;


public interface GroupService {

    String create(GroupDto groupDto);

    OffsetResponseVo<GroupOverviewResponseDto> getGroups(GroupSearchDto searchDto);

    int count(OffsetSearchDto searchDto);

    Optional<GroupDto> getGroup(String id);

    GroupDto update(GroupDto groupDto);

    //UserGroup save(UserGroup userGroup);

    //UserFormParametersVo getUserFromParameters();
    // TODO other


}
