package com.kushnir.paperki.model.order;

public class Order {

    private int idOrderType;
    private String tokenOrder;
    private String orderNumber;
    private int idUser;
    private double total;
    private double totalWithVAT;
    private double VATtotal;
    private double totalDiscount;
    private double finalTotal;
    private double finalTotalWithVAT;

    public Order() {
    }

    public int getIdOrderType() {
        return idOrderType;
    }

    public void setIdOrderType(int idOrderType) {
        this.idOrderType = idOrderType;
    }

    public String getTokenOrder() {
        return tokenOrder;
    }

    public void setTokenOrder(String tokenOrder) {
        this.tokenOrder = tokenOrder;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotalWithVAT() {
        return totalWithVAT;
    }

    public void setTotalWithVAT(double totalWithVAT) {
        this.totalWithVAT = totalWithVAT;
    }

    public double getVATtotal() {
        return VATtotal;
    }

    public void setVATtotal(double VATtotal) {
        this.VATtotal = VATtotal;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public double getFinalTotal() {
        return finalTotal;
    }

    public void setFinalTotal(double finalTotal) {
        this.finalTotal = finalTotal;
    }

    public double getFinalTotalWithVAT() {
        return finalTotalWithVAT;
    }

    public void setFinalTotalWithVAT(double finalTotalWithVAT) {
        this.finalTotalWithVAT = finalTotalWithVAT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (idOrderType != order.idOrderType) return false;
        if (idUser != order.idUser) return false;
        if (Double.compare(order.total, total) != 0) return false;
        if (Double.compare(order.totalWithVAT, totalWithVAT) != 0) return false;
        if (Double.compare(order.VATtotal, VATtotal) != 0) return false;
        if (Double.compare(order.totalDiscount, totalDiscount) != 0) return false;
        if (Double.compare(order.finalTotal, finalTotal) != 0) return false;
        if (Double.compare(order.finalTotalWithVAT, finalTotalWithVAT) != 0) return false;
        if (tokenOrder != null ? !tokenOrder.equals(order.tokenOrder) : order.tokenOrder != null) return false;
        return orderNumber != null ? orderNumber.equals(order.orderNumber) : order.orderNumber == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = idOrderType;
        result = 31 * result + (tokenOrder != null ? tokenOrder.hashCode() : 0);
        result = 31 * result + (orderNumber != null ? orderNumber.hashCode() : 0);
        result = 31 * result + idUser;
        temp = Double.doubleToLongBits(total);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(totalWithVAT);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(VATtotal);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(totalDiscount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(finalTotal);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(finalTotalWithVAT);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "idOrderType=" + idOrderType +
                ", tokenOrder='" + tokenOrder + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", idUser=" + idUser +
                ", total=" + total +
                ", totalWithVAT=" + totalWithVAT +
                ", VATtotal=" + VATtotal +
                ", totalDiscount=" + totalDiscount +
                ", finalTotal=" + finalTotal +
                ", finalTotalWithVAT=" + finalTotalWithVAT +
                '}';
    }
}
