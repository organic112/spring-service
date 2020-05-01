package com.potato112.springservice.domain.user.crud;


import com.potato112.springservice.domain.user.model.views.UserOverviewResponseVo;
import com.potato112.springservice.repository.entities.auth.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends PagingAndSortingRepository<User, String>, JpaSpecificationExecutor<User> {

    @Override
    Collection<User> findAll();

    void deleteByIdIn(Iterable<String> ids);

    long countAllByIdIn(Iterable<String> ids);

    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.id in :userIds")
    Page<User> findByUsersIds(@Param("userIds") List<String> userIds, Pageable pageable);

    /*
    TODO add more sophisticated methods
     and native SQL queries
     */
    @Query(value =
            "SELECT " +
            "usr.pk_user as id, "+
            "usr.email as email, "+
            "usr.first_name as firstName, "+
            "usr.last_name as lastName, "+
            "usr.lock_flag as locked, "+
            "usr.phone as phone, "+
            "group_concat(grp.group_name separator '&&') as userGroups "+
            "FROM user usr "+
            "LEFT JOIN user_group_mapping grpMap ON usr.pk_user = grpMap.user_id "+
            "LEFT JOIN user_group grp ON grpMap.group_id = grp.pk_user_group "+
            "WHERE ( "+
            "    ( :email is null or usr.email LIKE concat('%', :email ,'%') ) "+
            "AND ( :firstName is null or usr.first_name LIKE concat('%', :firstName ,'%') ) "+
            "AND ( :lastName is null or usr.last_name LIKE concat('%', :lastName ,'%') ) "+
            "AND ( :locked is null or usr.lock_flag LIKE concat('%', :locked ,'%')  ) "+
            "AND ( :phone is null or usr.phone LIKE concat('%', :phone ,'%') ) "+
            "AND ( :userGroups is null or grp.group_name LIKE concat('%', :userGroups ,'%') ) "+
            " ) "+
            "GROUP BY "+
            "usr.pk_user, "+
            "usr.email, "+
            "usr.first_name, "+
            "usr.last_name, "+
            "usr.lock_flag, "+
            "usr.phone "
            ,countQuery =
            "SELECT COUNT(usr.pk_user) FROM user usr "+
            "LEFT JOIN user_group_mapping grpMap ON usr.pk_user = grpMap.user_id "+
            "LEFT JOIN user_group grp ON grpMap.group_id = grp.pk_user_group "+
            "WHERE ( "+
            "    ( :email is null or usr.email LIKE concat('%', :email ,'%') ) "+
            "AND ( :firstName is null or usr.first_name LIKE concat('%', :firstName ,'%') ) "+
            "AND ( :lastName is null or usr.last_name LIKE concat('%', :lastName ,'%') ) "+
            "AND ( :locked is null or usr.lock_flag LIKE concat('%', :locked ,'%')  ) "+
            "AND ( :phone is null or usr.phone LIKE concat('%', :phone ,'%') ) "+
            "AND ( :userGroups is null or grp.group_name LIKE concat('%', :userGroups ,'%') ) "+
            " ) "+
            "GROUP BY "+
            "usr.pk_user, "+
            "usr.email, "+
            "usr.first_name, "+
            "usr.last_name, "+
            "usr.lock_flag, "+
            "usr.phone "
            ,nativeQuery = true)
    Page<UserOverviewResponseVo> getAllUsersForOverview(@Param("email") String email,
                                                        @Param("firstName") String firstName,
                                                        @Param("lastName") String lastName,
                                                        @Param("userGroups") String userGroups,
                                                        @Param("phone") String phone,
                                                        @Param("locked") String locked,
                                                        Pageable pageable);


   // Page<UserOverviewResponseVo> getAllUsersForOverview();
}
