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
    @Query(value = " TODO native query ", countQuery = " TODO native query ", nativeQuery = true)
    Page<UserOverviewResponseVo> getAllUsersForOverview(@Param("email") String email,
                                                        @Param("firstName") String firstName,
                                                        @Param("lastName") String lastName,
                                                        @Param("organizations") String organizations,
                                                        @Param("phone") String phone,
                                                        @Param("locked") String locked,
                                                        Pageable pageable);

}
