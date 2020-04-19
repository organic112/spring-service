package com.potato112.springservice.domain.user;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection;


@Getter
@ToString
@EqualsAndHashCode
public class OffsetResponseVo<T> {


    private Collection<T> content;
    private OffsetQueryInfoVo offsetInfo;

    public OffsetResponseVo(Collection<T> content, OffsetQueryInfoVo offsetInfo) {
        this.content = content;
        this.offsetInfo = offsetInfo;
    }
}
