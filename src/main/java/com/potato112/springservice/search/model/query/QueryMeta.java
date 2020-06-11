package com.potato112.springservice.search.model.query;

import com.potato112.springservice.search.model.query.advanced.SavedAdvSearch;
import lombok.Data;

import java.util.List;

/**
 * Data object storing all meta information for current Hibernate query
 * 1) List<Filter> filters - list of filters for simple search
 * 2) SavedAdvSearch - filtering logic rows for advanced search
 *
 * One of above exists for current simple or advanced logic search
 */
@Data
public class QueryMeta {

    // count for fast results
    private int numberOfIds;

    // pagination
    private int pageSize;
    private int currentPage;
    private int offset;
    private int startIndex;
    private int lastCount;

    // filtering data (to create Hibernate criterions)
    private List<Filter> filters;
    private SavedAdvSearch savedAdvSearch;

    // sorting, query state
    private List<Object> sortField;
    private boolean[] ascending;
    private boolean dirty = false;

    public QueryMeta() {
    }

    public QueryMeta(int pageSize, int currentPage) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }

    // TODO continue with Query info
}
