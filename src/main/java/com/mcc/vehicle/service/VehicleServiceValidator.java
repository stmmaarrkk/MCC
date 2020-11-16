package com.mcc.vehicle.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@AllArgsConstructor
public class VehicleServiceValidator {
    private  final List<Integer> ValidYearInterval = List.of(1950, 2050);
    private final Map<String,String> ValidMakeModelPair = Map.of(
            "corolla", "toyota",
            "camry", "toyota",
            "yaris", "toyota",
            "118i", "bmw",
            "x5","bmw",
            "rx450h", "lexus",
            "ls500", "lexus",
            "911","porsche",
            "cx9", "mazda"
    );
    private final Set<String> ValidFilterFields = Set.of(
            "id",
            "year",
            "make",
            "model"
    );
    
    
    public List<Integer> getValidYearInterval() {
        return ValidYearInterval;
    }

    public Map<String, String> getValidMakeModelPair() {
        return ValidMakeModelPair;
    }
    
    public Set<String> getValidFilterFields() {
        return ValidFilterFields;
    }

    public Boolean checkYearRange(Integer year){
        return (year == null) ? false : (year >= ValidYearInterval.get(0) && year <= ValidYearInterval.get(1));
    }
    public Boolean checkMakeModelPair(String make, String model){
        //Use vehicle.model to check whether itself is in the map
        //if not, or its corresponding value to the key is not equal to that of the vehicle, return false
        return (make == null || model == null)? false : make.equals(ValidMakeModelPair.get(model));
    }
    public Boolean checkFilter(Map<String, String> filter){
        for(String k : filter.keySet()){
            if(!ValidFilterFields.contains(k))//illegal filter field
                return false;
        }
        return true;
    }
}
