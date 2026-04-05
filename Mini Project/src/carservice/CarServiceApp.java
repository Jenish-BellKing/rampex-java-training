package carservice;

import carservice.center.ServiceCenter;
import carservice.exceptions.*;
import carservice.model.*;
import carservice.service.*;
import carservice.vehicle.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class CarServiceApp {

    public static void main(String[] args) {
        ServiceCenter center = new ServiceCenter("AutoElite Service Center");

        section("SETUP — Register Customers");
        Customer alice = null, bob = null, carol = null;
        try {
            alice = center.registerCustomer("Alice Johnson", "alice@example.com", "+919876500001", "12 Main St, Mumbai");
            bob   = center.registerCustomer("Bob Martinez",  "bob@example.com",   "+919876500002", "45 Park Ave, Delhi");
            carol = center.registerCustomer("Carol White",   "carol@example.com", "+919876500003", "7 Lake Rd, Pune");
        } catch (Exception e) {
            System.err.println("UNEXPECTED: " + e);
            return;
        }
        center.listAllCustomers();

        section("TEST 1 — Add Vehicles (Normal)");
        Car       aliceCar   = new Car("Toyota",   "Camry",      2021, "MH01AB1234", 25000, 4, "Petrol");
        Truck     bobTruck   = new Truck("Tata",    "Prima",      2019, "DL02CD5678", 80000, 10.5, false);
        Motorcycle carolBike = new Motorcycle("Royal Enfield", "Meteor 350", 2023, "MH12EF9999", 5000, 350, "Cruiser");
        try {
            center.addVehicle(alice.getCustomerId(), aliceCar);
            center.addVehicle(bob.getCustomerId(),   bobTruck);
            center.addVehicle(carol.getCustomerId(), carolBike);
        } catch (CarServiceException e) {
            System.err.println("UNEXPECTED: " + e);
        }
        center.listAllVehicles();

        section("TEST 2 — Build Services");
        OilChangeService syntheticOil  = new OilChangeService(OilChangeService.OilGrade.FULLY_SYNTHETIC);
        OilChangeService mineralOil    = new OilChangeService(OilChangeService.OilGrade.MINERAL);
        TireService      fourTires     = new TireService(4, true);
        TireService      twoTires      = new TireService(2, false);
        RepairService    brakeRepair   = new RepairService("Brake System Overhaul", 3.0, 2500.0);
        RepairService    engineRepair  = new RepairService("Engine Tune-up", 2.0, 1200.0);

        FullServicePackage carPackage = new FullServicePackage(
            "Car Full-Service Package",
            Arrays.asList(syntheticOil, fourTires, brakeRepair)
        );

        System.out.println("  " + syntheticOil);
        System.out.println("  " + fourTires);
        System.out.println("  " + brakeRepair);
        System.out.printf("  %s (base: %.2f, 15%% bundle discount)%n",
                          carPackage.getServiceName(), carPackage.getBasePrice());

        section("TEST 3 — Polymorphic Cost Calculation");
        Service[] services = { syntheticOil, mineralOil, fourTires, twoTires, brakeRepair, carPackage };
        Vehicle[] vehicles = { aliceCar, bobTruck, carolBike };
        System.out.printf("  %-30s %12s %12s %16s%n", "Service", "Car (1.0x)", "Truck (1.5x)", "Motorcycle (0.7x)");
        System.out.println("  " + "─".repeat(74));
        for (Service svc : services) {
            System.out.printf("  %-30s %12.2f %12.2f %16.2f%n",
                truncate(svc.getServiceName(), 30),
                svc.calculateCost(aliceCar),
                svc.calculateCost(bobTruck),
                svc.calculateCost(carolBike));
        }

        section("TEST 4 — Schedule Services (Normal)");
        ServiceRecord r1 = null, r2 = null, r3 = null, r4 = null;
        try {
            r1 = center.scheduleService(alice.getCustomerId(), aliceCar.getVehicleId(),
                        syntheticOil, LocalDate.now().plusDays(1), "Rajan Kumar");
            r2 = center.scheduleService(alice.getCustomerId(), aliceCar.getVehicleId(),
                        fourTires,    LocalDate.now().plusDays(2), "Suresh Nair");
            r3 = center.scheduleService(bob.getCustomerId(),   bobTruck.getVehicleId(),
                        brakeRepair,  LocalDate.now().plusDays(1), "Mohan Das");
            r4 = center.scheduleService(carol.getCustomerId(), carolBike.getVehicleId(),
                        twoTires,     LocalDate.now().plusDays(3), "Rajan Kumar");
        } catch (CarServiceException e) {
            System.err.println("UNEXPECTED: " + e);
        }

        section("TEST 5 — Update Service Status");
        try {
            center.updateServiceStatus(r1.getRecordId(), ServiceStatus.IN_PROGRESS);
            center.updateServiceStatus(r1.getRecordId(), ServiceStatus.COMPLETED);
            center.updateServiceStatus(r2.getRecordId(), ServiceStatus.COMPLETED);
            center.updateServiceStatus(r3.getRecordId(), ServiceStatus.COMPLETED);
        } catch (CarServiceException e) {
            System.err.println("UNEXPECTED: " + e);
        }

        section("TEST 6 — Edge Case: Change Status of Completed Record");
        try {
            center.updateServiceStatus(r1.getRecordId(), ServiceStatus.CANCELLED);
            System.err.println("  ERROR: Should have thrown InvalidServiceException!");
        } catch (CarServiceException e) {
            System.out.println("  ✔ Caught expected: " + e);
        }

        section("TEST 7 — Edge Case: Past Appointment Date");
        try {
            center.scheduleService(alice.getCustomerId(), aliceCar.getVehicleId(),
                    engineRepair, LocalDate.now().minusDays(1), "Rajan Kumar");
            System.err.println("  ERROR: Should have thrown InvalidServiceException!");
        } catch (CarServiceException e) {
            System.out.println("  ✔ Caught expected: " + e);
        }

        section("TEST 8 — Failure: Duplicate Customer Email");
        try {
            center.registerCustomer("Alice Clone", "alice@example.com", "+911234567890", "Fake St");
            System.err.println("  ERROR: Should have thrown DuplicateEntryException!");
        } catch (CarServiceException e) {
            System.out.println("  ✔ Caught expected: " + e);
        }

        section("TEST 9 — Failure: Duplicate License Plate");
        try {
            Car duplicate = new Car("Honda", "City", 2020, "MH01AB1234", 10000, 4, "Diesel");
            center.addVehicle(alice.getCustomerId(), duplicate);
            System.err.println("  ERROR: Should have thrown DuplicateEntryException!");
        } catch (CarServiceException e) {
            System.out.println("  ✔ Caught expected: " + e);
        }

        section("TEST 10 — Failure: Vehicle Not Found");
        try {
            center.getServiceHistory("VEH999999");
            System.err.println("  ERROR: Should have thrown VehicleNotFoundException!");
        } catch (CarServiceException e) {
            System.out.println("  ✔ Caught expected: " + e);
        }

        section("TEST 11 — Failure: Customer Not Found");
        try {
            center.findCustomer("CUST999999");
            System.err.println("  ERROR: Should have thrown CustomerNotFoundException!");
        } catch (CarServiceException e) {
            System.out.println("  ✔ Caught expected: " + e);
        }

        section("TEST 12 — Failure: Invalid Vehicle Data");
        try {
            new Car("", "Camry", 2021, "TN01XX0000", 0, 4, "Petrol");
            System.err.println("  ERROR: Should have thrown IllegalArgumentException!");
        } catch (IllegalArgumentException e) {
            System.out.println("  ✔ Caught expected IllegalArgumentException: " + e.getMessage());
        }
        try {
            new Car("Toyota", "Camry", 1800, "TN01XX0001", 0, 4, "Petrol");
            System.err.println("  ERROR: Should have thrown IllegalArgumentException for invalid year!");
        } catch (IllegalArgumentException e) {
            System.out.println("  ✔ Caught expected IllegalArgumentException: " + e.getMessage());
        }
        try {
            new RepairService("Engine fix", -2.0, 1000.0);
            System.err.println("  ERROR: Should have thrown IllegalArgumentException for negative hours!");
        } catch (IllegalArgumentException e) {
            System.out.println("  ✔ Caught expected IllegalArgumentException: " + e.getMessage());
        }

        section("TEST 13 — Advanced: Full-Service Package on a Truck");
        ServiceRecord r5 = null;
        try {
            r5 = center.scheduleService(bob.getCustomerId(), bobTruck.getVehicleId(),
                    carPackage, LocalDate.now().plusDays(5), "Suresh Nair");
            System.out.printf("  Package cost for Truck (1.5x): %.2f | Duration: %d min%n",
                    carPackage.calculateCost(bobTruck),
                    carPackage.getEstimatedDurationMinutes(bobTruck));
            System.out.println("  " + carPackage.getServiceDescription());
        } catch (CarServiceException e) {
            System.err.println("UNEXPECTED: " + e);
        }

        section("TEST 14 — Service History");
        try {
            center.printServiceHistory(aliceCar.getVehicleId());
        } catch (CarServiceException e) {
            System.err.println("UNEXPECTED: " + e);
        }

        section("TEST 15 — Generate Invoice (with Loyalty Discount)");
        try {
            Invoice aliceInvoice = center.generateInvoice(
                alice.getCustomerId(),
                List.of(r1.getRecordId(), r2.getRecordId()));
            aliceInvoice.print();
        } catch (CarServiceException e) {
            System.err.println("UNEXPECTED: " + e);
        }

        section("TEST 16 — Generate Invoice (Bob, no loyalty discount yet)");
        try {
            center.updateServiceStatus(r3.getRecordId(), ServiceStatus.COMPLETED);
            Invoice bobInvoice = center.generateInvoice(
                bob.getCustomerId(),
                List.of(r3.getRecordId()));
            bobInvoice.print();
        } catch (CarServiceException e) {
            System.err.println("UNEXPECTED: " + e);
        }

        section("SUMMARY");
        System.out.printf("  Total Customers     : %d%n", center.getTotalCustomers());
        System.out.printf("  Total Vehicles      : %d%n", center.getTotalVehicles());
        System.out.printf("  Total Service Records: %d%n", center.getTotalServiceRecords());
    }

    private static void section(String title) {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.printf( "║  %-56s║%n", title);
        System.out.println("╚══════════════════════════════════════════════════════════╝");
    }

    private static String truncate(String s, int max) {
        return s.length() <= max ? s : s.substring(0, max - 1) + "…";
    }
}
