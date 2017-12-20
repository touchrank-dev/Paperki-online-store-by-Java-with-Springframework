package com.kushnir.paperki.model.user;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class User {

    private Integer id;
    private String login;
    private String password;
    private String name;
    private String email;
    private String phone;
    private Boolean subscribe;
    private LocalDate birthDay;
    private UserType userType = UserType.ANONIMUS;
    private Boolean isEnterprise;

    private LocalDateTime createDate;
    private LocalDateTime editDate;

    public boolean isAnonimus() {
        return userType == UserType.ANONIMUS;
    }

    public User() {
    }

    public User(UserType userType) {
        this.userType = userType;
    }

    public User(String login, String name, UserType userType, String password) {
        this.login = login;
        this.name = name;
        this.userType = userType;
        this.password = password;
    }

    public User(String login, String password, String name, String email, UserType userType) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.email = email;
        this.userType = userType;
    }

    public User(Integer id, String login, String name, String password) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.password = password;
    }

    public User(Integer id,
                String login,
                String password,
                String name,
                String email,
                String phone,
                Boolean subscribe,
                LocalDate birthDay,
                LocalDateTime createDate,
                LocalDateTime editDate) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.subscribe = subscribe;
        this.birthDay = birthDay;
        this.createDate = createDate;
        this.editDate = editDate;
    }

    public User(String login,
                String password,
                String name,
                String email,
                String phone,
                Boolean subscribe,
                Boolean isEnterprise) {
        this.login = login;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.subscribe = subscribe;
        this.isEnterprise = isEnterprise;
    }

    public User(Integer id,
                String login,
                String password,
                String name,
                String email,
                String phone,
                Boolean subscribe,
                LocalDate birthDay,
                UserType userType,
                Boolean isEnterprise) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.subscribe = subscribe;
        this.birthDay = birthDay;
        this.userType = userType;
        this.isEnterprise = isEnterprise;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Boolean subscribe) {
        this.subscribe = subscribe;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Boolean getEnterprise() {
        return isEnterprise;
    }

    public void setEnterprise(Boolean enterprise) {
        isEnterprise = enterprise;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getEditDate() {
        return editDate;
    }

    public void setEditDate(LocalDateTime editDate) {
        this.editDate = editDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        if (subscribe != null ? !subscribe.equals(user.subscribe) : user.subscribe != null) return false;
        if (birthDay != null ? !birthDay.equals(user.birthDay) : user.birthDay != null) return false;
        if (userType != user.userType) return false;
        if (isEnterprise != null ? !isEnterprise.equals(user.isEnterprise) : user.isEnterprise != null) return false;
        if (createDate != null ? !createDate.equals(user.createDate) : user.createDate != null) return false;
        return editDate != null ? editDate.equals(user.editDate) : user.editDate == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (subscribe != null ? subscribe.hashCode() : 0);
        result = 31 * result + (birthDay != null ? birthDay.hashCode() : 0);
        result = 31 * result + (userType != null ? userType.hashCode() : 0);
        result = 31 * result + (isEnterprise != null ? isEnterprise.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (editDate != null ? editDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + "******" + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", subscribe=" + subscribe +
                ", birthDay=" + birthDay +
                ", userType=" + userType +
                ", isEnterprise=" + isEnterprise +
                ", createDate=" + createDate +
                ", editDate=" + editDate +
                '}';
    }
}
