package com.example.demo.core;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CRUDService<T> {

    List<T> sortAll();
    List<T> sortAllById();
    List<T> findAll();

    @Transactional
    T create(T entity);

    @Transactional
    boolean deleteById(Long id);

    @Transactional
    T update(T entity);

    T findByName(String id);
    T findById(Long id);
}
