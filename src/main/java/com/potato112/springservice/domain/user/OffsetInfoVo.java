package com.potato112.springservice.domain.user;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
public class OffsetInfoVo {

    private long offset;
    private long limit;
    private long total;

    public OffsetInfoVo(long offset, long limit, long total) {
        this.offset = offset;
        this.limit = limit;
        this.total = total;
    }
}
