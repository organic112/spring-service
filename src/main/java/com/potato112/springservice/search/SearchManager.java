package com.potato112.springservice.search;

import java.util.List;

public interface SearchManager<MODEL> {

    int count(QueryMeta queryMeta);

    List<MODEL> find(QueryMeta queryMeta);

}
