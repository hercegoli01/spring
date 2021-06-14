package com.example.demo.manufacturer.controller;

import com.example.demo.manufacturer.controller.response.ManufacturerListResponse;
import com.example.demo.manufacturer.entity.Manufacturer;
import com.example.demo.manufacturer.service.ManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ManufacturerController {


        @Autowired
        private ManufacturerService service;

        @GetMapping(value = "api/manufacturer/sort")
        public ResponseEntity<ManufacturerListResponse> sortAll(){
                ManufacturerListResponse list = new ManufacturerListResponse();
                list.setResponse(service.sortAll());
                return ResponseEntity.ok(list);
        }

        //FINDING A MANUFACTURER BY NAME
        @GetMapping(value = "/api/manufacturer/{name}")
        public ResponseEntity<Manufacturer> findByName(@PathVariable String name) {
                Manufacturer man = service.findByName(name);
                if (man != null) {
                        return ResponseEntity.ok(man);
                }
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        //CREATING MANUFACTURER
        @PostMapping(value = "/api/manufacturer", consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Manufacturer> create(@RequestBody Manufacturer entity) {
                service.create(entity);
                return ResponseEntity.ok(entity);
        }

        //UPDATING MANUFACTURER
        @PutMapping(value = "/api/manufacturer", consumes = MediaType.APPLICATION_JSON_VALUE)
        public Manufacturer updateManufacturer(@RequestBody Manufacturer entity) {
                return service.update(entity);
        }

        //DELETING MANUFACTURER
        @DeleteMapping(value = "/api/manufacturer/{id}")
        public ResponseEntity<String> deleteById(@PathVariable Long id) {
                if (service.deleteById(id)) {
                        return ResponseEntity.ok("DELETED SUCCESSFULLY");
                }
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

}
