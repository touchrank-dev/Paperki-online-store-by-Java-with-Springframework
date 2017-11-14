package com.kushnir.paperki.model.payment;

public class PaymentInfo {
    private int type;
    private String name;
    private double cost;

    public PaymentInfo() {
    }

    public PaymentInfo(int type, String name, double cost) {
        this.type = type;
        this.name = name;
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

        PaymentInfo that = (PaymentInfo) o;

        if (type != that.type) return false;
        if (Double.compare(that.cost, cost) != 0) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = type;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(cost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "PaymentInfo{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                '}';
    }
}
