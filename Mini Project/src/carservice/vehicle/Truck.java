package carservice.vehicle;

import carservice.model.VehicleType;

public class Truck extends Vehicle {

    private double payloadCapacityTons;
    private boolean isArticulated;

    public Truck(String make, String model, int year,
                 String licensePlate, int mileage,
                 double payloadCapacityTons, boolean isArticulated) {
        super(make, model, year, licensePlate, mileage, VehicleType.TRUCK);
        setPayloadCapacityTons(payloadCapacityTons);
        this.isArticulated = isArticulated;
    }

    @Override
    public String getVehicleDescription() {
        return String.format("%s %s %d Truck (%.1f-ton, %s)",
                             getMake(), getModel(), getYear(),
                             payloadCapacityTons,
                             isArticulated ? "Articulated" : "Rigid");
    }

    @Override
    public String getSpecificDetails() {
        return String.format("Payload: %.1f tons | Articulated: %s",
                             payloadCapacityTons, isArticulated ? "Yes" : "No");
    }

    public double getPayloadCapacityTons() { return payloadCapacityTons; }
    public boolean isArticulated()         { return isArticulated; }

    public void setPayloadCapacityTons(double tons) {
        if (tons <= 0)
            throw new IllegalArgumentException("Payload capacity must be positive.");
        this.payloadCapacityTons = tons;
    }
}
