package com.mcc.vehicle.controller;


import com.mcc.vehicle.domain.Vehicle;
import com.mcc.vehicle.service.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequestMapping(VehicleController.BASE_URL)
@AllArgsConstructor
@Validated
@RestController
public class VehicleController {
    public static final String BASE_URL = "/vehicles";

    @Autowired
    private final VehicleService vehicleService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Vehicle>> getAllVehicles(@RequestParam(required = false) Map<String, String> filter){
        System.out.println("[Get all] | parameters: " + filter);

        //1. Turn keys into lowercase
        //2. Check Nullity, if null return Bad Request
        //3. Treat every valid parameter as String
        //4. Guarantee no duplicate key (Since RequestParam will only pick the first one)
        Map<String, String> filterLowercaseKey = new HashMap<>();
        for (Map.Entry<String,String> entry : filter.entrySet()) {
            if (entry.getValue().equals("")) //check if null
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

            filterLowercaseKey.put(entry.getKey().toLowerCase(), entry.getValue().toLowerCase());
        }

        return new ResponseEntity<>(vehicleService.getAllVehicles(filterLowercaseKey), HttpStatus.OK);
    }

    @GetMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<Vehicle> getVehicleById(@NotNull @PathVariable Integer id){//if id is not integer type, it will return 400
        System.out.println("[Get one] | parameters: " + id);
        return new ResponseEntity<>(vehicleService.getVehicleById(id), HttpStatus.OK);
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<Vehicle> saveVehicle(@Valid @RequestBody Vehicle v){ //if any attribute in v is not valid type, it will return 400
        System.out.println("[Create]");
        return new ResponseEntity<>(vehicleService.saveVehicle(v), HttpStatus.CREATED);
    }

    @PutMapping(produces = "application/json")
    public ResponseEntity<Vehicle> updateVehicle(@Valid @RequestBody Vehicle v){
        System.out.println("[Update]");
        return new ResponseEntity<>(vehicleService.updateVehicle(v), HttpStatus.CREATED);
    }

    @DeleteMapping(path="/{id}", produces = "application/json")
    public ResponseEntity<Vehicle> deleteVehicleById(@NotNull @PathVariable Integer id){ //pathvariable here gaurantee id will mapping to variable in path
        System.out.println("[Delete] | parameters: " + id);
        return new ResponseEntity<>(vehicleService.deleteVehicleById(id), HttpStatus.OK);
    }

}
