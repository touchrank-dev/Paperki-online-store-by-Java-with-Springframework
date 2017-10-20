package com.kushnir.paperki.model.user;

public class UserUpdateErrorResponse {
    private String name;
    private String email;
    private String phone;
    private String birthday;

    private Boolean isErrors = false;

    public Boolean isErrors(){return this.isErrors;}

    public UserUpdateErrorResponse() {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        this.isErrors = true;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
        this.isErrors = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserUpdateErrorResponse that = (UserUpdateErrorResponse) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (birthday != null ? !birthday.equals(that.birthday) : that.birthday != null) return false;
        return isErrors != null ? isErrors.equals(that.isErrors) : that.isErrors == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (isErrors != null ? isErrors.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserUpdateErrorResponse{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", birthday='" + birthday + '\'' +
                ", isErrors=" + isErrors +
                '}';
    }
}
