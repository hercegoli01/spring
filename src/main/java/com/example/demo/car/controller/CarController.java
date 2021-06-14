package com.example.vehicleapp.vehicle.controller;

import com.example.demo.car.controller.response.CarResponse;
import com.example.demo.car.entity.Car;
import com.example.demo.car.service.implementation.CarServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CarController {

    @Autowired
    private CarServiceImplementation service;

    @GetMapping(value = "/api/vehicle/{name}")
    public ResponseEntity getVehicleByName(@PathVariable String name) {
        Car vehicle = service.findByName(name);
        if (vehicle != null) {
            return ResponseEntity.ok(vehicle);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping(value = "/api/vehicle/sort")
    public ResponseEntity<CarResponse> sortData() {
        CarResponse response = new CarResponse();
        response.setVehicles(service.sortAll());
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/api/vehicle/year/{prodYear}")
    public ResponseEntity findByProdYear(@PathVariable String prodYear) {
        int year = Integer.parseInt(prodYear);
        Car vehicle = service.findByProdYear(year);
        if (vehicle != null) {
            return ResponseEntity.ok(vehicle);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping(value = "/api/vehicle", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Car> createVehicle(@RequestBody Car vehicle) {
        service.create(vehicle);
        return ResponseEntity.ok(vehicle);
    }

    @PutMapping(value = "/api/vehicle", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Car> update(@RequestBody Car vehicle) {
        return ResponseEntity.ok(service.update(vehicle));
    }

    @DeleteMapping(value = "/api/vehicle/{id}")
    public ResponseEntity deleteById(@PathVariable Long id) {
        if (service.deleteById(id)) {
            return ResponseEntity.ok("Deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
