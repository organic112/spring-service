package com.potato112.springservice.domain.user.model.search;

import com.potato112.springservice.repository.entity.User;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public UserSpecification() {
    }

    public static Specification<User> userByEmail(String email){

        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("email"), email));
    }
}
