package com.potato112.springservice.domain.common;

import org.springframework.data.repository.CrudRepository;

public interface SysMapper<ENTITY, VO> {

    VO mapToVo(ENTITY entity);

    default ENTITY mapToEntity(VO modelVo) {
        throw new UnsupportedOperationException();
    }

    default ENTITY mapToEntity(VO modelVo, CrudRepository<ENTITY, String> crudRepository) {
        throw new UnsupportedOperationException();
    }
}
