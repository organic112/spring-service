package com.potato112.springservice.domain.user.crud;

import com.potato112.springservice.domain.common.search.OffsetQueryInfoVo;
import com.potato112.springservice.domain.common.search.OffsetResponseVo;
import com.potato112.springservice.domain.common.search.OffsetSearchDto;
import com.potato112.springservice.domain.group.GroupSearchDto;
import com.potato112.springservice.domain.user.api.GroupDto;
import com.potato112.springservice.domain.user.api.GroupOverviewResponseDto;
import com.potato112.springservice.domain.user.api.GroupService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class DBGroupService implements GroupService {

    private final GroupRepository groupRepository;

    @Override
    public String create(GroupDto userVo) {
        return null;
    }

    @Override
    public OffsetResponseVo<GroupOverviewResponseDto> getGroups(GroupSearchDto searchDto) {

        Pageable pageable = searchDto.getPageable();
        Page<GroupOverviewResponseDto> page = getGroupsOverviewItems(pageable, searchDto);
        OffsetQueryInfoVo infoVo = new OffsetQueryInfoVo(pageable.getOffset(), pageable.getPageSize(), page.getTotalElements());
        return new OffsetResponseVo<>(page.getContent(), infoVo);
    }

    @Override
    public int count(OffsetSearchDto searchDto) {
        return 0;
    }

    private Page<GroupOverviewResponseDto> getGroupsOverviewItems(Pageable pageable, GroupSearchDto searchDto) {
        Page<GroupOverviewResponseDto> page = groupRepository
                .getGroupsForOverview(searchDto.getGroupName(), pageable);
        return page;
    }
}
