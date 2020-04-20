package com.potato112.springservice.domain.common.search;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class OffsetQueryInfoVo {

    private final long offset;
    private final long limit;
    private final long total;

    public OffsetQueryInfoVo(long offset, long limit, long total) {
        this.offset = offset;
        this.limit = limit;
        this.total = total;
    }
}
