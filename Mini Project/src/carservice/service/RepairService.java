package carservice.service;

import carservice.vehicle.Vehicle;

public class RepairService extends Service {

    private static final double HOURLY_LABOR_RATE = 600.0;

    private final double estimatedLaborHours;
    private final double partsAndMaterialsCost;
    private final String repairDescription;

    public RepairService(String repairDescription, double estimatedLaborHours,
                         double partsAndMaterialsCost) {
        super("Repair: " + repairDescription,
              computeBase(estimatedLaborHours, partsAndMaterialsCost));
        if (estimatedLaborHours <= 0)
            throw new IllegalArgumentException("Labor hours must be positive.");
        if (partsAndMaterialsCost < 0)
            throw new IllegalArgumentException("Parts cost cannot be negative.");
        if (repairDescription == null || repairDescription.isBlank())
            throw new IllegalArgumentException("Repair description cannot be blank.");
        this.estimatedLaborHours    = estimatedLaborHours;
        this.partsAndMaterialsCost  = partsAndMaterialsCost;
        this.repairDescription      = repairDescription.trim();
    }

    private static double computeBase(double hours, double parts) {
        return (hours * HOURLY_LABOR_RATE) + parts;
    }

    @Override
    public double calculateCost(Vehicle vehicle) {
        double laborCost = estimatedLaborHours * HOURLY_LABOR_RATE * vehicle.getServiceCostMultiplier();
        return laborCost + partsAndMaterialsCost;
    }

    @Override
    public int getEstimatedDurationMinutes(Vehicle vehicle) {
        return (int) (estimatedLaborHours * 60 * vehicle.getServiceCostMultiplier());
    }

    @Override
    public String getServiceDescription() {
        return String.format("Repair: %s | Labor: %.1f hr @ ₹%.0f/hr | Parts: ₹%.2f",
                             repairDescription, estimatedLaborHours,
                             HOURLY_LABOR_RATE, partsAndMaterialsCost);
    }

    public double getEstimatedLaborHours()   { return estimatedLaborHours; }
    public double getPartsAndMaterialsCost() { return partsAndMaterialsCost; }
    public String getRepairDescription()     { return repairDescription; }
}
