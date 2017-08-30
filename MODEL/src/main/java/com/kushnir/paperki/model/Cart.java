package com.kushnir.paperki.model;

import java.util.HashMap;

public class Cart {

    private double total;                       // сумма
    private double totalWithVAT;                // сумма с налогом
    private double totalWithDiscount;           // сумма с учетом скидки
    private double totalWithDiscountWithVAT;    // сумма с учетом скидки и налога
    private double totalDiscount;               // сумма скидки
    private double totalVAT;                    // сумма налогов
    private Coupon coupon = null;               // скидочный купон
    private Present present = null;             // подарок
    private Payment payment = null;
    private Shipment shipment = null;
    private double paymentCost;                 // стоимость оплаты
    private double shipmentCost;                // стоимость доставки
    private double finalTotal;                  // окончательная сумма
    private double finalTotalWithVAT;           // окончательная сумма с учетом налога
    private HashMap<Integer, CartProduct> items = new HashMap<Integer, CartProduct>();

    public Cart() {
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

    public double getTotalWithDiscount() {
        return totalWithDiscount;
    }

    public void setTotalWithDiscount(double totalWithDiscount) {
        this.totalWithDiscount = totalWithDiscount;
    }

    public double getTotalWithDiscountWithVAT() {
        return totalWithDiscountWithVAT;
    }

    public void setTotalWithDiscountWithVAT(double totalWithDiscountWithVAT) {
        this.totalWithDiscountWithVAT = totalWithDiscountWithVAT;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public double getTotalVAT() {
        return totalVAT;
    }

    public void setTotalVAT(double totalVAT) {
        this.totalVAT = totalVAT;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public Present getPresent() {
        return present;
    }

    public void setPresent(Present present) {
        this.present = present;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Shipment getShipment() {
        return shipment;
    }

    public void setShipment(Shipment shipment) {
        this.shipment = shipment;
    }

    public double getPaymentCost() {
        return paymentCost;
    }

    public void setPaymentCost(double paymentCost) {
        this.paymentCost = paymentCost;
    }

    public double getShipmentCost() {
        return shipmentCost;
    }

    public void setShipmentCost(double shipmentCost) {
        this.shipmentCost = shipmentCost;
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

        if (Double.compare(cart.total, total) != 0) return false;
        if (Double.compare(cart.totalWithVAT, totalWithVAT) != 0) return false;
        if (Double.compare(cart.totalWithDiscount, totalWithDiscount) != 0) return false;
        if (Double.compare(cart.totalWithDiscountWithVAT, totalWithDiscountWithVAT) != 0) return false;
        if (Double.compare(cart.totalDiscount, totalDiscount) != 0) return false;
        if (Double.compare(cart.totalVAT, totalVAT) != 0) return false;
        if (Double.compare(cart.paymentCost, paymentCost) != 0) return false;
        if (Double.compare(cart.shipmentCost, shipmentCost) != 0) return false;
        if (Double.compare(cart.finalTotal, finalTotal) != 0) return false;
        if (Double.compare(cart.finalTotalWithVAT, finalTotalWithVAT) != 0) return false;
        if (coupon != null ? !coupon.equals(cart.coupon) : cart.coupon != null) return false;
        if (present != null ? !present.equals(cart.present) : cart.present != null) return false;
        if (payment != null ? !payment.equals(cart.payment) : cart.payment != null) return false;
        if (shipment != null ? !shipment.equals(cart.shipment) : cart.shipment != null) return false;
        return items != null ? items.equals(cart.items) : cart.items == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(total);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(totalWithVAT);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(totalWithDiscount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(totalWithDiscountWithVAT);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(totalDiscount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(totalVAT);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (coupon != null ? coupon.hashCode() : 0);
        result = 31 * result + (present != null ? present.hashCode() : 0);
        result = 31 * result + (payment != null ? payment.hashCode() : 0);
        result = 31 * result + (shipment != null ? shipment.hashCode() : 0);
        temp = Double.doubleToLongBits(paymentCost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(shipmentCost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(finalTotal);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(finalTotalWithVAT);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (items != null ? items.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "total=" + total +
                ", totalWithVAT=" + totalWithVAT +
                ", totalWithDiscount=" + totalWithDiscount +
                ", totalWithDiscountWithVAT=" + totalWithDiscountWithVAT +
                ", totalDiscount=" + totalDiscount +
                ", totalVAT=" + totalVAT +
                ", coupon=" + coupon +
                ", present=" + present +
                ", payment=" + payment +
                ", shipment=" + shipment +
                ", paymentCost=" + paymentCost +
                ", shipmentCost=" + shipmentCost +
                ", finalTotal=" + finalTotal +
                ", finalTotalWithVAT=" + finalTotalWithVAT +
                ", items=" + items +
                '}';
    }
}
