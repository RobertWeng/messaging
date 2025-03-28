package com.weng.messaging.model.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResultList<T> {

    private long total;      // Total number of items
    private int currentPage; // Current page number
    private int pageSize;    // Size of each page
    private int totalPages;  // Total number of pages
    private boolean hasNextPage; // Is there a next page?
    private boolean hasPreviousPage; // Is there a previous page?

    private List<T> result;  // List of results for the current page

    public ResultList(long total, int currentPage, int pageSize, List<T> result) {
        this.total = total;
        this.currentPage = currentPage + 1;
        this.pageSize = pageSize;
        this.totalPages = (int) Math.ceil((double) total / pageSize);
        this.hasNextPage = currentPage < totalPages - 1;
        this.hasPreviousPage = currentPage > 0;
        this.result = result;
    }
}