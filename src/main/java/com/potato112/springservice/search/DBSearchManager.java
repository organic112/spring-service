package com.potato112.springservice.search;

import java.util.List;

public interface DBSearchManager<OBJTYPE> {



    List<OBJTYPE> find(QueryMeta queryMeta);

    int count(QueryMeta queryMeta);

}
