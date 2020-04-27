package com.potato112.springservice.domain.user.crud;


import com.potato112.springservice.domain.user.api.GroupOverviewResponseDto;
import com.potato112.springservice.domain.user.model.views.UserOverviewResponseVo;
import com.potato112.springservice.repository.entities.auth.User;
import com.potato112.springservice.repository.entities.auth.UserGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface GroupRepository extends PagingAndSortingRepository<UserGroup, String>, JpaSpecificationExecutor<UserGroup> {


    @Override
    Collection<UserGroup> findAll();

    void deleteByIdIn(Iterable<String> ids);

    long countAllByIdIn(Iterable<String> ids);

    Optional<UserGroup> findByGroupName(String groupName);

    @Query("select g from UserGroup g where g.id in :groupIds")
    Page<UserGroup> findByGroupIds(@Param("groupIds") List<String> userIds, Pageable pageable);

    @Query(value =
            "SELECT " +
                    "grp.pk_user_group as id,"+
                    "grp.group_name as groupName "+
                    // "'GROUP_NAME' as permissions"+
                    "FROM user_group grp "+
                    "WHERE ( "+
                    "    ( :groupName is null or grp.group_name LIKE concat('%', :groupName ,'%') ) "+
                    " ) ",
            countQuery =
                    "SELECT COUNT(grp.pk_user_group) FROM user_group grp "+
                            "WHERE ( "+
                            "    ( :groupName is null or grp.group_name LIKE concat('%', :groupName ,'%') ) "+
                            " ) ",
            nativeQuery = true)
    Page<GroupOverviewResponseDto> getGroupsForOverview(@Param("groupName") String groupName, Pageable pageable);
}
