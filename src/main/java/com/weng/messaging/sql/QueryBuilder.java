package com.weng.messaging.sql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class QueryBuilder<T> {

    protected static class Param {

        final String key;
        final Object value;

        public Param(String key, Object value) {
            this.key = key;
            this.value = value;
        }
    }

    private StringBuilder sql = new StringBuilder();
    private List<Param> params = new ArrayList<>();
    private List<String> names = new ArrayList<>();

    public abstract Query query(EntityManager em);

    public abstract Query query(EntityManager em, String select);

    public abstract long count(EntityManager em);

    public String sql() {
        return sql.toString();
    }

    protected List<Param> params() {
        return params;
    }

    protected Query bind(Query query, String name, Object param) {
        if (param.getClass().isArray())
            param = Arrays.asList((Object[]) param);
        query.setParameter(name, param);
        return query;
    }

    public QueryBuilder append(Object o) {
        sql.append(o).append(" ");
        if (o instanceof String) {
            names.clear();
            Matcher matcher = Pattern.compile(":\\s*(\\w+)").matcher(o.toString());
            while (matcher.find()) names.add(matcher.group(1));
        }
        return this;
    }

    public QueryBuilder alwayTrue() {
        append("1=1");
        return this;
    }

    public QueryBuilder param(Object value) {
        params.add(new Param(key(), value));
        return this;
    }

    public QueryBuilder like(String value) {
        params.add(new Param(key(), "%" + value + "%"));
        return this;
    }

    public QueryBuilder llike(String value) {
        return like(value.toLowerCase());
    }

    public QueryBuilder ulike(String value) {
        return like(value.toUpperCase());
    }

    private String key() {
        assert names.size() <= 0;
        return names.remove(0);
    }
}
