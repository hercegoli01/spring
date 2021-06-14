package com.example.demo.user.service.impl;

import com.example.demo.core.implementation.CRUDServiceImplementation;
import com.example.demo.user.entity.User;
import com.example.demo.user.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.TypedQuery;

@Service
public class UserServiceImpl extends CRUDServiceImplementation<User> implements UserService {


    @Override
    protected void updateCore(User updatableEntity, User entity) {
        updatableEntity.setPassword(entity.getPassword());
        updatableEntity.setUsername(entity.getUsername());
        updatableEntity.setAuthorities(entity.getAuthorities());
    }

    @Override
    protected Class<User> getManagedClass() {
        return User.class;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            TypedQuery<User> query = entityManager.createNamedQuery(User.FIND_USER_BY_USER_NAME, User.class);
            query.setParameter("username", username);

            return query.getSingleResult();
        } catch (Exception e) {
            throw new UsernameNotFoundException("Nem található felhasználónév: " + username);
        }
    }
}
