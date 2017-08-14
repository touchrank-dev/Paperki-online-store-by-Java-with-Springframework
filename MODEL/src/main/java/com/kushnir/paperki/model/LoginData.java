package com.kushnir.paperki.model;

public class LoginData {
    private String login;
    private String password;

    public LoginData(String lodin, String password) {
        this.login = lodin;
        this.password = password;
    }

    public LoginData() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoginData loginData = (LoginData) o;

        if (login != null ? !login.equals(loginData.login) : loginData.login != null) return false;
        return password != null ? password.equals(loginData.password) : loginData.password == null;
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LoginData{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
