package com.mcc.vehicle;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcc.vehicle.domain.Vehicle;
import com.mcc.vehicle.repository.VehicleRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VehicleControllerIntegrationTests {
    public static final String BASE_URL = "/vehicles";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Test
    @Order(1)
    void getAllVehicles_ReturnVehicles_NoFilter() throws Exception {

        RequestBuilder request = MockMvcRequestBuilders.get(BASE_URL).contentType(MediaType.APPLICATION_JSON);
        var res = mvc.perform(request).andReturn().getResponse();

        //Http Status
        Integer status = res.getStatus();
        assertTrue(status.equals(200), ()->"Http Status should be OK");


        //reponse body
        List<Vehicle> vehicles = new ObjectMapper().readValue(res.getContentAsString(), new TypeReference<>(){});
        List<Vehicle> expectList = vehicleRepository.findAll();

        Collections.sort(vehicles, (d1, d2) -> {
            return d1.getId() - d2.getId();
        });

        Collections.sort(expectList, (d1, d2) -> {
            return d1.getId() - d2.getId();
        });

        assertTrue(vehicles.equals(expectList), ()->"Body must be the same");

    }
    @Test
    @Order(2)
    void getAllVehicles_ReturnOK_Filter() throws Exception{

        Vehicle v1 = new Vehicle(null, 2015, "toyota", null);

        RequestBuilder request1 = MockMvcRequestBuilders.get(BASE_URL)
                .param("year", v1.getYear().toString())
                .param("make", v1.getMake());

        Example<Vehicle> vehicleExample1 = Example.of(v1); //create example


        var res1 = mvc.perform(request1).andReturn().getResponse();
        //Http Status
        Integer status = res1.getStatus();
        assertTrue(status.equals(200), ()->"Http Status should be OK");

        //reponse body
        List<Vehicle> vehicles1 = new ObjectMapper().readValue(res1.getContentAsString(), new TypeReference<>(){});
        List<Vehicle> expectList1 = vehicleRepository.findAll(vehicleExample1);

        Collections.sort(vehicles1, (d1, d2) -> {
            return d1.getId() - d2.getId();
        });

        Collections.sort(expectList1, (d1, d2) -> {
            return d1.getId() - d2.getId();
        });

        assertTrue(vehicles1.equals(expectList1), ()->"Body must be the same");


        //Case 2, return empty list
        Vehicle v2 = new Vehicle(-1, null, null, null);
        RequestBuilder request2 = MockMvcRequestBuilders.get(BASE_URL)
                .param("id", v2.getId().toString());

        Example<Vehicle> vehicleExample2 = Example.of(v2); //create example


        var res2 = mvc.perform(request2).andReturn().getResponse();
        //Http Status
        Integer status2 = res2.getStatus();
        assertTrue(status.equals(200), ()->"Http Status should be OK");

        //reponse body
        List<Vehicle> vehicles2 = new ObjectMapper().readValue(res2.getContentAsString(), new TypeReference<>(){});
        List<Vehicle> expectList2 = vehicleRepository.findAll(vehicleExample2);

        Collections.sort(vehicles2, (d1, d2) -> {
            return d1.getId() - d2.getId();
        });

        Collections.sort(expectList2, (d1, d2) -> {
            return d1.getId() - d2.getId();
        });

        assertTrue(vehicles2.isEmpty(),  ()->"Body must be empty list");
        assertTrue(vehicles2.equals(expectList2), ()->"Body must be the same");
    }

    @Test
    @Order(3)
    void getAllVehicles_ReturnNotFound_NullFilter() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders.get(BASE_URL)
                .param("id", "")
                .param("model", "Camry");

        var res = mvc.perform(request).andReturn().getResponse();
        //Http Status
        Integer status = res.getStatus();
        assertTrue(status.equals(400), ()->"Http Status should be Bad request");
    }

    @Test
    @Order(3)
    void getAllVehicles_ReturnOK_FilterContainCapitalLetter() throws Exception{
        Vehicle v = new Vehicle(null, null, null, "CAMry");
        RequestBuilder request = MockMvcRequestBuilders.get(BASE_URL) //no qualified object
                .param("model", v.getModel());

        v.setModel(v.getModel().toLowerCase());
        Example<Vehicle> vehicleExample = Example.of(v); //create example

        var res = mvc.perform(request).andReturn().getResponse();

        //Http Status
        Integer status = res.getStatus();
        assertTrue(status.equals(200), ()->"Http Status should be OK");

        //reponse body
        List<Vehicle> vehicles = new ObjectMapper().readValue(res.getContentAsString(), new TypeReference<>(){});
        List<Vehicle> expectList = vehicleRepository.findAll(vehicleExample);

        assertTrue(vehicles.equals(expectList), ()->"Body must be the same");
    }


    @Test
    @Order(4)
    void getVehicleById_ReturnOK_ExistingId() throws Exception{
        Integer id = 1;
        RequestBuilder request = MockMvcRequestBuilders.get(BASE_URL + "/{id}", id);

        var res = mvc.perform(request).andReturn().getResponse();
        //Http Status
        Integer status = res.getStatus();
        assertEquals((int)status, 200, () -> "Http Status should be OK");

        Vehicle vehicle = new ObjectMapper().readValue(res.getContentAsString(), Vehicle.class);
        Vehicle expect = vehicleRepository.findById(id).orElse(null);

        assertEquals(expect, vehicle, () -> "Body must be the same");
    }

    @Test
    @Order(5)
    void getVehicleById_ReturnNotFound_NotExistingId() throws Exception {
        Integer id = 100;
        RequestBuilder request = MockMvcRequestBuilders.get(BASE_URL + "/{id}", id);

        var res = mvc.perform(request).andReturn().getResponse();

        //Http Status
        Integer status = res.getStatus();
        assertTrue(status.equals(404), ()->"Http Status should be Not found");
    }

    @Test
    @Order(5)
    void getVehicleById_ReturnBadRequest_InvalidId() throws Exception{
        String id = "aaa";
        RequestBuilder request = MockMvcRequestBuilders.get(BASE_URL + "/{id}", id);

        var res = mvc.perform(get(BASE_URL+ "/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    @Order(6)
    void saveVehicle_ReturnBadRequest_NonExistentIdInvalidEntities() throws Exception {
        Vehicle v = new Vehicle(100, 1966, "", "camry"); //empty make

        //Converting the Object to JSONString
        String jsonString = new ObjectMapper().writeValueAsString(v);

        var res = mvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isBadRequest());
    }


    @Test
    @Order(7)
    void saveVehicle_ReturnCreated_ExtraFields() throws Exception {

        //Converting the Object to JSONString
        String jsonString = new ObjectMapper().writeValueAsString(Map.of(
                "id", 990,
                "year", 1990,
                "make", "toyota",
                "model", "yaris",
                "extrakey", "extravalue"));

        var res = mvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isCreated());

    }

    @Test
    @Order(7)
    void saveVehicle_ReturnBadRequest_NotEnoughFields() throws Exception {

        //Converting the Object to JSONString
        String jsonString = new ObjectMapper().writeValueAsString(Map.of(
                "id", 990,
                "year", 1990,
                "make", "toyota"));

        var res = mvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isBadRequest());

    }

    @Test
    @Order(7)
    void saveVehicle_ReturnCreated_NonExistentId() throws Exception {
        Vehicle v = new Vehicle(101, 1966, "toyota", "camry");

        //Converting the Object to JSONString
        String jsonString = new ObjectMapper().writeValueAsString(v);

        var res = mvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isCreated());
    }


    @Test
    @Order(8)
    void updateVehicle_ReturnNotFound_NonExistentId() throws Exception {
        Vehicle v = new Vehicle(400, 1966, "toyota", "camry");

        String jsonString = new ObjectMapper().writeValueAsString(v);

        var res = mvc.perform(put(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(8)
    void updateVehicle_ReturnCreated_ExistingId() throws Exception {
        Vehicle v = new Vehicle(1, 1966, "toyota", "camry");

        //Converting the Object to JSONString
        String jsonString = new ObjectMapper().writeValueAsString(v);

        var res = mvc.perform(put(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(8)
    void updateVehicle_ReturnCreated_ExtraFields() throws Exception {

        //Converting the Object to JSONString
        String jsonString = new ObjectMapper().writeValueAsString(Map.of(
                "id", 1,
                "year", 1990,
                "make", "toyota",
                "model", "yaris",
                "extrakey", "extravalue"));

        var res = mvc.perform(put(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isCreated());

    }

    @Test
    @Order(8)
    void updateVehicle_ReturnBadRequest_NotEnoughFields() throws Exception {

        //Converting the Object to JSONString
        String jsonString = new ObjectMapper().writeValueAsString(Map.of(
                "id", 1,
                "year", 1990,
                "make", "toyota"));

        var res = mvc.perform(put(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isBadRequest());

    }



    @Test
    @Order(9)
    void deleteVehicleById_ReturnNotFund_NonExistentId() throws Exception {
        Integer id = 4041;
        RequestBuilder request = MockMvcRequestBuilders.delete(BASE_URL + "/{id}", id);

        var res = mvc.perform(request).andReturn().getResponse();
        //Http Status
        Integer status = res.getStatus();
        assertTrue(status.equals(404), ()->"Http Status should be Not found");
    }

    @Test
    @Order(10)
    void deleteVehicleById_ReturnOK_ExistingId() throws Exception{
        Integer id = 4;
        RequestBuilder request = MockMvcRequestBuilders.delete(BASE_URL + "/{id}", id);

        var res = mvc.perform(request).andReturn().getResponse();
        //Http Status
        Integer status = res.getStatus();

        assertTrue(status.equals(200), ()->"Http Status should be OK");
        assertNull(vehicleRepository.findById(id).orElse(null));
    }
    
    @Test
    @Order(10)
    void deleteVehicleById_ReturnBadRequest_InvalidId() throws Exception{
        String id = "a";
        RequestBuilder request = MockMvcRequestBuilders.delete(BASE_URL + "/{id}", id);

        var res = mvc.perform(delete(BASE_URL+ "/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
