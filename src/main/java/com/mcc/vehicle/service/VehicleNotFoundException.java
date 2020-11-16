package com.mcc.vehicle.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException(){
        super("Queried id is not in the DB");
        System.out.println("VehicleNotFoundException: "+ "Queried id is not in the DB");
    }
    public VehicleNotFoundException(String message){
        super(message);
        System.out.println("VehicleNotFoundException: "+message);
    }
}
