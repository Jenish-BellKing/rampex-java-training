package carservice.model;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicLong;

public class Customer {

    private static final AtomicLong SEQUENCE = new AtomicLong(1001L);

    private final String customerId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private final LocalDate registrationDate;

    public Customer(String name, String email, String phone, String address) {
        setName(name);
        setEmail(email);
        setPhone(phone);
        setAddress(address);
        this.customerId       = "CUST" + SEQUENCE.getAndIncrement();
        this.registrationDate = LocalDate.now();
    }

    public String getCustomerId()       { return customerId; }
    public String getName()             { return name; }
    public String getEmail()            { return email; }
    public String getPhone()            { return phone; }
    public String getAddress()          { return address; }
    public LocalDate getRegistrationDate() { return registrationDate; }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Customer name cannot be blank.");
        }
        this.name = name.trim();
    }

    public void setEmail(String email) {
        if (email == null || !email.matches("^[\\w._%+\\-]+@[\\w.\\-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Invalid email address: " + email);
        }
        this.email = email.trim().toLowerCase();
    }

    public void setPhone(String phone) {
        if (phone == null || !phone.matches("^\\+?[0-9]{7,15}$")) {
            throw new IllegalArgumentException("Invalid phone number: " + phone);
        }
        this.phone = phone.trim();
    }

    public void setAddress(String address) {
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Address cannot be blank.");
        }
        this.address = address.trim();
    }

    @Override
    public String toString() {
        return String.format("Customer{id='%s', name='%s', email='%s', phone='%s'}",
                             customerId, name, email, phone);
    }
}
