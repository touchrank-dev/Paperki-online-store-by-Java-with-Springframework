package com.kushnir.paperki.model.order;

public class Attribute {
    private int p_id_order;
    private String p_name;
    private String p_value;
    private int typeId;
    private int order;

    public Attribute() {
    }

    public Attribute(String p_name, String p_value) {
        this.p_name = p_name;
        this.p_value = p_value;
    }

    public Attribute(int p_id_order, String p_name, String p_value) {
        this.p_id_order = p_id_order;
        this.p_name = p_name;
        this.p_value = p_value;
    }

    public Attribute(String p_name, String p_value, int order) {
        this.p_name = p_name;
        this.p_value = p_value;
        this.order = order;
    }

    public int getP_id_order() {
        return p_id_order;
    }

    public void setP_id_order(int p_id_order) {
        this.p_id_order = p_id_order;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_value() {
        return p_value;
    }

    public void setP_value(String p_value) {
        this.p_value = p_value;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attribute attribute = (Attribute) o;

        if (p_id_order != attribute.p_id_order) return false;
        if (order != attribute.order) return false;
        if (p_name != null ? !p_name.equals(attribute.p_name) : attribute.p_name != null) return false;
        return p_value != null ? p_value.equals(attribute.p_value) : attribute.p_value == null;
    }

    @Override
    public int hashCode() {
        int result = p_id_order;
        result = 31 * result + (p_name != null ? p_name.hashCode() : 0);
        result = 31 * result + (p_value != null ? p_value.hashCode() : 0);
        result = 31 * result + order;
        return result;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "p_id_order=" + p_id_order +
                ", p_name='" + p_name + '\'' +
                ", p_value='" + p_value + '\'' +
                ", order=" + order +
                '}';
    }
}
