package carservice.model;

public enum VehicleType {
    CAR("Car", 1.0),
    TRUCK("Truck", 1.5),
    MOTORCYCLE("Motorcycle", 0.7),
    SUV("SUV", 1.2),
    VAN("Van", 1.3);

    private final String displayName;
    private final double serviceCostMultiplier;

    VehicleType(String displayName, double serviceCostMultiplier) {
        this.displayName            = displayName;
        this.serviceCostMultiplier  = serviceCostMultiplier;
    }

    public String getDisplayName()          { return displayName; }
    public double getServiceCostMultiplier(){ return serviceCostMultiplier; }

    @Override
    public String toString() {
        return displayName;
    }
}
