package com.example.demo.car.service;


import com.example.demo.car.entity.Car;
import com.example.demo.car.service.implementation.CarServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EXCELService {

    @Autowired
    private CarServiceImplementation service;

    public List<Car> listAll() {
        return service.findAll();
    }
}
