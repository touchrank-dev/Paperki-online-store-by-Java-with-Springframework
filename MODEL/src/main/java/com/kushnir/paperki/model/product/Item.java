package com.kushnir.paperki.model.product;

public class Item {
    private int pnt;
    private int quantity;
    private double basePrice;
    private double basePriceWithVat;
    private double discountAmount;
    private double finalPrice;
    private double finalPriceWithVat;
    private double total;
    private double totalWithVat;
    private double totalDiscount;

    public Item() {
    }

    public Item(int pnt,
                int quantity,
                double basePrice,
                double basePriceWithVat,
                double discountAmount,
                double finalPrice,
                double finalPriceWithVat,
                double total,
                double totalWithVat,
                double totalDiscount) {
        this.pnt = pnt;
        this.quantity = quantity;
        this.basePrice = basePrice;
        this.basePriceWithVat = basePriceWithVat;
        this.discountAmount = discountAmount;
        this.finalPrice = finalPrice;
        this.finalPriceWithVat = finalPriceWithVat;
        this.total = total;
        this.totalWithVat = totalWithVat;
        this.totalDiscount = totalDiscount;
    }

    public int getPnt() {
        return pnt;
    }

    public void setPnt(int pnt) {
        this.pnt = pnt;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getBasePriceWithVat() {
        return basePriceWithVat;
    }

    public void setBasePriceWithVat(double basePriceWithVat) {
        this.basePriceWithVat = basePriceWithVat;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public double getFinalPriceWithVat() {
        return finalPriceWithVat;
    }

    public void setFinalPriceWithVat(double finalPriceWithVat) {
        this.finalPriceWithVat = finalPriceWithVat;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotalWithVat() {
        return totalWithVat;
    }

    public void setTotalWithVat(double totalWithVat) {
        this.totalWithVat = totalWithVat;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (pnt != item.pnt) return false;
        if (quantity != item.quantity) return false;
        if (Double.compare(item.basePrice, basePrice) != 0) return false;
        if (Double.compare(item.basePriceWithVat, basePriceWithVat) != 0) return false;
        if (Double.compare(item.discountAmount, discountAmount) != 0) return false;
        if (Double.compare(item.finalPrice, finalPrice) != 0) return false;
        if (Double.compare(item.finalPriceWithVat, finalPriceWithVat) != 0) return false;
        if (Double.compare(item.total, total) != 0) return false;
        if (Double.compare(item.totalWithVat, totalWithVat) != 0) return false;
        return Double.compare(item.totalDiscount, totalDiscount) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = pnt;
        result = 31 * result + quantity;
        temp = Double.doubleToLongBits(basePrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(basePriceWithVat);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(discountAmount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(finalPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(finalPriceWithVat);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(total);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(totalWithVat);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(totalDiscount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Item{" +
                "pnt=" + pnt +
                ", quantity=" + quantity +
                ", basePrice=" + basePrice +
                ", basePriceWithVat=" + basePriceWithVat +
                ", discountAmount=" + discountAmount +
                ", finalPrice=" + finalPrice +
                ", finalPriceWithVat=" + finalPriceWithVat +
                ", total=" + total +
                ", totalWithVat=" + totalWithVat +
                ", totalDiscount=" + totalDiscount +
                '}';
    }
}
