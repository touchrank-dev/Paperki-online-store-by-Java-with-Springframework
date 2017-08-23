package com.kushnir.paperki.model;

import java.util.HashMap;

public class Cart {

    private Double total;
    private Double totalVAT;
    private Double totalDiscount;
    private Double totalDiscountWithVAT;
    private Double totalWithDiscount;
    private Double totalWithDiscountWithWat;

    private Double paymentTotal;
    private Double shipmentTotal;

    private HashMap<Integer, CartProduct> items = new HashMap<Integer, CartProduct>();

    public HashMap<Integer, CartProduct> getItems() {
        return items;
    }
    public void setItems(HashMap<Integer, CartProduct> items) {
        this.items = items;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getTotalVAT() {
        return totalVAT;
    }

    public void setTotalVAT(Double totalVAT) {
        this.totalVAT = totalVAT;
    }

    public Double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(Double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public Double getTotalDiscountWithVAT() {
        return totalDiscountWithVAT;
    }

    public void setTotalDiscountWithVAT(Double totalDiscountWithVAT) {
        this.totalDiscountWithVAT = totalDiscountWithVAT;
    }

    public Double getTotalWithDiscount() {
        return totalWithDiscount;
    }

    public void setTotalWithDiscount(Double totalWithDiscount) {
        this.totalWithDiscount = totalWithDiscount;
    }

    public Double getTotalWithDiscountWithWat() {
        return totalWithDiscountWithWat;
    }

    public void setTotalWithDiscountWithWat(Double totalWithDiscountWithWat) {
        this.totalWithDiscountWithWat = totalWithDiscountWithWat;
    }

    public Double getPaymentTotal() {
        return paymentTotal;
    }

    public void setPaymentTotal(Double paymentTotal) {
        this.paymentTotal = paymentTotal;
    }

    public Double getShipmentTotal() {
        return shipmentTotal;
    }

    public void setShipmentTotal(Double shipmentTotal) {
        this.shipmentTotal = shipmentTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cart cart = (Cart) o;

        if (total != null ? !total.equals(cart.total) : cart.total != null) return false;
        if (totalVAT != null ? !totalVAT.equals(cart.totalVAT) : cart.totalVAT != null) return false;
        if (totalDiscount != null ? !totalDiscount.equals(cart.totalDiscount) : cart.totalDiscount != null)
            return false;
        if (totalDiscountWithVAT != null ? !totalDiscountWithVAT.equals(cart.totalDiscountWithVAT) : cart.totalDiscountWithVAT != null)
            return false;
        if (totalWithDiscount != null ? !totalWithDiscount.equals(cart.totalWithDiscount) : cart.totalWithDiscount != null)
            return false;
        if (totalWithDiscountWithWat != null ? !totalWithDiscountWithWat.equals(cart.totalWithDiscountWithWat) : cart.totalWithDiscountWithWat != null)
            return false;
        if (paymentTotal != null ? !paymentTotal.equals(cart.paymentTotal) : cart.paymentTotal != null) return false;
        if (shipmentTotal != null ? !shipmentTotal.equals(cart.shipmentTotal) : cart.shipmentTotal != null)
            return false;
        return items != null ? items.equals(cart.items) : cart.items == null;
    }

    @Override
    public int hashCode() {
        int result = total != null ? total.hashCode() : 0;
        result = 31 * result + (totalVAT != null ? totalVAT.hashCode() : 0);
        result = 31 * result + (totalDiscount != null ? totalDiscount.hashCode() : 0);
        result = 31 * result + (totalDiscountWithVAT != null ? totalDiscountWithVAT.hashCode() : 0);
        result = 31 * result + (totalWithDiscount != null ? totalWithDiscount.hashCode() : 0);
        result = 31 * result + (totalWithDiscountWithWat != null ? totalWithDiscountWithWat.hashCode() : 0);
        result = 31 * result + (paymentTotal != null ? paymentTotal.hashCode() : 0);
        result = 31 * result + (shipmentTotal != null ? shipmentTotal.hashCode() : 0);
        result = 31 * result + (items != null ? items.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "total=" + total +
                ", totalVAT=" + totalVAT +
                ", totalDiscount=" + totalDiscount +
                ", totalDiscountWithVAT=" + totalDiscountWithVAT +
                ", totalWithDiscount=" + totalWithDiscount +
                ", totalWithDiscountWithWat=" + totalWithDiscountWithWat +
                ", paymentTotal=" + paymentTotal +
                ", shipmentTotal=" + shipmentTotal +
                ", items=" + items +
                '}';
    }
}
