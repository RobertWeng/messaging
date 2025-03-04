package com.weng.messaging.sql;

import lombok.Getter;

import java.util.List;

@Getter
public class ResultList<T> {

    private final long total;

    private final List<T> result;

    public ResultList(long total, List<T> result) {
        this.total = total;
        this.result = result;
    }
}