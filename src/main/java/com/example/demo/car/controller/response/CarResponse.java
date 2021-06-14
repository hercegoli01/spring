package com.example.demo.car.controller.response;

import com.example.demo.car.entity.Car;

import java.util.List;

public class CarResponse {

    private List<Car> vehicles;

    public CarResponse() {
    }

    public CarResponse(List<Car> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Car> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Car> vehicles) {
        this.vehicles = vehicles;
    }
}
