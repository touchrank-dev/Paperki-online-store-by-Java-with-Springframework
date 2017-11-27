package com.kushnir.paperki.model.user;

public class ErrorMessages {
    private String message;
    private Boolean isError = false;

    public ErrorMessages() {
    }

    public ErrorMessages(String message) {
        this.message = message;
        this.isError = true;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        this.isError = true;
    }

    public Boolean isError() {
        return isError;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ErrorMessages that = (ErrorMessages) o;

        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        return isError != null ? isError.equals(that.isError) : that.isError == null;
    }

    @Override
    public int hashCode() {
        int result = message != null ? message.hashCode() : 0;
        result = 31 * result + (isError != null ? isError.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ErrorMessages{" +
                "message='" + message + '\'' +
                ", isError=" + isError +
                '}';
    }
}
