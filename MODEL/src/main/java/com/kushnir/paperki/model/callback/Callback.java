package com.kushnir.paperki.model.callback;

public class Callback {
    private String name;
    private String phone;
    private String comment;

    public Callback() {
    }

    public Callback(String name, String phone, String comment) {
        this.name = name;
        this.phone = phone;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Callback callback = (Callback) o;

        if (name != null ? !name.equals(callback.name) : callback.name != null) return false;
        if (phone != null ? !phone.equals(callback.phone) : callback.phone != null) return false;
        return comment != null ? comment.equals(callback.comment) : callback.comment == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Callback{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
