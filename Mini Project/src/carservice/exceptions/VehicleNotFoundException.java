package carservice.exceptions;

public class VehicleNotFoundException extends CarServiceException {

    private final String vehicleId;

    public VehicleNotFoundException(String vehicleId) {
        super("ERR_VEHICLE_NOT_FOUND",
              String.format("Vehicle not found with ID: %s", vehicleId));
        this.vehicleId = vehicleId;
    }

    public String getVehicleId() {
        return vehicleId;
    }
}
