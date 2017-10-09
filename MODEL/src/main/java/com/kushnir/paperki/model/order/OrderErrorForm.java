package com.kushnir.paperki.model.order;

public class OrderErrorForm {

    private String message;

    private String name;
    private String email;
    private String phone;
    private String shipment;
    private String payment;

    private String unp;
    private String address;

    private Boolean isErrors = false;
    public Boolean isErrors(){return this.isErrors;}

    public void setErrors(String message) {
        this.message = message;
        isErrors = true;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        isErrors = true;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        isErrors = true;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        isErrors = true;
    }

    public String getShipment() {
        return shipment;
    }

    public void setShipment(String shipment) {
        this.shipment = shipment;
        isErrors = true;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
        isErrors = true;
    }

    public String getUnp() {
        return unp;
    }

    public void setUnp(String unp) {
        this.unp = unp;
        isErrors = true;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        isErrors = true;
    }
}
