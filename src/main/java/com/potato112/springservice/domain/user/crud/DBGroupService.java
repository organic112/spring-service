package com.potato112.springservice.domain.user.crud;

import com.potato112.springservice.domain.common.search.OffsetQueryInfoVo;
import com.potato112.springservice.domain.common.search.OffsetResponseVo;
import com.potato112.springservice.domain.common.search.OffsetSearchDto;
import com.potato112.springservice.domain.group.GroupSearchDto;
import com.potato112.springservice.domain.user.api.GroupDto;
import com.potato112.springservice.domain.user.api.GroupOverviewResponseDto;
import com.potato112.springservice.domain.user.api.GroupService;
import com.potato112.springservice.domain.user.model.GroupMapper;
import com.potato112.springservice.domain.user.model.GroupOverviewMapper;
import com.potato112.springservice.repository.entities.auth.UserGroup;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class DBGroupService implements GroupService {

    private final GroupRepository groupRepository;

    @Override
    public String create(GroupDto groupDto) {

        GroupMapper userGroupMapper = new GroupMapper();
        UserGroup userGroup = userGroupMapper.mapToEntity(groupDto);
        groupRepository.save(userGroup);
        return userGroup.getId();
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
        Page<UserGroup> entityPage = groupRepository.findAll(pageable);
        // convert entity to specific dto
        Page<GroupOverviewResponseDto> dtoPage = entityPage.map(group -> new GroupOverviewMapper().mapToVo(group));
        return dtoPage;
    }

    @Override
    public Optional<GroupDto> getGroup(String id) {

        System.out.println("Try fetch group with id: " + id);
        return groupRepository.findById(id).map(group -> new GroupMapper().mapToVo(group));
    }

    @Override
    public GroupDto update(GroupDto groupDto) {

        System.out.println("group name" + groupDto.getGroupName());
        System.out.println("group permissions size" + groupDto.getGroupPermissions().size());
        System.out.println("ECHO01 before update groupDTO create user: "+ groupDto.getCreateUser());

        UserGroup userGroup = groupRepository.save(new GroupMapper().mapToEntity(groupDto));
        groupRepository.save(userGroup);
        return groupDto;
    }
}
