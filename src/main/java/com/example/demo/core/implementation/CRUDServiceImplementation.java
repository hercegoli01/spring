package com.example.demo.core.implementation;

import com.example.demo.core.CRUDService;
import com.example.demo.core.entity.CoreEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.List;

public abstract class CRUDServiceImplementation<T extends CoreEntity> implements CRUDService<T> {

    @Autowired
    protected EntityManager entityManager;

    public CRUDServiceImplementation() {

    }

    @Override
    public List<T> sortAll() {
        return entityManager.createQuery("SELECT a FROM " + getManagedClass().getSimpleName() + " a order by a.name asc", getManagedClass()).getResultList();
    }

    @Override
    public List<T> sortAllById() {
        return entityManager.createQuery("SELECT a FROM " + getManagedClass().getSimpleName() + " a order by a.id asc", getManagedClass()).getResultList();
    }

    @Override
    public List<T> findAll() {
        return entityManager.createQuery("SELECT a FROM " + getManagedClass().getSimpleName() + " a", getManagedClass()).getResultList();
    }

    @Override
    public T create(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public boolean deleteById(Long id) {
        T authorEntity = findById(id);
        if (authorEntity == null) {
            return false;
        }
        entityManager.remove(authorEntity);
        return true;
    }

    @Override
    public T update(T entity) {
        T updateBook = findById(entity.getId());
        if (updateBook != null) {
            updateCore(updateBook, entity);
            entityManager.merge(updateBook);
        }
        return updateBook;
    }

    @Override
    public T findByName(String name) {

        return entityManager.createQuery("SELECT a FROM " + getManagedClass().getSimpleName() + " a WHERE " +
                "a.type = :type", getManagedClass()).setParameter("type", name).getSingleResult();
    }

    @Override
    public T findById(Long id) {
        return entityManager.find(getManagedClass(), id);
    }

    protected abstract void updateCore(T updatableEntity, T entity);

    protected abstract Class<T> getManagedClass();

}
