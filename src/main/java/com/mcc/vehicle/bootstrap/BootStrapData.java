package com.mcc.vehicle.bootstrap;


import com.mcc.vehicle.domain.Vehicle;
import com.mcc.vehicle.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

public class BootStrapData implements CommandLineRunner {
    @Autowired
    private final VehicleRepository vehicleRepository;

    public BootStrapData(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public void run(String... args) throws Exception{
        System.out.println("start bootstrapppppp");
        Vehicle v1 = new Vehicle();
        v1.setId(0);
        v1.setYear(2015);
        v1.setMake("toyota");
        v1.setModel("camry");
        vehicleRepository.save(v1);


        Vehicle v2 = new Vehicle();
        v2.setId(1);
        v2.setYear(2011);
        v2.setMake("porsche");
        v2.setModel("911");
        vehicleRepository.save(v2);

        Vehicle v3 = new Vehicle();
        v3.setId(2);
        v3.setYear(2013);
        v3.setMake("bmw");
        v3.setModel("x5");
        vehicleRepository.save(v3);

        Vehicle v4 = new Vehicle();
        v4.setId(3);
        v4.setYear(2020);
        v4.setMake("mazda");
        v4.setModel("cx9");
        vehicleRepository.save(v4);
    }

}
