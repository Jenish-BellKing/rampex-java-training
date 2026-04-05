package carservice.vehicle;

import carservice.model.VehicleType;

public class Motorcycle extends Vehicle {

    private int engineDisplacementCC;
    private String bikeType;

    public Motorcycle(String make, String model, int year,
                      String licensePlate, int mileage,
                      int engineDisplacementCC, String bikeType) {
        super(make, model, year, licensePlate, mileage, VehicleType.MOTORCYCLE);
        setEngineDisplacementCC(engineDisplacementCC);
        setBikeType(bikeType);
    }

    @Override
    public String getVehicleDescription() {
        return String.format("%s %s %d (%dcc %s)",
                             getMake(), getModel(), getYear(),
                             engineDisplacementCC, bikeType);
    }

    @Override
    public String getSpecificDetails() {
        return String.format("Engine: %d CC | Type: %s", engineDisplacementCC, bikeType);
    }

    public int getEngineDisplacementCC() { return engineDisplacementCC; }
    public String getBikeType()          { return bikeType; }

    public void setEngineDisplacementCC(int cc) {
        if (cc <= 0)
            throw new IllegalArgumentException("Engine displacement must be positive.");
        this.engineDisplacementCC = cc;
    }

    public void setBikeType(String bikeType) {
        if (bikeType == null || bikeType.isBlank())
            throw new IllegalArgumentException("Bike type cannot be blank.");
        this.bikeType = bikeType.trim();
    }
}
