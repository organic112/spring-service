package com.potato112.springservice.search.interfaces;

import com.potato112.springservice.search.model.query.QueryMeta;

import java.util.List;

/**
 * Base interface for DBSearchManager
 * Implementations refer to current class
 */
public interface DBSearchManager<OBJTYPE> {

    int count(QueryMeta queryMeta);

    List<OBJTYPE> find(QueryMeta queryMeta);
}
