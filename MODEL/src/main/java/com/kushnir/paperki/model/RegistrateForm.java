package com.kushnir.paperki.model;

public class RegistrateForm {

    private String name;
    private String email;
    private Boolean subscribe;
    private String password;
    private Boolean autopass;
    private String phone;
    private String birthDate;
    private Boolean enterprise;
    private String UNP;
    private String enterpriseName;
    private String billingAddress;
    private String accountNumber;
    private String bankName;
    private String bankCode;

    public RegistrateForm() {
    }

    public RegistrateForm(String name,
                          String email,
                          Boolean subscribe,
                          String password,
                          Boolean autopass,
                          String phone,
                          Boolean enterprise) {
        this.name = name;
        this.email = email;
        this.subscribe = subscribe;
        this.password = password;
        this.autopass = autopass;
        this.phone = phone;
        this.enterprise = enterprise;
    }

    public RegistrateForm(String name,
                          String email,
                          Boolean subscribe,
                          String password,
                          Boolean autopass,
                          String phone,
                          Boolean enterprise,
                          String UNP,
                          String enterpriseName,
                          String billingAddress,
                          String accountNumber,
                          String bankName,
                          String bankCode) {
        this.name = name;
        this.email = email;
        this.subscribe = subscribe;
        this.password = password;
        this.autopass = autopass;
        this.phone = phone;
        this.enterprise = enterprise;
        this.UNP = UNP;
        this.enterpriseName = enterpriseName;
        this.billingAddress = billingAddress;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.bankCode = bankCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Boolean subscribe) {
        this.subscribe = subscribe;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAutopass() {
        return autopass;
    }

    public void setAutopass(Boolean autopass) {
        this.autopass = autopass;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Boolean enterprise) {
        this.enterprise = enterprise;
    }

    public String getUNP() {
        return UNP;
    }

    public void setUNP(String UNP) {
        this.UNP = UNP;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegistrateForm that = (RegistrateForm) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (subscribe != null ? !subscribe.equals(that.subscribe) : that.subscribe != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (autopass != null ? !autopass.equals(that.autopass) : that.autopass != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (birthDate != null ? !birthDate.equals(that.birthDate) : that.birthDate != null) return false;
        if (enterprise != null ? !enterprise.equals(that.enterprise) : that.enterprise != null) return false;
        if (UNP != null ? !UNP.equals(that.UNP) : that.UNP != null) return false;
        if (enterpriseName != null ? !enterpriseName.equals(that.enterpriseName) : that.enterpriseName != null)
            return false;
        if (billingAddress != null ? !billingAddress.equals(that.billingAddress) : that.billingAddress != null)
            return false;
        if (accountNumber != null ? !accountNumber.equals(that.accountNumber) : that.accountNumber != null)
            return false;
        if (bankName != null ? !bankName.equals(that.bankName) : that.bankName != null) return false;
        return bankCode != null ? bankCode.equals(that.bankCode) : that.bankCode == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (subscribe != null ? subscribe.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (autopass != null ? autopass.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (enterprise != null ? enterprise.hashCode() : 0);
        result = 31 * result + (UNP != null ? UNP.hashCode() : 0);
        result = 31 * result + (enterpriseName != null ? enterpriseName.hashCode() : 0);
        result = 31 * result + (billingAddress != null ? billingAddress.hashCode() : 0);
        result = 31 * result + (accountNumber != null ? accountNumber.hashCode() : 0);
        result = 31 * result + (bankName != null ? bankName.hashCode() : 0);
        result = 31 * result + (bankCode != null ? bankCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RegistrateForm{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", subscribe=" + subscribe +
                ", password='" + "******" + '\'' +
                ", autopass=" + autopass +
                ", phone='" + phone + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", enterprise=" + enterprise +
                ", UNP=" + UNP +
                ", enterpriseName='" + enterpriseName + '\'' +
                ", billingAddress='" + billingAddress + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankCode=" + bankCode +
                '}';
    }
}
