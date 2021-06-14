package com.example.demo.car.entity;

import com.example.demo.core.entity.CoreEntity;
import com.example.demo.manufacturer.entity.Manufacturer;

import javax.persistence.*;

@Entity
@Table(name = "vehicle")
public class Car extends CoreEntity {

    @Column(name = "type")
    private String name;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    @Column(name = "doors")
    private Double doors;

    @Column(name = "prod_year")
    private Double prodYear;


    public Car() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Double getDoors() {
        return doors;
    }
    public int getIntDoors(){
        return doors.intValue();
    }

    public void setDoors(Double doors) {
        this.doors = doors;
    }

    public Double getProdYear() {
        return prodYear;
    }
    public int getIntProdYear(){
        return prodYear.intValue();
    }

    public void setProdYear(Double prodYear) {
        this.prodYear = prodYear;
    }
}
