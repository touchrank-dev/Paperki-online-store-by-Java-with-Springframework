package com.kushnir.paperki.model.delivery;

public class ShipmentInfo {
    private int type;
    private String name;
    private String address;
    private double cost;

    public ShipmentInfo() {
    }

    public ShipmentInfo(int type, String name, String address, double cost) {
        this.type = type;
        this.name = name;
        this.address = address;
        this.cost = cost;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShipmentInfo that = (ShipmentInfo) o;

        if (type != that.type) return false;
        if (Double.compare(that.cost, cost) != 0) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return address != null ? address.equals(that.address) : that.address == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = type;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        temp = Double.doubleToLongBits(cost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "ShipmentInfo{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", cost=" + cost +
                '}';
    }
}
