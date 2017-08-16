package com.kushnir.paperki.model;

public class ErrorLoginData {
    private String login;
    private String password;

    private Boolean isErrors = false;

    public Boolean isErrors(){
        return this.isErrors;
    }

    public ErrorLoginData() {
    }

    public ErrorLoginData(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
        this.isErrors = true;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        this.isErrors = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ErrorLoginData that = (ErrorLoginData) o;

        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        return password != null ? password.equals(that.password) : that.password == null;
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ErrorLoginData{" +
                "login='" + login + '\'' +
                ", password='" + "******" + '\'' +
                '}';
    }
}
