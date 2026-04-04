package bank.model;

public class AccountHolder {

    private String name;
    private String email;
    private String phone;
    private final String nationalId;

    public AccountHolder(String name, String email, String phone, String nationalId) {
        setName(name);
        setEmail(email);
        setPhone(phone);
        if (nationalId == null || nationalId.isBlank()) {
            throw new IllegalArgumentException("National ID cannot be null or blank.");
        }
        this.nationalId = nationalId.trim();
    }

    public String getName()       { return name; }
    public String getEmail()      { return email; }
    public String getPhone()      { return phone; }
    public String getNationalId() { return nationalId; }

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Account holder name cannot be null or blank.");
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

    @Override
    public String toString() {
        return String.format("AccountHolder{name='%s', email='%s', phone='%s', nationalId='%s'}",
                             name, email, phone, nationalId);
    }
}
