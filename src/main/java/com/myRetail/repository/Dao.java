package com.myRetail.repository;

import java.util.Optional;

public interface Dao<T> {
    Optional<T> get(String id);
    void save(T t);
}