package com.potato112.springservice.search;

import lombok.Data;

import java.util.List;

@Data
public class QueryMeta {

    private int pageSize;
    private int currentPage;
    private int offset;
    private int startIndex;
    private int lastCount;
    private List<Filter> filters;
    private int numberOfIds;
    private boolean dirty = false;
    private List<Object> sortField;
    private boolean[] ascending;

    /**
     * Used in adv. search instead of filters
     */
    private SavedSearch savedSearch;

    public QueryMeta() {
    }

    public QueryMeta(int pageSize, int currentPage) {
        this.pageSize = pageSize;
        this.currentPage = currentPage;
    }

    // TODO continue with Query info
}
