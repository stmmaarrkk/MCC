package com.mcc.vehicle.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity(name = "VEHICLE")
public class Vehicle extends Transportation {
    public Vehicle() {
        super();
    }

    public Vehicle(Integer Id, Integer Year, String Make, String Model) {
        super(Id, Year, Make, Model);
    }
}



//@Valid
//@Data
//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//public class Vehicle{
//    //@ check only valid at controller
//    //null is still a valid type of its entities
//    @javax.persistence.Id
//    @NotNull(message = "id should not be null")
//    private Integer Id;
//    @NotNull(message = "year should not be null")
//    private Integer Year;
//    @NotNull(message = "make should not be null")
//    private String Make;
//    @NotNull(message = "model should not be null")
//    private String Model;
//}
