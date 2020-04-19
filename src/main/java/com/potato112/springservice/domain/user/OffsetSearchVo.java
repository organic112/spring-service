package com.potato112.springservice.domain.user;


import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Map;

@Getter
public class OffsetSearchVo implements HasPageable {

    public static final String SORT_FIELD = "sortField";
    public static final String SORT_ORDER = "sortOrder";
    public static final String OFFSET = "offset";
    public static final String LIMIT = "limit";

    protected final OffsetPageRequest pageable;

    public OffsetSearchVo(Map<String, String> params) {

        if (StringUtils.isBlank(params.get("limit")) || StringUtils.isBlank(params.get("offset"))) {
            throw new IllegalArgumentException("limit and offset should be defined");
        }

        int offset = Integer.parseInt(params.get("offset"));
        int limit = Integer.parseInt( params.get("limit"));

        String sortField = params.get( params.get("sortField"));
        Sort sort;

        if (StringUtils.isNotBlank(sortField)) {
            sort = Sort.by(sortField);

            String sortOrder = params.get(params.get("sortOrder"));

            if (StringUtils.containsIgnoreCase(sortOrder, "desc")) {
                sort = sort.descending();
            } else {
                sort = sort.ascending();
            }
        } else {
            sort = Sort.unsorted();
        }
        this.pageable = new OffsetPageRequest(offset, limit, sort);
    }

    @Override
    public Pageable getPageable() {
        return this.pageable;
    }

    @Override
    public Sort getSort() {
        return null;
    }
}
