package com.fadlan.tht.dto.response;

import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class Pagination {

    private final int page;
    private final int size;
    private final long totalElements;
    private final int totalPages;
    private final boolean hasNext;
    private final boolean hasPrevious;

    private Pagination(Page<?> page) {
        this.page = page.getNumber();
        this.size = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.hasNext = page.hasNext();
        this.hasPrevious = page.hasPrevious();
    }

    public static Pagination from(Page<?> page) {
        return new Pagination(page);
    }
}
