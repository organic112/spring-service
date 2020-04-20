package com.potato112.springservice.domain.common.search;


import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

@EqualsAndHashCode
public class OffsetPageRequest implements Pageable, Serializable {

    private final int limit;
    private final long offset;
    private Sort sort;

    public OffsetPageRequest(long offset, int limit, Sort sort) {

        if (offset < 0) {
            throw new IllegalArgumentException("Offset index must be >=0");
        }

        if (limit < 1) {
            throw new IllegalArgumentException("Limit must not be < 1");
        }
        this.limit = limit;
        this.offset = offset;
        this.sort = sort;
    }

    public OffsetPageRequest(long offset, int limit) {
        this(offset, limit, Sort.unsorted());
    }

    @Override
    public int getPageNumber() {
        // custom getOffset impl allows ignore this value
        return 0;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    @Override
    public Pageable next() {
        return new OffsetPageRequest((getOffset() + getPageSize()), getPageSize(), getSort());
    }

    public OffsetPageRequest previous() {
        if (hasPrevious()) {
            return new OffsetPageRequest(getOffset() - getPageSize(), getPageSize(), getSort());
        }
        return this;
    }

    @Override
    public Pageable previousOrFirst() {

        if (hasPrevious()) {
            return previous();
        }
        return first();
    }

    @Override
    public Pageable first() {
        return new OffsetPageRequest(0, getPageSize(), getSort());
    }

    @Override
    public boolean hasPrevious() {
        return offset >= limit;
    }
}
