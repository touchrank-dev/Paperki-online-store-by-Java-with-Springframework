package com.kushnir.paperki.model.order;

public class Attribute {
    private int p_id_order;
    private String p_name;
    private String p_value;

    public Attribute() {
    }

    public Attribute(int p_id_order, String p_name, String p_value) {
        this.p_id_order = p_id_order;
        this.p_name = p_name;
        this.p_value = p_value;
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
}
