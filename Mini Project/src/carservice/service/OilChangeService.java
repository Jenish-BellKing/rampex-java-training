package carservice.service;

import carservice.vehicle.Vehicle;

public class OilChangeService extends Service {

    public enum OilGrade { MINERAL, SEMI_SYNTHETIC, FULLY_SYNTHETIC }

    private static final double BASE_PRICE_MINERAL         = 800.0;
    private static final double BASE_PRICE_SEMI_SYNTHETIC  = 1200.0;
    private static final double BASE_PRICE_FULLY_SYNTHETIC = 1800.0;

    private final OilGrade oilGrade;

    public OilChangeService(OilGrade oilGrade) {
        super("Oil Change (" + oilGrade.name().replace('_', ' ') + ")",
              resolveBasePrice(oilGrade));
        this.oilGrade = oilGrade;
    }

    private static double resolveBasePrice(OilGrade grade) {
        switch (grade) {
            case MINERAL:          return BASE_PRICE_MINERAL;
            case SEMI_SYNTHETIC:   return BASE_PRICE_SEMI_SYNTHETIC;
            case FULLY_SYNTHETIC:  return BASE_PRICE_FULLY_SYNTHETIC;
            default: throw new IllegalArgumentException("Unknown oil grade: " + grade);
        }
    }

    @Override
    public double calculateCost(Vehicle vehicle) {
        return getBasePrice() * vehicle.getServiceCostMultiplier();
    }

    @Override
    public int getEstimatedDurationMinutes(Vehicle vehicle) {
        int base = 30;
        if (oilGrade == OilGrade.FULLY_SYNTHETIC) base += 15;
        return (int) (base * vehicle.getServiceCostMultiplier());
    }

    @Override
    public String getServiceDescription() {
        return String.format("Oil Change using %s oil. Includes drain, filter replacement, and top-up.",
                             oilGrade.name().toLowerCase().replace('_', ' '));
    }

    public OilGrade getOilGrade() { return oilGrade; }
}
