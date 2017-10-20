package com.kushnir.paperki.model;

import org.springframework.http.HttpStatus;

public class RestMessage {
    HttpStatus code;
    String message;
    Object object;

    public RestMessage() {
    }

    public RestMessage(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }

    public RestMessage(HttpStatus code, String message, Object object) {
        this.code = code;
        this.message = message;
        this.object = object;
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RestMessage that = (RestMessage) o;

        if (code != that.code) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        return object != null ? object.equals(that.object) : that.object == null;
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (object != null ? object.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "RestMessage{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", object=" + object +
                '}';
    }
}
