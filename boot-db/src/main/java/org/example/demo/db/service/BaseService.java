package org.example.demo.db.service;

public interface BaseService<T>{

    T save(T t);
    T update(T t);
    T findById(Long id);
}
