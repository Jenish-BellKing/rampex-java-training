package carservice.service;

import carservice.vehicle.Vehicle;

public class TireService extends Service {

    private static final double COST_PER_TIRE   = 500.0;
    private static final int    MINUTES_PER_TIRE = 20;

    private final int numberOfTires;
    private final boolean includesBalancing;

    public TireService(int numberOfTires, boolean includesBalancing) {
        super("Tire Service", computeBase(numberOfTires, includesBalancing));
        if (numberOfTires < 1 || numberOfTires > 18)
            throw new IllegalArgumentException("Number of tires must be between 1 and 18.");
        this.numberOfTires     = numberOfTires;
        this.includesBalancing = includesBalancing;
    }

    private static double computeBase(int tires, boolean balancing) {
        double cost = tires * COST_PER_TIRE;
        if (balancing) cost += tires * 150.0;
        return cost;
    }

    @Override
    public double calculateCost(Vehicle vehicle) {
        return getBasePrice() * vehicle.getServiceCostMultiplier();
    }

    @Override
    public int getEstimatedDurationMinutes(Vehicle vehicle) {
        int base = numberOfTires * MINUTES_PER_TIRE;
        if (includesBalancing) base += numberOfTires * 10;
        return base;
    }

    @Override
    public String getServiceDescription() {
        return String.format("Tire replacement for %d tire(s)%s.",
                             numberOfTires,
                             includesBalancing ? " with wheel balancing" : "");
    }

    public int getNumberOfTires()     { return numberOfTires; }
    public boolean isIncludesBalancing() { return includesBalancing; }
}
