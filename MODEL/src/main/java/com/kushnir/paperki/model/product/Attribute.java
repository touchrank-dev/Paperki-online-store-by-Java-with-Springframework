package com.kushnir.paperki.model.product;

public class Attribute {
    private Integer pnt;
    private String name;
    private String value;
    private Integer order;

    public Attribute() {
    }



    public Attribute(String name, String value, Integer order) {
        this.name = name;
        this.value = value;
        this.order = order;
    }

    public Attribute(String name, String value, Integer order, Integer pnt) {
        this.pnt = pnt;
        this.name = name;
        this.value = value;
        this.order = order;
    }


    public Attribute(Integer pnt, String name, String value, Integer order) {
        this.pnt = pnt;
        this.name = name;
        this.value = value;
        this.order = order;
    }

    public Integer getPnt() {
        return pnt;
    }

    public void setPnt(Integer pnt) {
        this.pnt = pnt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attribute attribute = (Attribute) o;

        if (pnt != null ? !pnt.equals(attribute.pnt) : attribute.pnt != null) return false;
        if (name != null ? !name.equals(attribute.name) : attribute.name != null) return false;
        if (value != null ? !value.equals(attribute.value) : attribute.value != null) return false;
        return order != null ? order.equals(attribute.order) : attribute.order == null;
    }

    @Override
    public int hashCode() {
        int result = pnt != null ? pnt.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                ", pnt=" + pnt +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", order=" + order +
                '}';
    }
}
