package com.kushnir.paperki.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

public class CartProduct {
    private Integer pnt;
    private String fullName;
    private String shortName;
    private Integer VAT;
    private Integer quantity;
    private Double currentPrice;
    private Double currentPriceWithVAT;
    private Double totalPrice;
    private Double totalPriceWithVAT;
    private Double vatAmount;
    private Double discountedPrice;
    private Double discountedPriceWithVAT;

    private HashMap<Integer, Price> prices;

    public CartProduct() {
    }

    public CartProduct(Integer pnt,
                       String fullName,
                       String shortName,
                       Integer VAT,
                       Integer quantity,
                       Double currentPrice) {
        this.pnt = pnt;
        this.fullName = fullName;
        this.shortName = shortName;
        this.VAT = VAT;
        this.vatAmount = 1 + (VAT/100.0);
        this.quantity = quantity;
        this.currentPrice = currentPrice;
        this.currentPriceWithVAT = new BigDecimal(this.currentPrice * this.vatAmount)
                .setScale(2, RoundingMode.UP).doubleValue();
    }

    private void calculate(){
        this.totalPrice = currentPrice * this.quantity;
        this.totalPriceWithVAT = new BigDecimal(this.totalPrice * this.vatAmount)
                .setScale(2, RoundingMode.UP).doubleValue();
    }

    public Integer getPnt() {
        return pnt;
    }

    public void setPnt(Integer pnt) {
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

    public Integer getVAT() {
        return VAT;
    }

    public void setVAT(Integer VAT) {
        this.VAT = VAT;
        calculate();
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
        calculate();
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
        calculate();
    }

    public Double getCurrentPriceWithVAT() {
        return currentPriceWithVAT;
    }

    public void setCurrentPriceWithVAT(Double currentPriceWithVAT) {
        this.currentPriceWithVAT = currentPriceWithVAT;
        calculate();
    }

    public Double getVatAmount() {
        return vatAmount;
    }

    public Double getDiscountedPrice() {
        return discountedPrice;
    }

    public Double getDiscountedPriceWithVAT() {
        return discountedPriceWithVAT;
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

        if (pnt != null ? !pnt.equals(that.pnt) : that.pnt != null) return false;
        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null) return false;
        if (shortName != null ? !shortName.equals(that.shortName) : that.shortName != null) return false;
        if (VAT != null ? !VAT.equals(that.VAT) : that.VAT != null) return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        if (currentPrice != null ? !currentPrice.equals(that.currentPrice) : that.currentPrice != null) return false;
        if (currentPriceWithVAT != null ? !currentPriceWithVAT.equals(that.currentPriceWithVAT) : that.currentPriceWithVAT != null)
            return false;
        if (vatAmount != null ? !vatAmount.equals(that.vatAmount) : that.vatAmount != null) return false;
        if (discountedPrice != null ? !discountedPrice.equals(that.discountedPrice) : that.discountedPrice != null)
            return false;
        if (discountedPriceWithVAT != null ? !discountedPriceWithVAT.equals(that.discountedPriceWithVAT) : that.discountedPriceWithVAT != null)
            return false;
        return prices != null ? prices.equals(that.prices) : that.prices == null;
    }

    @Override
    public int hashCode() {
        int result = pnt != null ? pnt.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        result = 31 * result + (VAT != null ? VAT.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (currentPrice != null ? currentPrice.hashCode() : 0);
        result = 31 * result + (currentPriceWithVAT != null ? currentPriceWithVAT.hashCode() : 0);
        result = 31 * result + (vatAmount != null ? vatAmount.hashCode() : 0);
        result = 31 * result + (discountedPrice != null ? discountedPrice.hashCode() : 0);
        result = 31 * result + (discountedPriceWithVAT != null ? discountedPriceWithVAT.hashCode() : 0);
        result = 31 * result + (prices != null ? prices.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CartProduct{" +
                "pnt=" + pnt +
                ", fullName='" + fullName + '\'' +
                ", shortName='" + shortName + '\'' +
                ", VAT=" + VAT +
                ", quantity=" + quantity +
                ", currentPrice=" + currentPrice +
                ", currentPriceWithVAT=" + currentPriceWithVAT +
                ", vatAmount=" + vatAmount +
                ", discountedPrice=" + discountedPrice +
                ", discountedPriceWithVAT=" + discountedPriceWithVAT +
                ", prices=" + prices +
                '}';
    }
}
