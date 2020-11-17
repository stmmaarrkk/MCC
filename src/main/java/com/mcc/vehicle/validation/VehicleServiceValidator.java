package com.mcc.vehicle.validation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@AllArgsConstructor
public class VehicleServiceValidator {
    private  final List<Integer> ValidYearInterval = List.of(1950, 2050);
    private final Set<String> ValidFilterAttributes = Set.of(
            "id",
            "year",
            "make",
            "model"
    );
    
    
    public List<Integer> getValidYearInterval() {
        return ValidYearInterval;
    }
    
    public Set<String> getValidFilterAttributes() {
        return ValidFilterAttributes;
    }

    public Boolean checkYearRange(Integer year){
        return year != null && (year >= ValidYearInterval.get(0) && year <= ValidYearInterval.get(1));
    }
    public Boolean checkFilter(Map<String, String> filter){
        for(String k : filter.keySet()){
            if(!ValidFilterAttributes.contains(k))//illegal filter attributes
                return false;
        }
        return true;
    }
}
