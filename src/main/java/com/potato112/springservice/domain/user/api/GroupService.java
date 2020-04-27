package com.potato112.springservice.domain.user.api;


import com.potato112.springservice.domain.common.search.OffsetResponseVo;
import com.potato112.springservice.domain.common.search.OffsetSearchDto;
import com.potato112.springservice.domain.group.GroupSearchDto;


public interface GroupService {

    String create(GroupDto userVo);

    OffsetResponseVo<GroupOverviewResponseDto> getGroups(GroupSearchDto searchDto);

    int count(OffsetSearchDto searchDto);

    //UserFormParametersVo getUserFromParameters();
    // TODO other
}
