package com.kushnir.paperki.model;

public class ErrorRegistrateForm {

    private String name;
    private String email;
    private String subscribe;
    private String password;
    private String autopass;
    private String phone;
    private String birthDate;
    private String enterprise;

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

    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAutopass() {
        return autopass;
    }

    public void setAutopass(String autopass) {
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

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
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
        return enterprise != null ? enterprise.equals(that.enterprise) : that.enterprise == null;
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
                '}';
    }
}
