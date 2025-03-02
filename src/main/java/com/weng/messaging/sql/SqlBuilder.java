package com.weng.messaging.sql;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.Query;

import java.math.BigInteger;
import java.util.Optional;

public class SqlBuilder<T> extends QueryBuilder<T> {

    @Override
    public Query query(EntityManager em) {
        Query query = em.createNativeQuery(sql());
        params().forEach(param -> bind(query, param.key, param.value));
        return query;
    }

    @Override
    public Query query(EntityManager em, String select) {
        Query query = em.createNativeQuery(select + " " + sql());
        params().forEach(param -> bind(query, param.key, param.value));
        return query;
    }

    @Override
    public long count(EntityManager em) {
        Object count = query(em, "SELECT COUNT(*)").getSingleResult();
        if (count == null) return 0l;
        if (count instanceof BigInteger) return ((BigInteger) count).longValue();
        return Long.valueOf(count.toString());
    }

    public Query query(EntityManager em, Class<T> clazz) {
        Query query = em.createNativeQuery(sql(), clazz);
        params().forEach(param -> bind(query, param.key, param.value));
        return query;
    }

    public Query query(EntityManager em, Class<T> clazz, String select) {
        Query query = em.createNativeQuery(select + " " + sql(), clazz);
        params().forEach(param -> bind(query, param.key, param.value));
        return query;
    }

    public Optional<T> findFirst(EntityManager em, Class<T> clazz) {
        try {
            return Optional.of((T) query(em, clazz).getSingleResult());
        } catch (NoResultException | NonUniqueResultException e) {
            return Optional.empty();
        }
    }
}
