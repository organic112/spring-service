package com.potato112.springservice.domain.user;


import com.potato112.springservice.domain.user.model.UserOverviewResponseVo;
import com.potato112.springservice.repository.entity.User;
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
            "usr.pk_user as id,"+
            "usr.email as email,"+
            "usr.first_name as firstName,"+
            "usr.last_name as lastName,"+
            "usr.lock_flag as locked,"+
            "usr.phone as phone "+
            // "'GROUP_NAME' as groups"+
            "FROM user usr "+
            "WHERE ( "+
            "    ( :email is null or usr.email LIKE concat('%', :email ,'%') ) "+
            "AND ( :firstName is null or usr.first_name LIKE concat('%', :firstName ,'%') ) "+
            "AND ( :lastName is null or usr.last_name LIKE concat('%', :lastName ,'%') ) "+
            "AND ( :locked is null or usr.lock_flag LIKE concat('%', :locked ,'%')  ) "+
            "AND ( :phone is null or usr.phone LIKE concat('%', :phone ,'%') ) "+
            " ) ",
             countQuery =
            "SELECT COUNT(usr.pk_user) FROM user usr "+
            "WHERE ( "+
            "    ( :email is null or usr.email LIKE concat('%', :email ,'%') ) "+
            "AND ( :firstName is null or usr.first_name LIKE concat('%', :firstName ,'%') ) "+
            "AND ( :lastName is null or usr.last_name LIKE concat('%', :lastName ,'%') ) "+
            "AND ( :locked is null or usr.lock_flag LIKE concat('%', :locked ,'%')  ) "+
            "AND ( :phone is null or usr.phone LIKE concat('%', :phone ,'%') ) "+
            " ) ",
            nativeQuery = true)
    Page<UserOverviewResponseVo> getAllUsersForOverview(@Param("email") String email,
                                                        @Param("firstName") String firstName,
                                                        @Param("lastName") String lastName,
                                                     // @Param("organizations") String organizations,
                                                        @Param("phone") String phone,
                                                        @Param("locked") String locked,
                                                        Pageable pageable);


   // Page<UserOverviewResponseVo> getAllUsersForOverview();
}
