package com.kushnir.paperki.model.user;

import java.time.LocalDateTime;

public class PasswordRecoveryRequest {
    private Integer id;
    private String token;
    private Integer userId;
    private String userLogin;
    private String email;
    private String ipAddress;
    private LocalDateTime createDate;
    private Boolean isExpired;
    private Boolean isPerformed;

    public PasswordRecoveryRequest() {
    }

    public PasswordRecoveryRequest(String userLogin) {
        this.userLogin = userLogin;
    }

    public PasswordRecoveryRequest(Integer id,
                                   String token,
                                   Integer userId,
                                   String userLogin,
                                   String email,
                                   String ipAddress,
                                   LocalDateTime createDate,
                                   Boolean isExpired,
                                   Boolean isPerformed) {
        this.id = id;
        this.token = token;
        this.userId = userId;
        this.userLogin = userLogin;
        this.email = email;
        this.ipAddress = ipAddress;
        this.createDate = createDate;
        this.isExpired = isExpired;
        this.isPerformed = isPerformed;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Boolean getExpired() {
        return isExpired;
    }

    public void setExpired(Boolean expired) {
        isExpired = expired;
    }

    public Boolean getPerformed() {
        return isPerformed;
    }

    public void setPerformed(Boolean performed) {
        isPerformed = performed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PasswordRecoveryRequest that = (PasswordRecoveryRequest) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (token != null ? !token.equals(that.token) : that.token != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (userLogin != null ? !userLogin.equals(that.userLogin) : that.userLogin != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (ipAddress != null ? !ipAddress.equals(that.ipAddress) : that.ipAddress != null) return false;
        if (createDate != null ? !createDate.equals(that.createDate) : that.createDate != null) return false;
        if (isExpired != null ? !isExpired.equals(that.isExpired) : that.isExpired != null) return false;
        return isPerformed != null ? isPerformed.equals(that.isPerformed) : that.isPerformed == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (userLogin != null ? userLogin.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (ipAddress != null ? ipAddress.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (isExpired != null ? isExpired.hashCode() : 0);
        result = 31 * result + (isPerformed != null ? isPerformed.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PasswordRecoveryRequest{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", userId=" + userId +
                ", userLogin='" + userLogin + '\'' +
                ", email='" + email + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", createDate=" + createDate +
                ", isExpired=" + isExpired +
                ", isPerformed=" + isPerformed +
                '}';
    }
}
