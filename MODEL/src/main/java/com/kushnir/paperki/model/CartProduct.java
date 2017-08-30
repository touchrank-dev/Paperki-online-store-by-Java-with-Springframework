package com.kushnir.paperki.model;

import java.util.HashMap;

public class CartProduct {
    private int pnt;
    private String fullName;
    private String shortName;
    private String link;
    private int VAT;                            // значение налога
    private double vatValue;                    // коэффициент налога

    private int quantity;                       // количество в корзине

    private double currentPrice;                // обычная цена
    private double currentPriceWithVAT;         // обычная цена с налогом

    private double discountedPrice;             // цена со скидкой
    private double discountedPriceWithVAT;      // цена со скидкой и налогом
    private double discountAmount;              // размер скидки от цены TODO учет налога в размере скидки?

    private double finalPrice;                  // окончательная цена со скидкой
    private double finalPriceWithVAT;           // окончательная цена со скидкой и налогом

    private double total;                       // сумма
    private double totalWithVAT;                // сумма с налогом

    private double totalWithDiscount;           // сумма с учетом скидки
    private double totalWithDiscountWithVAT;    // сумма с учетом скидки и налога
    private double totalDiscount;               // сумма скидки TODO учет налога в сумме скидки?

    private double finalTotal;                  // окончательная сумма
    private double finalTotalWithVAT;           // окончательная сумма с учетом налога

    private double totalVAT;                    // сумма налогов

    private HashMap<Integer, Price> prices = new HashMap<>();

    public CartProduct() {}


    public int getPnt() {
        return pnt;
    }

    public void setPnt(int pnt) {
        this.pnt = pnt;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getVAT() {
        return VAT;
    }

    public void setVAT(int VAT) {
        this.VAT = VAT;
    }

    public double getVatValue() {
        return vatValue;
    }

    public void setVatValue(double vatValue) {
        this.vatValue = vatValue;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getCurrentPriceWithVAT() {
        return currentPriceWithVAT;
    }

    public void setCurrentPriceWithVAT(double currentPriceWithVAT) {
        this.currentPriceWithVAT = currentPriceWithVAT;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public double getDiscountedPriceWithVAT() {
        return discountedPriceWithVAT;
    }

    public void setDiscountedPriceWithVAT(double discountedPriceWithVAT) {
        this.discountedPriceWithVAT = discountedPriceWithVAT;
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

    public double getFinalPriceWithVAT() {
        return finalPriceWithVAT;
    }

    public void setFinalPriceWithVAT(double finalPriceWithVAT) {
        this.finalPriceWithVAT = finalPriceWithVAT;
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

    public double getTotalVAT() {
        return totalVAT;
    }

    public void setTotalVAT(double totalVAT) {
        this.totalVAT = totalVAT;
    }

    public HashMap<Integer, Price> getPrices() {
        return prices;
    }

    public void setPrices(HashMap<Integer, Price> prices) {
        this.prices = prices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CartProduct that = (CartProduct) o;

        if (pnt != that.pnt) return false;
        if (VAT != that.VAT) return false;
        if (Double.compare(that.vatValue, vatValue) != 0) return false;
        if (quantity != that.quantity) return false;
        if (Double.compare(that.currentPrice, currentPrice) != 0) return false;
        if (Double.compare(that.currentPriceWithVAT, currentPriceWithVAT) != 0) return false;
        if (Double.compare(that.discountedPrice, discountedPrice) != 0) return false;
        if (Double.compare(that.discountedPriceWithVAT, discountedPriceWithVAT) != 0) return false;
        if (Double.compare(that.discountAmount, discountAmount) != 0) return false;
        if (Double.compare(that.finalPrice, finalPrice) != 0) return false;
        if (Double.compare(that.finalPriceWithVAT, finalPriceWithVAT) != 0) return false;
        if (Double.compare(that.total, total) != 0) return false;
        if (Double.compare(that.totalWithVAT, totalWithVAT) != 0) return false;
        if (Double.compare(that.totalWithDiscount, totalWithDiscount) != 0) return false;
        if (Double.compare(that.totalWithDiscountWithVAT, totalWithDiscountWithVAT) != 0) return false;
        if (Double.compare(that.totalDiscount, totalDiscount) != 0) return false;
        if (Double.compare(that.finalTotal, finalTotal) != 0) return false;
        if (Double.compare(that.finalTotalWithVAT, finalTotalWithVAT) != 0) return false;
        if (Double.compare(that.totalVAT, totalVAT) != 0) return false;
        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null) return false;
        if (shortName != null ? !shortName.equals(that.shortName) : that.shortName != null) return false;
        if (link != null ? !link.equals(that.link) : that.link != null) return false;
        return prices != null ? prices.equals(that.prices) : that.prices == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = pnt;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + VAT;
        temp = Double.doubleToLongBits(vatValue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + quantity;
        temp = Double.doubleToLongBits(currentPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(currentPriceWithVAT);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(discountedPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(discountedPriceWithVAT);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(discountAmount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(finalPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(finalPriceWithVAT);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(total);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(totalWithVAT);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(totalWithDiscount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(totalWithDiscountWithVAT);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(totalDiscount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(finalTotal);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(finalTotalWithVAT);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(totalVAT);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (prices != null ? prices.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CartProduct{" +
                "pnt=" + pnt +
                ", fullName='" + fullName + '\'' +
                ", shortName='" + shortName + '\'' +
                ", link='" + link + '\'' +
                ", VAT=" + VAT +
                ", vatValue=" + vatValue +
                ", quantity=" + quantity +
                ", currentPrice=" + currentPrice +
                ", currentPriceWithVAT=" + currentPriceWithVAT +
                ", discountedPrice=" + discountedPrice +
                ", discountedPriceWithVAT=" + discountedPriceWithVAT +
                ", discountAmount=" + discountAmount +
                ", finalPrice=" + finalPrice +
                ", finalPriceWithVAT=" + finalPriceWithVAT +
                ", total=" + total +
                ", totalWithVAT=" + totalWithVAT +
                ", totalWithDiscount=" + totalWithDiscount +
                ", totalWithDiscountWithVAT=" + totalWithDiscountWithVAT +
                ", totalDiscount=" + totalDiscount +
                ", finalTotal=" + finalTotal +
                ", finalTotalWithVAT=" + finalTotalWithVAT +
                ", totalVAT=" + totalVAT +
                ", prices=" + prices +
                '}';
    }
}
