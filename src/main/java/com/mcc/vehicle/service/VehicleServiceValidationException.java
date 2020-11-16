package com.mcc.vehicle.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class VehicleServiceValidationException extends RuntimeException{
    public VehicleServiceValidationException(){
        super();
        System.out.println("VehicleServiceValidationException");
    }
    public VehicleServiceValidationException(String message){
        super(message);
        System.out.println("VehicleServiceValidationException: "+message);
    }
}
