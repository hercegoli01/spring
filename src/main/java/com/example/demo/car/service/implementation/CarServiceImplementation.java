package com.example.demo.car.service.implementation;

import com.example.demo.core.implementation.CRUDServiceImplementation;
import com.example.demo.car.entity.Car;
import com.example.demo.car.service.CarService;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImplementation extends CRUDServiceImplementation<Car> implements CarService {
    @Override
    protected void updateCore(Car updatableEntity, Car entity) {
        updatableEntity.setDoors(entity.getDoors());
        updatableEntity.setManufacturer(entity.getManufacturer());
        updatableEntity.setProdYear(entity.getProdYear());
        updatableEntity.setName(entity.getName());
    }

    @Override
    protected Class<Car> getManagedClass() {
        return Car.class;
    }

    @Override
    public Car findByProdYear(int prodYear) {
        return null;
    }


}
