package com.kushnir.paperki.model;

public class ErrorRegistrateForm {

    private String name;
    private String email;
    private String subscribe;
    private String password;
    private String confirmPassword;
    private String autopass;
    private String phone;
    private String birthDate;
    private String enterprise;
    private String UNP;
    private String enterpriseName;
    private String billingAddress;
    private String accountNumber;
    private String bankName;
    private String bankCode;

    private Boolean isErrors = false;

    public Boolean isErrors(){return this.isErrors;}

    public ErrorRegistrateForm() {
    }

    public ErrorRegistrateForm(String name,
                               String email,
                               String subscribe,
                               String password,
                               String autopass,
                               String phone,
                               String birthDate,
                               String enterprise) {
        this.name = name;
        this.email = email;
        this.subscribe = subscribe;
        this.password = password;
        this.autopass = autopass;
        this.phone = phone;
        this.birthDate = birthDate;
        this.enterprise = enterprise;
    }

    public ErrorRegistrateForm(String name,
                               String email,
                               String subscribe,
                               String password,
                               String autopass,
                               String phone,
                               String birthDate,
                               String enterprise,
                               String UNP,
                               String enterpriseName,
                               String billingAddress,
                               String accountNumber,
                               String bankName,
                               String bankCode,
                               Boolean isErrors) {
        this.name = name;
        this.email = email;
        this.subscribe = subscribe;
        this.password = password;
        this.autopass = autopass;
        this.phone = phone;
        this.birthDate = birthDate;
        this.enterprise = enterprise;
        this.UNP = UNP;
        this.enterpriseName = enterpriseName;
        this.billingAddress = billingAddress;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.bankCode = bankCode;
        this.isErrors = isErrors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.isErrors = true;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        this.isErrors = true;
    }

    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
        this.isErrors = true;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        this.isErrors = true;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        this.isErrors = true;
    }

    public String getAutopass() {
        return autopass;
    }

    public void setAutopass(String autopass) {
        this.autopass = autopass;
        this.isErrors = true;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        this.isErrors = true;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
        this.isErrors = true;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
        this.isErrors = true;
    }

    public String getUNP() {
        return UNP;
    }

    public void setUNP(String UNP) {
        this.UNP = UNP;
        this.isErrors = true;

    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
        this.isErrors = true;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
        this.isErrors = true;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        this.isErrors = true;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
        this.isErrors = true;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
        this.isErrors = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ErrorRegistrateForm that = (ErrorRegistrateForm) o;

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
        if (bankCode != null ? !bankCode.equals(that.bankCode) : that.bankCode != null) return false;
        return isErrors != null ? isErrors.equals(that.isErrors) : that.isErrors == null;
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
        result = 31 * result + (isErrors != null ? isErrors.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ErrorRegistrateForm{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", subscribe='" + subscribe + '\'' +
                ", password='" + password + '\'' +
                ", autopass='" + autopass + '\'' +
                ", phone='" + phone + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", enterprise='" + enterprise + '\'' +
                ", UNP='" + UNP + '\'' +
                ", enterpriseName='" + enterpriseName + '\'' +
                ", billingAddress='" + billingAddress + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", isErrors=" + isErrors +
                '}';
    }
}
