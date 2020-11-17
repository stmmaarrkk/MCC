package com.mcc.vehicle.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.*;

@Valid
@NoArgsConstructor
@AllArgsConstructor
@Inheritance
@Entity(name = "TRANSPORTATION")
@Data
abstract public class Transportation {
    //@ check only valid at controller
    //null is still a valid type of its entities
    @javax.persistence.Id
    @NotNull(message = "id should not be null")
    private Integer Id;
    @NotNull(message = "year should not be null")
    private Integer Year;
    @NotNull(message = "make should not be null")
    private String Make;
    @NotNull(message = "model should not be null")
    private String Model;
}
