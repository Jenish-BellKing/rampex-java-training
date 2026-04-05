package carservice.model;

import carservice.exceptions.InvalidServiceException;
import carservice.service.Service;
import carservice.vehicle.Vehicle;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class ServiceRecord {

    private final String recordId;
    private final Customer customer;
    private final Vehicle vehicle;
    private final Service service;
    private final LocalDate appointmentDate;
    private ServiceStatus status;
    private LocalDateTime completedAt;
    private String technicianName;
    private String notes;
    private final double agreedCost;

    public ServiceRecord(Customer customer, Vehicle vehicle,
                         Service service, LocalDate appointmentDate,
                         String technicianName) {
        if (customer == null)        throw new IllegalArgumentException("Customer cannot be null.");
        if (vehicle == null)         throw new IllegalArgumentException("Vehicle cannot be null.");
        if (service == null)         throw new IllegalArgumentException("Service cannot be null.");
        if (appointmentDate == null) throw new IllegalArgumentException("Appointment date cannot be null.");
        if (technicianName == null || technicianName.isBlank())
            throw new IllegalArgumentException("Technician name cannot be blank.");

        this.recordId        = "REC" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        this.customer        = customer;
        this.vehicle         = vehicle;
        this.service         = service;
        this.appointmentDate = appointmentDate;
        this.technicianName  = technicianName.trim();
        this.status          = ServiceStatus.SCHEDULED;
        this.agreedCost      = service.calculateCost(vehicle);
        this.notes           = "";
    }

    public void updateStatus(ServiceStatus newStatus) throws InvalidServiceException {
        if (this.status == ServiceStatus.COMPLETED && newStatus != ServiceStatus.COMPLETED) {
            throw new InvalidServiceException(
                "Cannot change status of a completed service record: " + recordId);
        }
        if (this.status == ServiceStatus.CANCELLED) {
            throw new InvalidServiceException(
                "Cannot change status of a cancelled service record: " + recordId);
        }
        this.status = newStatus;
        if (newStatus == ServiceStatus.COMPLETED) {
            this.completedAt = LocalDateTime.now();
        }
    }

    public void addNote(String note) {
        if (note != null && !note.isBlank()) {
            this.notes = this.notes.isBlank() ? note.trim() : this.notes + "; " + note.trim();
        }
    }

    public String getRecordId()         { return recordId; }
    public Customer getCustomer()       { return customer; }
    public Vehicle getVehicle()         { return vehicle; }
    public Service getService()         { return service; }
    public LocalDate getAppointmentDate() { return appointmentDate; }
    public ServiceStatus getStatus()    { return status; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public String getTechnicianName()   { return technicianName; }
    public String getNotes()            { return notes; }
    public double getAgreedCost()       { return agreedCost; }

    @Override
    public String toString() {
        return String.format(
            "ServiceRecord{id='%s', service='%s', vehicle='%s', date=%s, status=%s, cost=%.2f}",
            recordId, service.getServiceName(), vehicle.getLicensePlate(),
            appointmentDate, status, agreedCost);
    }
}
