package com.backend.ticketing.service;

import jakarta.persistence.Parameter;
import jakarta.persistence.TemporalType;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Date;
import java.util.Calendar;

public class DummyTypedQuery<T> implements TypedQuery<T> {
    private final List<T> result;

    public DummyTypedQuery(List<T> result) {
        this.result = result;
    }

    @Override
    public List<T> getResultList() {
        return result;
    }

    @Override
    public T getSingleResult() {
        if (result == null || result.isEmpty()) return null;
        return result.get(0);
    }

    // The following methods are required by the interface, so must be present, but can throw:
    @Override public int executeUpdate() { throw new UnsupportedOperationException(); }
    @Override public int getFirstResult() { throw new UnsupportedOperationException(); }
    @Override public int getMaxResults() { throw new UnsupportedOperationException(); }
    @Override public TypedQuery<T> setFirstResult(int startPosition) { throw new UnsupportedOperationException(); }
    @Override public TypedQuery<T> setMaxResults(int maxResult) { throw new UnsupportedOperationException(); }
    @Override public TypedQuery<T> setHint(String hintName, Object value) { throw new UnsupportedOperationException(); }

    @Override
    public Map<String, Object> getHints() {
        return Map.of();
    }

    @Override public Set<jakarta.persistence.Parameter<?>> getParameters() { throw new UnsupportedOperationException(); }
    @Override public jakarta.persistence.Parameter<?> getParameter(String name) { throw new UnsupportedOperationException(); }
    @Override public <U> jakarta.persistence.Parameter<U> getParameter(String name, Class<U> type) { throw new UnsupportedOperationException(); }
    @Override public jakarta.persistence.Parameter<?> getParameter(int position) { throw new UnsupportedOperationException(); }
    @Override public <U> jakarta.persistence.Parameter<U> getParameter(int position, Class<U> type) { throw new UnsupportedOperationException(); }
    @Override public boolean isBound(jakarta.persistence.Parameter<?> param) { throw new UnsupportedOperationException(); }
    @Override public <U> U getParameterValue(jakarta.persistence.Parameter<U> param) { throw new UnsupportedOperationException(); }
    @Override public Object getParameterValue(String name) { throw new UnsupportedOperationException(); }
    @Override public Object getParameterValue(int position) { throw new UnsupportedOperationException(); }
    @Override public TypedQuery<T> setFlushMode(jakarta.persistence.FlushModeType flushMode) { throw new UnsupportedOperationException(); }
    @Override public jakarta.persistence.FlushModeType getFlushMode() { throw new UnsupportedOperationException(); }
    @Override public TypedQuery<T> setLockMode(jakarta.persistence.LockModeType lockMode) { throw new UnsupportedOperationException(); }
    @Override public jakarta.persistence.LockModeType getLockMode() { throw new UnsupportedOperationException(); }
    @Override public <U> U unwrap(Class<U> cls) { throw new UnsupportedOperationException(); }
    @Override public TypedQuery<T> setParameter(String name, Object value) { throw new UnsupportedOperationException(); }
    @Override public TypedQuery<T> setParameter(int position, Object value) { throw new UnsupportedOperationException(); }
    @Override public <U> TypedQuery<T> setParameter(jakarta.persistence.Parameter<U> param, U value) { throw new UnsupportedOperationException(); }

    @Override
    public TypedQuery<T> setParameter(Parameter<Calendar> parameter, Calendar calendar, TemporalType temporalType) {
        return null;
    }

    @Override
    public TypedQuery<T> setParameter(Parameter<Date> parameter, Date date, TemporalType temporalType) {
        return null;
    }

    @Override public TypedQuery<T> setParameter(String name, java.util.Date value, jakarta.persistence.TemporalType temporalType) { throw new UnsupportedOperationException(); }
    @Override public TypedQuery<T> setParameter(String name, java.util.Calendar value, jakarta.persistence.TemporalType temporalType) { throw new UnsupportedOperationException(); }
    @Override public TypedQuery<T> setParameter(int position, java.util.Date value, jakarta.persistence.TemporalType temporalType) { throw new UnsupportedOperationException(); }
    @Override public TypedQuery<T> setParameter(int position, java.util.Calendar value, jakarta.persistence.TemporalType temporalType) { throw new UnsupportedOperationException(); }
}
