package com.potato112.springservice.search.interfaces;

import com.potato112.springservice.search.model.query.QueryMeta;

import java.util.List;

public interface SearchManager<MODEL> {

    int count(QueryMeta queryMeta);

    List<MODEL> find(QueryMeta queryMeta);

}
