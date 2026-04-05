package carservice.vehicle;

import carservice.model.VehicleType;

public class Car extends Vehicle {

    private int numberOfDoors;
    private String fuelType;

    public Car(String make, String model, int year,
               String licensePlate, int mileage,
               int numberOfDoors, String fuelType) {
        super(make, model, year, licensePlate, mileage, VehicleType.CAR);
        setNumberOfDoors(numberOfDoors);
        setFuelType(fuelType);
    }

    @Override
    public String getVehicleDescription() {
        return String.format("%d-Door %s %s %s (%s)", numberOfDoors, getYear(), getMake(), getModel(), fuelType);
    }

    @Override
    public String getSpecificDetails() {
        return String.format("Doors: %d | Fuel: %s", numberOfDoors, fuelType);
    }

    public int getNumberOfDoors() { return numberOfDoors; }
    public String getFuelType()   { return fuelType; }

    public void setNumberOfDoors(int doors) {
        if (doors < 2 || doors > 6)
            throw new IllegalArgumentException("Number of doors must be between 2 and 6.");
        this.numberOfDoors = doors;
    }

    public void setFuelType(String fuelType) {
        if (fuelType == null || fuelType.isBlank())
            throw new IllegalArgumentException("Fuel type cannot be blank.");
        this.fuelType = fuelType.trim();
    }
}
