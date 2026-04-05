package carservice.center;

import carservice.exceptions.*;
import carservice.model.*;
import carservice.service.Service;
import carservice.vehicle.Vehicle;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ServiceCenter {

    private static final int    LOYALTY_THRESHOLD  = 3;
    private static final double LOYALTY_DISCOUNT    = 10.0;

    private final String centerName;
    private final Map<String, Customer>      customers;
    private final Map<String, Vehicle>       vehicles;
    private final Map<String, ServiceRecord> serviceRecords;
    private final Map<String, Invoice>       invoices;

    public ServiceCenter(String centerName) {
        if (centerName == null || centerName.isBlank())
            throw new IllegalArgumentException("Center name cannot be blank.");
        this.centerName     = centerName.trim();
        this.customers      = new LinkedHashMap<>();
        this.vehicles       = new LinkedHashMap<>();
        this.serviceRecords = new LinkedHashMap<>();
        this.invoices       = new LinkedHashMap<>();
    }

    public Customer registerCustomer(String name, String email,
                                     String phone, String address)
            throws DuplicateEntryException {
        boolean emailExists = customers.values().stream()
                .anyMatch(c -> c.getEmail().equalsIgnoreCase(email.trim()));
        if (emailExists) {
            throw new DuplicateEntryException("Customer", email);
        }
        Customer customer = new Customer(name, email, phone, address);
        customers.put(customer.getCustomerId(), customer);
        System.out.printf("  ✔ Registered customer: %s (%s)%n",
                          customer.getName(), customer.getCustomerId());
        return customer;
    }

    public Vehicle addVehicle(String customerId, Vehicle vehicle)
            throws CustomerNotFoundException, DuplicateEntryException {
        findCustomer(customerId);
        boolean plateExists = vehicles.values().stream()
                .anyMatch(v -> v.getLicensePlate().equalsIgnoreCase(vehicle.getLicensePlate()));
        if (plateExists) {
            throw new DuplicateEntryException("Vehicle", vehicle.getLicensePlate());
        }
        vehicles.put(vehicle.getVehicleId(), vehicle);
        System.out.printf("  ✔ Added vehicle: %s (ID: %s) for customer %s%n",
                          vehicle.getLicensePlate(), vehicle.getVehicleId(), customerId);
        return vehicle;
    }

    public ServiceRecord scheduleService(String customerId, String vehicleId,
                                         Service service, LocalDate appointmentDate,
                                         String technicianName)
            throws CarServiceException {
        Customer customer = findCustomer(customerId);
        Vehicle  vehicle  = findVehicle(vehicleId);

        if (appointmentDate == null || appointmentDate.isBefore(LocalDate.now())) {
            throw new InvalidServiceException(
                "Appointment date must be today or in the future.");
        }

        ServiceRecord record = new ServiceRecord(customer, vehicle, service,
                                                 appointmentDate, technicianName);
        serviceRecords.put(record.getRecordId(), record);
        System.out.printf("  ✔ Scheduled: %-22s | %s | %s | Date: %s | Cost: %.2f%n",
                          service.getServiceName(), vehicle.getLicensePlate(),
                          record.getRecordId(), appointmentDate, record.getAgreedCost());
        return record;
    }

    public void updateServiceStatus(String recordId, ServiceStatus newStatus)
            throws CarServiceException {
        ServiceRecord record = findRecord(recordId);
        record.updateStatus(newStatus);
        System.out.printf("  ✔ Record %s status → %s%n", recordId, newStatus);
    }

    public Invoice generateInvoice(String customerId, List<String> recordIds)
            throws CarServiceException {
        Customer customer = findCustomer(customerId);
        List<ServiceRecord> records = new ArrayList<>();
        for (String rid : recordIds) {
            ServiceRecord r = findRecord(rid);
            if (!r.getCustomer().getCustomerId().equals(customerId)) {
                throw new InvalidServiceException(
                    "Record " + rid + " does not belong to customer " + customerId);
            }
            records.add(r);
        }
        Invoice invoice = new Invoice(customer, records);
        double loyaltyDiscount = calculateLoyaltyDiscount(customerId);
        if (loyaltyDiscount > 0) {
            invoice.applyDiscount(loyaltyDiscount);
            System.out.printf("  ★ Loyalty discount applied: %.0f%% (customer has %d+ completed services)%n",
                              loyaltyDiscount, LOYALTY_THRESHOLD);
        }
        invoices.put(invoice.getInvoiceId(), invoice);
        return invoice;
    }

    public List<ServiceRecord> getServiceHistory(String vehicleId)
            throws VehicleNotFoundException {
        findVehicle(vehicleId);
        return serviceRecords.values().stream()
                .filter(r -> r.getVehicle().getVehicleId().equals(vehicleId))
                .sorted(Comparator.comparing(ServiceRecord::getAppointmentDate).reversed())
                .collect(Collectors.toList());
    }

    public void printServiceHistory(String vehicleId) throws VehicleNotFoundException {
        Vehicle vehicle = findVehicle(vehicleId);
        List<ServiceRecord> history = getServiceHistory(vehicleId);
        System.out.println("═".repeat(75));
        System.out.printf("  SERVICE HISTORY — %s (%s)%n",
                          vehicle.getLicensePlate(), vehicle.getVehicleDescription());
        System.out.println("─".repeat(75));
        if (history.isEmpty()) {
            System.out.println("  No service records found.");
        } else {
            System.out.printf("  %-10s %-22s %-14s %-10s %8s%n",
                              "Date", "Service", "Record ID", "Status", "Cost");
            System.out.println("─".repeat(75));
            for (ServiceRecord r : history) {
                System.out.printf("  %-10s %-22s %-14s %-10s %8.2f%n",
                        r.getAppointmentDate(),
                        truncate(r.getService().getServiceName(), 22),
                        r.getRecordId(),
                        r.getStatus().getDisplayName(),
                        r.getAgreedCost());
            }
        }
        System.out.println("═".repeat(75));
    }

    public void listAllVehicles() {
        System.out.println("\n══ " + centerName + " — Registered Vehicles ══");
        if (vehicles.isEmpty()) { System.out.println("  None."); return; }
        vehicles.values().forEach(v -> System.out.println("  " + v));
    }

    public void listAllCustomers() {
        System.out.println("\n══ " + centerName + " — Registered Customers ══");
        if (customers.isEmpty()) { System.out.println("  None."); return; }
        customers.values().forEach(c -> System.out.println("  " + c));
    }

    public Customer findCustomer(String customerId) throws CustomerNotFoundException {
        Customer c = customers.get(customerId);
        if (c == null) throw new CustomerNotFoundException(customerId);
        return c;
    }

    public Vehicle findVehicle(String vehicleId) throws VehicleNotFoundException {
        Vehicle v = vehicles.get(vehicleId);
        if (v == null) throw new VehicleNotFoundException(vehicleId);
        return v;
    }

    public String getCenterName()       { return centerName; }
    public int getTotalCustomers()      { return customers.size(); }
    public int getTotalVehicles()       { return vehicles.size(); }
    public int getTotalServiceRecords() { return serviceRecords.size(); }

    private ServiceRecord findRecord(String recordId) throws InvalidServiceException {
        ServiceRecord r = serviceRecords.get(recordId);
        if (r == null)
            throw new InvalidServiceException("Service record not found: " + recordId);
        return r;
    }

    private double calculateLoyaltyDiscount(String customerId) {
        long completedCount = serviceRecords.values().stream()
                .filter(r -> r.getCustomer().getCustomerId().equals(customerId))
                .filter(r -> r.getStatus() == ServiceStatus.COMPLETED)
                .count();
        return completedCount >= LOYALTY_THRESHOLD ? LOYALTY_DISCOUNT : 0.0;
    }

    private static String truncate(String s, int max) {
        return s.length() <= max ? s : s.substring(0, max - 1) + "…";
    }
}
