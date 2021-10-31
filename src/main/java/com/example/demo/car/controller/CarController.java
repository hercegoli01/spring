package com.example.demo.car.controller;

import com.example.demo.car.helper.EXCELExporter;
import com.example.demo.car.service.CSVService;
import com.example.demo.car.controller.response.CarResponse;
import com.example.demo.car.entity.Car;
import com.example.demo.car.service.EXCELService;
import com.example.demo.car.service.implementation.CarServiceImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class CarController {

    @Autowired
    private CarServiceImplementation service;

    @Autowired
    CSVService fileService;

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

    @GetMapping("api/vehicle/download")
    public ResponseEntity<Resource> getFile() {
        String filename = "cars.csv";
        InputStreamResource file = new InputStreamResource(fileService.load());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }

    @Autowired
    EXCELService excelService;

    @GetMapping("/api/vehicle/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException, IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Car> carList = excelService.listAll();

        EXCELExporter excelExporter = new EXCELExporter(carList);

        excelExporter.export(response);
    }
}
