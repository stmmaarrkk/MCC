package com.mcc.vehicle.service;

import com.mcc.vehicle.domain.Vehicle;
import com.mcc.vehicle.exception.VehicleNotFoundException;
import com.mcc.vehicle.exception.VehicleServiceException;
import com.mcc.vehicle.exception.VehicleServiceValidationException;
import com.mcc.vehicle.repository.VehicleRepository;
import com.mcc.vehicle.validation.VehicleServiceValidator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
@Service
public class VehicleServiceInMemory implements VehicleService {

    @Autowired
    private final VehicleRepository vehicleRepository;

    @Autowired
    private final VehicleServiceValidator vehicleServiceValidator;

    @Override
    public Vehicle getVehicleById(Integer id) {
        /*Validation*/
        if(!vehicleRepository.existsById(id))
            throw new VehicleNotFoundException();

        /*Operation*/
        try{
            return vehicleRepository.findById(id).orElse(null);//item must exist
        }catch(RuntimeException e){
            throw new VehicleServiceException(e.getMessage());//Exception from repository
        }
    }

    @Override
    public List<Vehicle> getAllVehicles(Map<String, String> filter) {
        /*Validation*/
        //filter has gone through Nullity check, no duplicate key, turned into lowercase
        if(!vehicleServiceValidator.checkFilter(filter)) //check whether fields are legal
            throw new VehicleServiceValidationException("Illegal field in filter");

        /*Operation*/
        try {
            Vehicle v = new Vehicle();
            if (filter.get("id") != null)
                v.setId(Integer.valueOf(filter.get("id")));
            if (filter.get("year") != null)
                v.setYear(Integer.valueOf(filter.get("year")));
            if (filter.get("make") != null)
                v.setMake(filter.get("make"));
            if (filter.get("model") != null)
                v.setModel(filter.get("model"));

            Example<Vehicle> vehicleExample = Example.of(v);
            return vehicleRepository.findAll(vehicleExample);
        } catch (RuntimeException e){
            throw new VehicleServiceException(e.getMessage()); //Exception from repository or our logic
        }
    }

    @Override
    public Vehicle updateVehicle(Vehicle vehicle){
        /*Validation*/
        if(!vehicleServiceValidator.checkYearRange(vehicle.getYear()))
            throw new VehicleServiceValidationException("Year is invalid");
        if(!vehicleRepository.existsById(vehicle.getId()))
            throw new VehicleNotFoundException();

        /*Operation*/
        try {
            vehicleRepository.save(vehicle); //H2 will overwrite the existing data
            return vehicle;
        }catch(RuntimeException e){
            throw new VehicleServiceException(e.getMessage()); //Exception from repository
        }
    }

    @Override
    public Vehicle saveVehicle(Vehicle vehicle){
        /*Validation*/
        if(!vehicleServiceValidator.checkYearRange(vehicle.getYear()))
            throw new VehicleServiceValidationException("Year is invalid");
        if (vehicleRepository.existsById(vehicle.getId()))
            throw new VehicleServiceValidationException("Queried id is already in the DB");

        /*Operation*/
        try {
            vehicleRepository.save(vehicle);
            return vehicle;
        }catch(RuntimeException e){
            throw new VehicleServiceException(e.getMessage()); //Exception from repository
        }
    }

    @Override
    public Vehicle deleteVehicleById(Integer id){
        /*Validation*/
        if (!vehicleRepository.existsById(id))
            throw new VehicleNotFoundException();

        /*Operation*/
        try{
            var vehicle = vehicleRepository.findById(id).orElse(null); //item must exist
            vehicleRepository.deleteById(id);
            return vehicle;
        }catch(RuntimeException e){
            throw new VehicleServiceException(e.getMessage()); //Exception from repository
        }
    }
}
