package com.example.demo.user.service.impl;

import com.example.demo.core.implementation.CRUDServiceImplementation;
import com.example.demo.user.entity.Role;
import com.example.demo.user.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends CRUDServiceImplementation<Role> implements RoleService {


    @Override
    protected void updateCore(Role updatableEntity, Role entity) {
        updatableEntity.setAuthority(entity.getAuthority());
    }

    @Override
    protected Class<Role> getManagedClass() {
        return Role.class;
    }
}
