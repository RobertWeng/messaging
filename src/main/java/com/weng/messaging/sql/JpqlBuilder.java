package com.weng.messaging.sql;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Optional;

public class JpqlBuilder<T> extends QueryBuilder<T> {

    public JpqlBuilder<T> orderBy(String clause) {
        return (JpqlBuilder) append("ORDER BY").append(clause);
    }

    @Override
    public Query query(EntityManager em) {
        Query query = em.createQuery(sql());
        params().forEach(param -> bind(query, param.key, param.value));
        return query;
    }

    @Override
    public Query query(EntityManager em, String select) {
        Query query = em.createQuery(select + " " + sql());
        params().forEach(param -> bind(query, param.key, param.value));
        return query;
    }

    public long distinctCount(EntityManager em) {
        Object count = query(em, "SELECT COUNT(DISTINCT o)").getSingleResult();
        if (count == null) return 0l;
        if (count instanceof BigInteger bigInteger) return bigInteger.longValue();
        return Long.valueOf(count.toString());
    }

    public long count(EntityManager em) {
        Object count = query(em, "SELECT COUNT(*)").getSingleResult();
        if (count == null) return 0l;
        if (count instanceof BigInteger bigInteger) return bigInteger.longValue();
        return Long.valueOf(count.toString());
    }

    public TypedQuery<T> query(EntityManager em, Class<T> clazz) {
        TypedQuery<T> query = em.createQuery(sql(), clazz);
        params().forEach(param -> bind(query, param.key, param.value));
        return query;
    }

    public TypedQuery<T> query(EntityManager em, Class<T> clazz, String select) {
        TypedQuery<T> query = em.createQuery(select + " " + sql(), clazz);
        params().forEach(param -> bind(query, param.key, param.value));
        return query;
    }

    public Optional<T> findFirst(EntityManager em, Class<T> clazz) {
        try {
            return Optional.of(query(em, clazz).getSingleResult());
        } catch (NoResultException | NonUniqueResultException e) {
            return Optional.empty();
        }
    }
}
