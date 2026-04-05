package carservice.vehicle;

import carservice.model.VehicleType;

import java.util.concurrent.atomic.AtomicLong;

public abstract class Vehicle {

    private static final AtomicLong SEQUENCE = new AtomicLong(5001L);

    private final String vehicleId;
    private String make;
    private String model;
    private int year;
    private String licensePlate;
    private int mileage;
    private final VehicleType vehicleType;

    protected Vehicle(String make, String model, int year,
                      String licensePlate, int mileage, VehicleType vehicleType) {
        setMake(make);
        setModel(model);
        setYear(year);
        setLicensePlate(licensePlate);
        setMileage(mileage);
        if (vehicleType == null) throw new IllegalArgumentException("VehicleType cannot be null.");
        this.vehicleId   = "VEH" + SEQUENCE.getAndIncrement();
        this.vehicleType = vehicleType;
    }

    public abstract String getVehicleDescription();

    public abstract String getSpecificDetails();

    public final double getServiceCostMultiplier() {
        return vehicleType.getServiceCostMultiplier();
    }

    public String getVehicleId()       { return vehicleId; }
    public String getMake()            { return make; }
    public String getModel()           { return model; }
    public int getYear()               { return year; }
    public String getLicensePlate()    { return licensePlate; }
    public int getMileage()            { return mileage; }
    public VehicleType getVehicleType(){ return vehicleType; }

    public void setMake(String make) {
        if (make == null || make.isBlank())
            throw new IllegalArgumentException("Make cannot be blank.");
        this.make = make.trim();
    }

    public void setModel(String model) {
        if (model == null || model.isBlank())
            throw new IllegalArgumentException("Model cannot be blank.");
        this.model = model.trim();
    }

    public void setYear(int year) {
        int currentYear = java.time.Year.now().getValue();
        if (year < 1886 || year > currentYear + 1)
            throw new IllegalArgumentException(
                "Year must be between 1886 and " + (currentYear + 1) + ".");
        this.year = year;
    }

    public void setLicensePlate(String licensePlate) {
        if (licensePlate == null || licensePlate.isBlank())
            throw new IllegalArgumentException("License plate cannot be blank.");
        this.licensePlate = licensePlate.trim().toUpperCase();
    }

    public void setMileage(int mileage) {
        if (mileage < 0)
            throw new IllegalArgumentException("Mileage cannot be negative.");
        this.mileage = mileage;
    }

    @Override
    public String toString() {
        return String.format("%s | %s %s %d | Plate: %s | Mileage: %d km",
                             vehicleType, make, model, year, licensePlate, mileage);
    }
}
