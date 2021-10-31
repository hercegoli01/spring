package com.example.demo.car.service;

import com.example.demo.car.entity.Car;
import com.example.demo.car.entity.*;
import com.example.demo.car.helper.CSVHelper;

import com.example.demo.car.service.implementation.CarServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
public class CSVService {


    @Autowired
    private CarServiceImplementation service;

    public ByteArrayInputStream load() {
        List<Car> cars = service.findAll();

        ByteArrayInputStream in = CSVHelper.tutorialsToCSV(cars);
        return in;
    }
}
