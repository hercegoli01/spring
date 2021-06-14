package com.example.demo.manufacturer.service.implementation;

import com.example.demo.core.implementation.CRUDServiceImplementation;
import com.example.demo.manufacturer.entity.Manufacturer;
import com.example.demo.manufacturer.service.ManufacturerService;
import org.springframework.stereotype.Service;

@Service
public class ManufacturerServiceImplementation extends CRUDServiceImplementation<Manufacturer> implements ManufacturerService{

    @Override
    protected void updateCore(Manufacturer updatableEntity, Manufacturer entity) {
        updatableEntity.setName(entity.getName());
    }

    @Override
    protected Class<Manufacturer> getManagedClass() {
        return Manufacturer.class;
    }


}
