package com.mycompany.atm.transaction.system.model;

public class Customer {

    private int id;
    private String customerName;
    private int userId;
    private String email;
    private String phone;

    public Customer() {}

    public Customer(int id, String customerName, int userId, String email, String phone) {
        this.id = id;
        this.customerName = customerName;
        this.userId = userId;
        this.email = email;
        this.phone = phone;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
}
