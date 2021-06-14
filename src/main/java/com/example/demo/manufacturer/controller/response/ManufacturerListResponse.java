package com.example.demo.manufacturer.controller.response;

import com.example.demo.manufacturer.entity.Manufacturer;

import java.util.List;

public class ManufacturerListResponse {

    private List<Manufacturer> response;

    public ManufacturerListResponse() {
    }

    public ManufacturerListResponse(List<Manufacturer> response) {
        this.response = response;
    }

    public List<Manufacturer> getResponse() {
        return response;
    }

    public void setResponse(List<Manufacturer> response) {
        this.response = response;
    }
}
