package carservice.service;

import carservice.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FullServicePackage extends Service {

    private static final double PACKAGE_DISCOUNT_PERCENT = 15.0;

    private final List<Service> includedServices;

    public FullServicePackage(String packageName, List<Service> services) {
        super(packageName, 0.0);
        if (services == null || services.isEmpty())
            throw new IllegalArgumentException("A package must include at least one service.");
        this.includedServices = new ArrayList<>(services);
        double total = services.stream().mapToDouble(Service::getBasePrice).sum();
        setBasePrice(total * (1 - PACKAGE_DISCOUNT_PERCENT / 100.0));
    }

    @Override
    public double calculateCost(Vehicle vehicle) {
        double full = includedServices.stream()
                                      .mapToDouble(s -> s.calculateCost(vehicle))
                                      .sum();
        return full * (1 - PACKAGE_DISCOUNT_PERCENT / 100.0);
    }

    @Override
    public int getEstimatedDurationMinutes(Vehicle vehicle) {
        return includedServices.stream()
                               .mapToInt(s -> s.getEstimatedDurationMinutes(vehicle))
                               .sum();
    }

    @Override
    public String getServiceDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Full Service Package (%.0f%% bundle discount):%n",
                                PACKAGE_DISCOUNT_PERCENT));
        for (Service s : includedServices) {
            sb.append("  • ").append(s.getServiceName()).append(System.lineSeparator());
        }
        return sb.toString();
    }

    public List<Service> getIncludedServices() {
        return Collections.unmodifiableList(includedServices);
    }

    public double getDiscountPercent() {
        return PACKAGE_DISCOUNT_PERCENT;
    }
}
