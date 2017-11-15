package com.kushnir.paperki.model.order;

public class Attribute {
    private int orderId;
    private String name;
    private String value;
    private int order;
    private String typeName;
    private int typeId;

    public Attribute() {
    }

    public Attribute(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Attribute(String name, String value, int order) {
        this.name = name;
        this.value = value;
        this.order = order;
    }

    public Attribute(int orderId, String name, String value) {
        this.orderId = orderId;
        this.name = name;
        this.value = value;
    }

    public Attribute(int orderId, String name, String value, int order, String typeName, int typeId) {
        this.orderId = orderId;
        this.name = name;
        this.value = value;
        this.order = order;
        this.typeName = typeName;
        this.typeId = typeId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attribute attribute = (Attribute) o;

        if (orderId != attribute.orderId) return false;
        if (order != attribute.order) return false;
        if (typeId != attribute.typeId) return false;
        if (name != null ? !name.equals(attribute.name) : attribute.name != null) return false;
        if (value != null ? !value.equals(attribute.value) : attribute.value != null) return false;
        return typeName != null ? typeName.equals(attribute.typeName) : attribute.typeName == null;
    }

    @Override
    public int hashCode() {
        int result = orderId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + order;
        result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
        result = 31 * result + typeId;
        return result;
    }

    @Override
    public String toString() {
        return "Attribute{" +
                "orderId=" + orderId +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", order=" + order +
                ", typeName='" + typeName + '\'' +
                ", typeId=" + typeId +
                '}';
    }
}
