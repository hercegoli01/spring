package com.example.demo.car.service;

import com.example.demo.core.CRUDService;
import com.example.demo.car.entity.Car;

public interface CarService extends CRUDService<Car> {
    Car findByProdYear(int prodYear);
}
