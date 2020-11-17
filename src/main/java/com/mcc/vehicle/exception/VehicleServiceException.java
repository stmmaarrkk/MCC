package com.mcc.vehicle.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class VehicleServiceException extends RuntimeException{
    //Including the error caused by our in-mem DB(i.e. VehicleRepository)//
    public VehicleServiceException(){
        super("Meet unexpected exception");
        System.out.println("VehicleServiceException: " + "Meet unexpected exception");
    }
    public VehicleServiceException(String message){
        super(message);
        System.out.println("VehicleServiceException: "+message);
    }
}
