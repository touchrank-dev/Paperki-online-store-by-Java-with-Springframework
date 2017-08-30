package com.kushnir.paperki.model;

import java.util.HashMap;

public class Cart {
    private Double total;
    private Double totalVATAmount;                  // размер налогов
    private Double totalWithVAT;                    //
    private Double totalDiscountAmount;             // размер скидки
    private Double totalWithDiscount;
    private Double totalWithDiscountWithVAT;
    private Double paymentCost;
    private Double shipmentCost;
    private Double finallyTotal;
    private HashMap<Integer, CartProduct> items = new HashMap<Integer, CartProduct>();

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getTotalVATAmount() {
        return totalVATAmount;
    }

    public void setTotalVATAmount(Double totalVATAmount) {
        this.totalVATAmount = totalVATAmount;
    }

    public Double getTotalWithVAT() {
        return totalWithVAT;
    }

    public void setTotalWithVAT(Double totalWithVAT) {
        this.totalWithVAT = totalWithVAT;
    }

    public Double getTotalDiscountAmount() {
        return totalDiscountAmount;
    }

    public void setTotalDiscountAmount(Double totalDiscountAmount) {
        this.totalDiscountAmount = totalDiscountAmount;
    }

    public Double getTotalWithDiscount() {
        return totalWithDiscount;
    }

    public void setTotalWithDiscount(Double totalWithDiscount) {
        this.totalWithDiscount = totalWithDiscount;
    }

    public Double getTotalWithDiscountWithVAT() {
        return totalWithDiscountWithVAT;
    }

    public void setTotalWithDiscountWithVAT(Double totalWithDiscountWithVAT) {
        this.totalWithDiscountWithVAT = totalWithDiscountWithVAT;
    }

    public Double getPaymentCost() {
        return paymentCost;
    }

    public void setPaymentCost(Double paymentCost) {
        this.paymentCost = paymentCost;
    }

    public Double getShipmentCost() {
        return shipmentCost;
    }

    public void setShipmentCost(Double shipmentCost) {
        this.shipmentCost = shipmentCost;
    }

    public Double getFinallyTotal() {
        return finallyTotal;
    }

    public void setFinallyTotal(Double finallyTotal) {
        this.finallyTotal = finallyTotal;
    }

    public HashMap<Integer, CartProduct> getItems() {
        return items;
    }

    public void setItems(HashMap<Integer, CartProduct> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cart cart = (Cart) o;

        if (total != null ? !total.equals(cart.total) : cart.total != null) return false;
        if (totalVATAmount != null ? !totalVATAmount.equals(cart.totalVATAmount) : cart.totalVATAmount != null)
            return false;
        if (totalWithVAT != null ? !totalWithVAT.equals(cart.totalWithVAT) : cart.totalWithVAT != null) return false;
        if (totalDiscountAmount != null ? !totalDiscountAmount.equals(cart.totalDiscountAmount) : cart.totalDiscountAmount != null)
            return false;
        if (totalWithDiscount != null ? !totalWithDiscount.equals(cart.totalWithDiscount) : cart.totalWithDiscount != null)
            return false;
        if (totalWithDiscountWithVAT != null ? !totalWithDiscountWithVAT.equals(cart.totalWithDiscountWithVAT) : cart.totalWithDiscountWithVAT != null)
            return false;
        if (paymentCost != null ? !paymentCost.equals(cart.paymentCost) : cart.paymentCost != null) return false;
        if (shipmentCost != null ? !shipmentCost.equals(cart.shipmentCost) : cart.shipmentCost != null) return false;
        if (finallyTotal != null ? !finallyTotal.equals(cart.finallyTotal) : cart.finallyTotal != null) return false;
        return items != null ? items.equals(cart.items) : cart.items == null;
    }

    @Override
    public int hashCode() {
        int result = total != null ? total.hashCode() : 0;
        result = 31 * result + (totalVATAmount != null ? totalVATAmount.hashCode() : 0);
        result = 31 * result + (totalWithVAT != null ? totalWithVAT.hashCode() : 0);
        result = 31 * result + (totalDiscountAmount != null ? totalDiscountAmount.hashCode() : 0);
        result = 31 * result + (totalWithDiscount != null ? totalWithDiscount.hashCode() : 0);
        result = 31 * result + (totalWithDiscountWithVAT != null ? totalWithDiscountWithVAT.hashCode() : 0);
        result = 31 * result + (paymentCost != null ? paymentCost.hashCode() : 0);
        result = 31 * result + (shipmentCost != null ? shipmentCost.hashCode() : 0);
        result = 31 * result + (finallyTotal != null ? finallyTotal.hashCode() : 0);
        result = 31 * result + (items != null ? items.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "total=" + total +
                ", totalVATAmount=" + totalVATAmount +
                ", totalWithVAT=" + totalWithVAT +
                ", totalDiscountAmount=" + totalDiscountAmount +
                ", totalWithDiscount=" + totalWithDiscount +
                ", totalWithDiscountWithVAT=" + totalWithDiscountWithVAT +
                ", paymentCost=" + paymentCost +
                ", shipmentCost=" + shipmentCost +
                ", finallyTotal=" + finallyTotal +
                ", items=" + items +
                '}';
    }
}
