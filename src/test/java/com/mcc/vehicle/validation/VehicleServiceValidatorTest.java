package com.mcc.vehicle.validation;

import com.mcc.vehicle.validation.VehicleServiceValidator;
import org.junit.jupiter.api.Test;

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
    void checkFilter_ReturnTrue_ValidFilter() {
        var validFilterAttributes = vehicleServiceValidator.getValidFilterAttributes();

        Map<String, String> filter= new HashMap<>();

        //iterate every valid attribute
        for (var field: validFilterAttributes) {
            filter.put(field, "0" ); //filter get an extra entry in each iteration
            assertTrue(vehicleServiceValidator.checkFilter(filter));
        }
    }

    @Test
    void checkFilter_ReturnFalse_InvalidFilter() {
        var validFilterAttributes = vehicleServiceValidator.getValidFilterAttributes();

        //iterate every valid attribute
        for (var field: validFilterAttributes) {
            //test only one entry each iteration
            assertFalse(vehicleServiceValidator.checkFilter(Map.of(field+"&#$(*%", "0" )));
        }

    }
}