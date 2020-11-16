package com.mcc.vehicle.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class VehicleServiceValidatorTest {
    VehicleServiceValidator vehicleServiceValidator = new VehicleServiceValidator();

    @Test
    void checkYearRange_ReturnTrue_ValidYear() {
        Integer upperEdge = vehicleServiceValidator.getValidYearInterval().get(1);
        Integer lowerEdge = vehicleServiceValidator.getValidYearInterval().get(0);
        Integer middle = (upperEdge + lowerEdge) / 2;
        assertTrue(vehicleServiceValidator.checkYearRange(upperEdge));
        assertTrue(vehicleServiceValidator.checkYearRange(lowerEdge ));
        assertTrue(vehicleServiceValidator.checkYearRange(middle));
    }

    @Test
    void checkYearRange_ReturnFalse_InvalidYear() {
        Integer lessOne = vehicleServiceValidator.getValidYearInterval().get(0) - 1;
        Integer largerOne = vehicleServiceValidator.getValidYearInterval().get(1) + 1;
        Integer negative = -100;
        assertFalse(vehicleServiceValidator.checkYearRange(lessOne));
        assertFalse(vehicleServiceValidator.checkYearRange(largerOne));
        assertFalse(vehicleServiceValidator.checkYearRange(negative));
    }


    @Test
    void checkMakeModelPair_ReturnTrue_ValidPair() {
        var validMakeModelPair = vehicleServiceValidator.getValidMakeModelPair();
        for (Map.Entry<String,String> entry : validMakeModelPair.entrySet())
            assertTrue(vehicleServiceValidator.checkMakeModelPair(entry.getValue(), entry.getKey()));

    }
    @Test
    void checkMakeModelPair_ReturnFalse_InvalidPair() {
        var validMakeModelPair = vehicleServiceValidator.getValidMakeModelPair();

        for (Map.Entry<String,String> entry : validMakeModelPair.entrySet())
            //add symbol make make illegal
            assertFalse(vehicleServiceValidator.checkMakeModelPair(entry.getValue()+"&#$(*%", entry.getKey()));
        for (Map.Entry<String,String> entry : validMakeModelPair.entrySet())
            //add symbol make model illegal
            assertFalse(vehicleServiceValidator.checkMakeModelPair(entry.getValue(), entry.getKey()+"&#$(*%"));
    }

    @Test
    void checkFilter_ReturnTrue_ValidFilter() {
        var validFilterFields = vehicleServiceValidator.getValidFilterFields();

        Map<String, String> filter= new HashMap<>();

        //iterate every valid field
        for (var field: validFilterFields) {
            filter.put(field, "0" ); //filter item get an extra entry in each iteration
            assertTrue(vehicleServiceValidator.checkFilter(filter));
        }
    }
    @Test
    void checkFilter_ReturnFalse_InvalidFilter() {
        var validFilterFields = vehicleServiceValidator.getValidFilterFields();

        //iterate every valid field
        for (var field: validFilterFields) {
            //test only one entry each iteration
            assertFalse(vehicleServiceValidator.checkFilter(Map.of(field+"&#$(*%", "0" )));
        }
    }
}