package com.mcc.vehicle.service;


import com.mcc.vehicle.domain.Vehicle;

import java.util.*;


public interface VehicleService {
    Vehicle getVehicleById(Integer id);
    List<Vehicle> getAllVehicles(Map<String, String> filter);
    Vehicle updateVehicle(Vehicle v); //either id not found or invalid syntax will return null
    Vehicle saveVehicle(Vehicle v);
    Vehicle deleteVehicleById(Integer id);
}
