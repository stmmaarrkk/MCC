package com.mcc.vehicle.service;

import com.mcc.vehicle.domain.Vehicle;
import com.mcc.vehicle.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VehicleServiceInMemoryTest {
    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private VehicleServiceValidator vehicleServiceValidator;
    @InjectMocks
    private VehicleServiceInMemory vehicleServiceInMemory;


    /*---------------------------------getOne---------------------------------*/
    @Test
    void getVehicleById_ReturnVehicle_ExistingId() {
        Integer id = 0;
        Vehicle vehicle = new Vehicle(id, 2015, "toyota", "camry");

        when(vehicleRepository.existsById(id)).thenReturn(true);
        when(vehicleRepository.findById(id)).thenReturn(Optional.of(vehicle));

        assertTrue(vehicleServiceInMemory.getVehicleById(id).equals(vehicle));
    }

    @Test
    void getVehicleById_ThrowRuntimeException_NotExistingId() {
        Integer id = 7;

        when(vehicleRepository.existsById(id)).thenReturn(false);
        assertThrows(VehicleNotFoundException.class, ()->{
            vehicleServiceInMemory.getVehicleById(id);
        });
    }


    /*---------------------------------getAll---------------------------------*/

    @Test
    void getAll_ReturnVehicle_NoFilter(){
        List<Vehicle> list = new ArrayList<>();
        list.add(new Vehicle(1, 2015,"toyota", "camry"));
        list.add(new Vehicle(2, 2011, "porsche", "911"));
        list.add(new Vehicle(3, 1995, "bmw", "x5"));

        Map<String, String> filter = Map.of();
        when(vehicleServiceValidator.checkFilter(filter)).thenReturn(true);

        Example<Vehicle> vehicleExample = Example.of(new Vehicle());
        when(vehicleRepository.findAll(vehicleExample)).thenReturn(list);

        assertTrue(vehicleServiceInMemory.getAllVehicles(filter).equals(list));
    }

    @Test
    void getAll_ReturnVehicle_ExistingVehicleWithFilter(){
        Integer year = 2015;
        List<Vehicle> list = new ArrayList<>();
        list.add(new Vehicle(1, year,"toyota", "camry"));
        list.add(new Vehicle(2, year, "porsche", "911"));

        Map<String, String> filter = Map.of("year", year.toString());
        when(vehicleServiceValidator.checkFilter(filter)).thenReturn(true);

        Example<Vehicle> vehicleExample = Example.of(new Vehicle(null, year, null, null));
        when(vehicleRepository.findAll(vehicleExample)).thenReturn(list);

        assertTrue(vehicleServiceInMemory.getAllVehicles(filter).equals(list));
    }

    @Test
    void getAll_ReturnEmptyList_NoExistingVehicle(){
        Integer year = 2030;
        List<Vehicle> list = new ArrayList<>();

        Map<String, String> filter = Map.of("year", year.toString());
        when(vehicleServiceValidator.checkFilter(filter)).thenReturn(true);

        Example<Vehicle> vehicleExample = Example.of(new Vehicle(null, year, null, null));
        when(vehicleRepository.findAll(vehicleExample)).thenReturn(list);

        assertTrue(vehicleServiceInMemory.getAllVehicles(filter).equals(new ArrayList<>()));
    }

    @Test
    void getAll_ThrowVehicleServiceValidationException_InvalidFilter(){

        Map<String, String> filter = Map.of("sadasdasd", "7788"); //sadasdasd is not a legal class attribute
        when(vehicleServiceValidator.checkFilter(filter)).thenReturn(false);

        assertThrows(VehicleServiceValidationException.class, ()->{
            vehicleServiceInMemory.getAllVehicles(filter);
        });

        verify(vehicleServiceValidator).checkFilter(filter);

    }



    /*---------------------------------create---------------------------------*/

    @Test
    void addOne_ThrowVehicleServiceValidationException_ExistingIdValidAttributes() {
        Integer id = 8;
        Vehicle vehicle = new Vehicle(id, 1980, "toyota", "camry");

        when(vehicleServiceValidator.checkYearRange(vehicle.getYear())).thenReturn(true);
        when(vehicleServiceValidator.checkMakeModelPair(vehicle.getMake(), vehicle.getModel())).thenReturn(true);
        when(vehicleRepository.existsById(id)).thenReturn(true);


        assertThrows(VehicleServiceValidationException.class, ()->{
            vehicleServiceInMemory.saveVehicle(vehicle);
        });
    }

    @Test
    void addOne_ReturnVehicle_NotExistingIdValidAttributes() {
        Integer id = 8;
        Vehicle vehicle = new Vehicle(id, 1980, "toyota", "camry");

        when(vehicleServiceValidator.checkYearRange(vehicle.getYear())).thenReturn(true);
        when(vehicleServiceValidator.checkMakeModelPair(vehicle.getMake(), vehicle.getModel())).thenReturn(true);
        when(vehicleRepository.existsById(id)).thenReturn(false);
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);

        assertTrue(vehicleServiceInMemory.saveVehicle(vehicle).equals(vehicle));
    }

    @Test
    void addOne_ThrowVehicleServiceValidationException_NotExistingIdInvalidAttributes() {
        Integer id = 8;
        Vehicle vehicle = new Vehicle(id, 2005, "toyota", "invalidModel"); //invalid Model

        when(vehicleServiceValidator.checkYearRange(vehicle.getYear())).thenReturn(true);
        when(vehicleServiceValidator.checkMakeModelPair(vehicle.getMake(), vehicle.getModel())).thenReturn(false);

        assertThrows(VehicleServiceValidationException.class, ()->{
            vehicleServiceInMemory.saveVehicle(vehicle);
        });
    }




    /*---------------------------------update---------------------------------*/
    @Test
    void updateVehicle_ReturnVehicle_ExistingIdValidAttributes() {
        Integer id = 1;
        Vehicle vehicle = new Vehicle(id, 1980, "toyota", "testModel");

        when(vehicleServiceValidator.checkYearRange(vehicle.getYear())).thenReturn(true);
        when(vehicleServiceValidator.checkMakeModelPair(vehicle.getMake(), vehicle.getModel())).thenReturn(true);
        when(vehicleRepository.existsById(id)).thenReturn(true);
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);

        assertTrue(vehicleServiceInMemory.updateVehicle(vehicle).equals(vehicle));

        verify(vehicleRepository).existsById(id); //check if data have been save to DB
    }

    @Test
    void updateVehicle_ThrowVehicleServiceValidationException_NotExistingIdValidAttributes() {
        Integer id = 8;
        Vehicle vehicle = new Vehicle(id, 1980, "toyota", "testModel");
        when(vehicleServiceValidator.checkYearRange(vehicle.getYear())).thenReturn(true);
        when(vehicleServiceValidator.checkMakeModelPair(vehicle.getMake(), vehicle.getModel())).thenReturn(true);
        when(vehicleRepository.existsById(vehicle.getId())).thenReturn(false);

        assertThrows(VehicleNotFoundException.class, ()->{
            vehicleServiceInMemory.updateVehicle(vehicle);
        });
    }

    @Test
    void updateVehicle_ThrowVehicleServiceValidationException_ExistingIdInvalidAttributes() {
        Integer id = 1;
        Vehicle vehicle = new Vehicle(id, 1966, "invalidMake", "testModel");
        when(vehicleServiceValidator.checkYearRange(vehicle.getYear())).thenReturn(false);

        assertThrows(VehicleServiceValidationException.class, ()->{
            vehicleServiceInMemory.updateVehicle(vehicle);
        });
    }

    //void updateVehicle_ThrowRuntimeException_ExistingIdNullEntities() { } //controller will not allowed

    /*---------------------------------delete---------------------------------*/

    @Test
    void deleteOne_ReturnVehicle_IdExisting() {
        Integer id = 0;
        Vehicle vehicle = new Vehicle(id, 2015, "toyota", "camry");

        when(vehicleRepository.existsById(id)).thenReturn(true);
        when(vehicleRepository.findById(id)).thenReturn(Optional.of(vehicle));
        //vehicleRepository.delete will return nothing

        assertTrue(vehicleServiceInMemory.deleteVehicleById(id).equals(vehicle));
    }
    @Test
    void deleteOne_ThrowRuntimeException_IdNotExisted() {
        Integer id = 7;
        Vehicle vehicle = new Vehicle(id, 2015, "toyota", "camry");

        when(vehicleRepository.existsById(id)).thenReturn(false);
        assertThrows(VehicleNotFoundException.class, ()->{
            vehicleServiceInMemory.deleteVehicleById(id);
        });
    }



}