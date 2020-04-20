package com.potato112.springservice.domain.common.search;


import org.springframework.data.domain.Pageable;

public interface HasPageable extends HasSort {

    Pageable getPageable();

}
