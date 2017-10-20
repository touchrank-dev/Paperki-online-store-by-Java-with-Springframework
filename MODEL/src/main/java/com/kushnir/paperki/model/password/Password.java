package com.kushnir.paperki.model.password;

public class Password {
    private Integer UserId;
    private String password;

    public Password() {
    }

    public Password(Integer userId, String password) {
        UserId = userId;
        this.password = password;
    }

    public Integer getUserId() {
        return UserId;
    }

    public void setUserId(Integer userId) {
        UserId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Password password1 = (Password) o;

        if (UserId != null ? !UserId.equals(password1.UserId) : password1.UserId != null) return false;
        return password != null ? password.equals(password1.password) : password1.password == null;
    }

    @Override
    public int hashCode() {
        int result = UserId != null ? UserId.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Password{" +
                "UserId=" + UserId +
                ", password='" + "******" + '\'' +
                '}';
    }
}
