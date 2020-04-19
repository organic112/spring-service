package com.potato112.springservice.domain.user;


import org.springframework.data.domain.Pageable;

public interface HasPageable extends HasSort {

    Pageable getPageable();

}
