package com.fadlan.tht.dto.response;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PagedResponse<T> {

    private final List<T> items;
    private final Pagination pagination;

    public PagedResponse(List<T> items, Pagination pagination) {
        this.items = items;
        this.pagination = pagination;
    }

    public static <T> PagedResponse<T> from(Page<T> page) {
        return new PagedResponse<>(
                page.getContent(),
                Pagination.from(page));
    }
}
