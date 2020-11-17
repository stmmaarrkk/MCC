package com.mcc.vehicle.repository;


import com.mcc.vehicle.domain.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> { }












//@Repository
//public class VehicleRepository {
//    //function in this class should be static, since Repository can only generate 1.
//    //Use Id as the key of a hash map to store the corresponding table
//    //We assume this part is always correct( Since we use reliable database in practice)
//    private static Map<Integer, Vehicle> vehicles = new HashMap<Integer, Vehicle>();
//
//    public VehicleRepository(){};
//    public VehicleRepository(Boolean testMode){
//        if(testMode) {
//            testCaseInitialization();
//        }
//    };
//
//    public static void testCaseInitialization(){
//        vehicles.clear();
//        Vehicle v1 = new Vehicle(1, 2015,"toyota", "camry");
//        Vehicle v2 = new Vehicle(2, 2011, "porsche", "911");
//        Vehicle v3 = new Vehicle(3, 1995, "bmw", "x5");
//        vehicles.put(v1.getId(), v1);
//        vehicles.put(v2.getId(), v2);
//        vehicles.put(v3.getId(), v3);
//    }
//
//    public static Vehicle getOne(Integer id) {
//        if(id == null)
//            throw new RuntimeException("id should not be null");
//        if(!vehicles.containsKey(id))
//            throw new RuntimeException("Queried id is not in DB");
//        return vehicles.get(id); //if id not in vehicle, Map will raise error, so we don't
//    }
//
//    public static List<Vehicle> getAll(Map<String, String> filter) {
//        Collection<Vehicle> c = vehicles.values();
//        List<Vehicle> itemList = new ArrayList<Vehicle>();
//        itemList.addAll(c);
//        //System.out.println(itemList);
//
//        //filter vehicle that don't meet requirements
//        if(filter.get("id") != null) {
//            System.out.println("filter id");
//            var condition = Integer.valueOf(filter.get("id"));
//            Stream<Vehicle> allitems = itemList.stream();
//            itemList = allitems.filter(vehicle -> vehicle.getId().equals(condition)).collect(Collectors.toList());
//            //System.out.println(itemList);
//        }
//        if(filter.get("year") != null) {
//            System.out.println("filter year");
//            var condition = Integer.valueOf(filter.get("year"));
//            Stream<Vehicle> allitems = itemList.stream();
//            itemList = allitems.filter(vehicle -> vehicle.getYear().equals(condition)).collect(Collectors.toList());
//
//        }
//        if(filter.get("make") != null) {
//            System.out.println("filter make");
//            var condition = filter.get("make");
//            Stream<Vehicle> allitems = itemList.stream();
//            itemList = allitems.filter(vehicle -> vehicle.getMake().equals(condition)).collect(Collectors.toList());
//            System.out.println(itemList);
//        }
//        if(filter.get("model") != null) {
//            System.out.println("filter model");
//            var condition = filter.get("model");
//            Stream<Vehicle> allitems = itemList.stream();
//            itemList = allitems.filter(vehicle -> vehicle.getModel().equals(condition)).collect(Collectors.toList());
//        }
//
//        if(itemList.isEmpty())
//            throw new RuntimeException("Not thing found");
//        return itemList;
//    }
//
//    //use id as key to store value
//    public static Vehicle addOne(Vehicle v) {
//        if(v.getId() == null)
//            throw new RuntimeException("Queried id should not be null");
//        if(vehicles.containsKey(v.getId()))
//            throw new RuntimeException("Queried id is already in DB");
//
//        vehicles.put(v.getId(), v);
//        return v;
//
//    }
//
//    //use id as key to store value
//    public static Vehicle updateOne(Vehicle v) {
//        if(v.getId() == null)
//            throw new RuntimeException("Queried id should not be null");
//        if(vehicles.containsKey(v.getId())) {
//            vehicles.put(v.getId(), v);
//            return v;
//        }else
//            throw new RuntimeException("Queried id is not in DB");
//    }
//
//    public static Vehicle deleteOne(Integer id) {
//        if(id == null)
//            throw new RuntimeException("Queried id should not be null");
//        if(!vehicles.containsKey(id))
//            throw new RuntimeException("Queried id is not in DB");
//        return vehicles.remove(id);
//    }
//
//}
