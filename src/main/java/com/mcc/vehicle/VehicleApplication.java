package com.mcc.vehicle;

import com.mcc.vehicle.controller.VehicleController;
import com.mcc.vehicle.domain.Vehicle;
import com.mcc.vehicle.repository.VehicleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class VehicleApplication {

    public static void main(String[] args) {
        SpringApplication.run(VehicleApplication.class, args);
    }

    @Bean
    public CommandLineRunner vehicleDemo(VehicleRepository vehicleRepository) {
        return (args) -> {

            System.out.println("start bootstrapppppp");

            vehicleRepository.save(new Vehicle(0, 2015,"toyota", "camry" ));
            vehicleRepository.save(new Vehicle(1, 2015,"porsche", "911" ));
            vehicleRepository.save(new Vehicle(2, 2013,"bmw", "x5" ));
            vehicleRepository.save(new Vehicle(3, 2020,"mazda", "cx9" ));
            vehicleRepository.save(new Vehicle(4, 2011,"lexus", "rx450h" ));
            vehicleRepository.save(new Vehicle(5, 1996,"toyota", "corolla" ));
            vehicleRepository.save(new Vehicle(6, 2015,"toyota", "yaris" ));
            vehicleRepository.save(new Vehicle(7, 2013,"porsche", "911" ));
            vehicleRepository.save(new Vehicle(8, 2020,"bmw", "118i" ));
            vehicleRepository.save(new Vehicle(9, 2010,"mazda", "cx9" ));


            // fetch all vehicles
            System.out.println("Vehicles found with findAll():");
            System.out.println("---------------------------");
            for (Vehicle vehicle : vehicleRepository.findAll()) {
                System.out.println(vehicle.toString());
            }
            System.out.println();

            // fetch vehicle by id
            Vehicle vehicle = vehicleRepository.findById(1).get();
            System.out.println("Vehicle found with findById(1L):");
            System.out.println("-----------------------------");
            System.out.println(vehicle.toString());
            System.out.println();

//            // update vehicle title
//            Vehicle vehicleUpdate = vehicleRepository.findById(2L).get();
//            vehicleUpdate.setTitle("Java Gently 2nd Edition");
//            vehicleRepository.save(vehicleUpdate);
//            System.out.println("Update vehicle title:");
//            System.out.println("------------------");
//            System.out.println(vehicleUpdate.toString());
//            System.out.println();

            // total vehicles in DB
            System.out.println("Total vehicles in DB:");
            System.out.println("------------------");
            System.out.println(vehicleRepository.count());
            System.out.println();
        };
    }

}
