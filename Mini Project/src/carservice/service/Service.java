package carservice.service;

import carservice.vehicle.Vehicle;

import java.util.concurrent.atomic.AtomicLong;

public abstract class Service {

    private static final AtomicLong SEQUENCE = new AtomicLong(9001L);

    private final String serviceId;
    private final String serviceName;
    private double basePrice;

    protected Service(String serviceName, double basePrice) {
        if (serviceName == null || serviceName.isBlank())
            throw new IllegalArgumentException("Service name cannot be blank.");
        validatePrice(basePrice);
        this.serviceId   = "SVC" + SEQUENCE.getAndIncrement();
        this.serviceName = serviceName.trim();
        this.basePrice   = basePrice;
    }

    public abstract double calculateCost(Vehicle vehicle);

    public abstract int getEstimatedDurationMinutes(Vehicle vehicle);

    public abstract String getServiceDescription();

    public String getServiceId()   { return serviceId; }
    public String getServiceName() { return serviceName; }
    public double getBasePrice()   { return basePrice; }

    public void setBasePrice(double basePrice) {
        validatePrice(basePrice);
        this.basePrice = basePrice;
    }

    protected void validatePrice(double price) {
        if (price < 0)
            throw new IllegalArgumentException("Price cannot be negative. Received: " + price);
    }

    @Override
    public String toString() {
        return String.format("Service{id='%s', name='%s', basePrice=%.2f}",
                             serviceId, serviceName, basePrice);
    }
}
