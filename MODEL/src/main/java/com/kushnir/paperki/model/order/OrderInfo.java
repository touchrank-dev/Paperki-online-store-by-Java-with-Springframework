package com.kushnir.paperki.model.order;

public class OrderInfo {

    private String customerName;
    private String enterpriseName;
    private String UNP;
    private String email;
    private String phone;
    private String paymentName;
    private String paymentAccount;
    private String paymentBankName;
    private String paymentBankCode;
    private String shipmentName;
    private String shipmentAddress;
    private String userNotes;

    public OrderInfo() {
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getUNP() {
        return UNP;
    }

    public void setUNP(String UNP) {
        this.UNP = UNP;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getPaymentAccount() {
        return paymentAccount;
    }

    public void setPaymentAccount(String paymentAccount) {
        this.paymentAccount = paymentAccount;
    }

    public String getPaymentBankName() {
        return paymentBankName;
    }

    public void setPaymentBankName(String paymentBankName) {
        this.paymentBankName = paymentBankName;
    }

    public String getPaymentBankCode() {
        return paymentBankCode;
    }

    public void setPaymentBankCode(String paymentBankCode) {
        this.paymentBankCode = paymentBankCode;
    }

    public String getShipmentName() {
        return shipmentName;
    }

    public void setShipmentName(String shipmentName) {
        this.shipmentName = shipmentName;
    }

    public String getShipmentAddress() {
        return shipmentAddress;
    }

    public void setShipmentAddress(String shipmentAddress) {
        this.shipmentAddress = shipmentAddress;
    }

    public String getUserNotes() {
        return userNotes;
    }

    public void setUserNotes(String userNotes) {
        this.userNotes = userNotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderInfo orderInfo = (OrderInfo) o;

        if (customerName != null ? !customerName.equals(orderInfo.customerName) : orderInfo.customerName != null)
            return false;
        if (enterpriseName != null ? !enterpriseName.equals(orderInfo.enterpriseName) : orderInfo.enterpriseName != null)
            return false;
        if (UNP != null ? !UNP.equals(orderInfo.UNP) : orderInfo.UNP != null) return false;
        if (email != null ? !email.equals(orderInfo.email) : orderInfo.email != null) return false;
        if (phone != null ? !phone.equals(orderInfo.phone) : orderInfo.phone != null) return false;
        if (paymentName != null ? !paymentName.equals(orderInfo.paymentName) : orderInfo.paymentName != null)
            return false;
        if (paymentAccount != null ? !paymentAccount.equals(orderInfo.paymentAccount) : orderInfo.paymentAccount != null)
            return false;
        if (paymentBankName != null ? !paymentBankName.equals(orderInfo.paymentBankName) : orderInfo.paymentBankName != null)
            return false;
        if (paymentBankCode != null ? !paymentBankCode.equals(orderInfo.paymentBankCode) : orderInfo.paymentBankCode != null)
            return false;
        if (shipmentName != null ? !shipmentName.equals(orderInfo.shipmentName) : orderInfo.shipmentName != null)
            return false;
        if (shipmentAddress != null ? !shipmentAddress.equals(orderInfo.shipmentAddress) : orderInfo.shipmentAddress != null)
            return false;
        return userNotes != null ? userNotes.equals(orderInfo.userNotes) : orderInfo.userNotes == null;
    }

    @Override
    public int hashCode() {
        int result = customerName != null ? customerName.hashCode() : 0;
        result = 31 * result + (enterpriseName != null ? enterpriseName.hashCode() : 0);
        result = 31 * result + (UNP != null ? UNP.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (paymentName != null ? paymentName.hashCode() : 0);
        result = 31 * result + (paymentAccount != null ? paymentAccount.hashCode() : 0);
        result = 31 * result + (paymentBankName != null ? paymentBankName.hashCode() : 0);
        result = 31 * result + (paymentBankCode != null ? paymentBankCode.hashCode() : 0);
        result = 31 * result + (shipmentName != null ? shipmentName.hashCode() : 0);
        result = 31 * result + (shipmentAddress != null ? shipmentAddress.hashCode() : 0);
        result = 31 * result + (userNotes != null ? userNotes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "customerName='" + customerName + '\'' +
                ", enterpriseName='" + enterpriseName + '\'' +
                ", UNP='" + UNP + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", paymentName='" + paymentName + '\'' +
                ", paymentAccount='" + paymentAccount + '\'' +
                ", paymentBankName='" + paymentBankName + '\'' +
                ", paymentBankCode='" + paymentBankCode + '\'' +
                ", shipmentName='" + shipmentName + '\'' +
                ", shipmentAddress='" + shipmentAddress + '\'' +
                ", userNotes='" + userNotes + '\'' +
                '}';
    }
}
