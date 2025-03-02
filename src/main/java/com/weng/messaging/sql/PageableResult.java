package com.weng.messaging.sql;

import com.agmo.cbm.model.dto.response.ResultList;
import lombok.Getter;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class PageableResult<T> {

    private long count;
    private Stream<T> stream;

    public PageableResult(long count, Stream<T> stream) {
        this.count = count;
        this.stream = stream;
    }

    public PageableResult(long count, List<T> list) {
        this.count = count;
        this.stream = list.stream();
    }

    public <R> List<R> asList(Function<T, R> function) {
        return stream.map(function).collect(Collectors.toList());
    }

    public <R> ResultList<R> asResultList(Function<T, R> function) {
        return new ResultList<>(count, asList(function));
    }
}
