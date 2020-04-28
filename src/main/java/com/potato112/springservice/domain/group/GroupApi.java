package com.potato112.springservice.domain.group;


import com.potato112.springservice.domain.common.search.OffsetResponseVo;
import com.potato112.springservice.domain.user.api.GroupOverviewResponseDto;
import com.potato112.springservice.domain.user.api.GroupService;
import com.potato112.springservice.domain.user.model.authorize.UserVo;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping(value = GroupApi.ENDPOINT, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class GroupApi {


    static final String ENDPOINT = "/api/v1/group";
    private final GroupService groupService;
    //private final CreateUserService createUserService;


    @GetMapping(value = "/{groupname}")
    public GroupOverviewResponseDto getGroupByName(@PathVariable("groupname") String groupName) {

        // FIXME
       /* System.out.println("request in user api (with param)! grupName:" + grupName);
        Optional<GroupOverviewResponseDto> groupOp = groupService.getGroup(grupName);
        return groupOp.orElseThrow(() -> new RuntimeException("Groups Not Found"));*/
       return null;
    }

    @PostMapping
    public String createGroup(@RequestBody @Valid UserVo userVo) {

        // FIXME
        return null; //createUserService.createUser(userVo);
    }

    @GetMapping
    OffsetResponseVo<GroupOverviewResponseDto> getGroups(@RequestParam Map<String, String> allParams) {
        GroupSearchDto groupSearchDto = new GroupSearchDto(allParams);
        return groupService.getGroups(groupSearchDto);
    }
}
