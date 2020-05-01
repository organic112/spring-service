package com.potato112.springservice.domain.user.crud;

import com.potato112.springservice.domain.common.search.OffsetQueryInfoVo;
import com.potato112.springservice.domain.common.search.OffsetResponseVo;
import com.potato112.springservice.domain.common.search.OffsetSearchDto;
import com.potato112.springservice.domain.group.GroupSearchDto;
import com.potato112.springservice.domain.user.api.GroupDto;
import com.potato112.springservice.domain.user.api.GroupOverviewResponseDto;
import com.potato112.springservice.domain.user.api.GroupService;
import com.potato112.springservice.domain.user.model.GroupOverviewMapper;
import com.potato112.springservice.domain.user.model.UserGroupMapper;
import com.potato112.springservice.repository.entities.auth.UserGroup;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class DBGroupService implements GroupService {

    private final GroupRepository groupRepository;


    @Override
    public String create(GroupDto groupDto) {

        UserGroupMapper userGroupMapper = new UserGroupMapper();

        UserGroup userGroup = userGroupMapper.mapToEntity(groupDto);
        groupRepository.save(userGroup);

        System.out.println("user group save");
        return "fixme";
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

        // fetch entity
        Page<UserGroup> entityPage =  groupRepository.findAll(pageable);

        // convert entity to specific dto
        Page<GroupOverviewResponseDto> dtoPage =  entityPage.map(group -> new GroupOverviewMapper().mapToVo(group));
        return dtoPage;
    }
}
