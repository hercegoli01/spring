package com.example.demo.car.service;

import com.example.demo.core.CRUDService;
import com.example.demo.car.entity.Car;

import java.io.ByteArrayInputStream;

public interface CarService extends CRUDService<Car> {
    Car findByProdYear(int prodYear);
}
