package com.kushnir.paperki.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;

public class CartProduct {
    private Integer pnt;
    private String fullName;
    private String shortName;
    private Integer VAT;
    private Double vatAmount;
    private Integer quantity;
    private Integer quantityAvailable;
    private Double currentPrice;
    private Double currentPriceWithVAT;
    private Double discountAmount;
    private Double discountedPrice;
    private Double discountedPriceWithVAT;
    private Double totalPrice;
    private Double totalPriceWithVAT;
    private Double totalDiscountedPrice;
    private Double totalDiscountedPriceWithVAT;

    private HashMap<Integer, Price> prices = new HashMap<>();

    public CartProduct() {
    }

    public CartProduct(Integer pnt,
                       String fullName,
                       String shortName,
                       Integer VAT,
                       Integer quantityAvailable) {
        this.pnt = pnt;
        this.fullName = fullName;
        this.shortName = shortName;
        this.VAT = VAT;
        this.quantityAvailable = quantityAvailable;
    }


    private void calcVatAmounth() {
        this.vatAmount = 1 + (VAT/100.0);
    }

    private void calcCurrPriceWithVat() {
        this.currentPriceWithVAT = new BigDecimal(this.currentPrice * this.vatAmount)
                .setScale(2, RoundingMode.UP).doubleValue();
    }

    private void calcTotalPrice() {
        this.totalPrice = currentPrice * this.quantity;
    }

    private void calcTotalPriceWithVat(){
        this.totalPriceWithVAT = new BigDecimal(this.totalPrice * this.vatAmount)
                .setScale(2, RoundingMode.UP).doubleValue();
    }


    public Integer getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(Integer quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
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
    }

    public Double getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(Double vatAmount) {
        this.vatAmount = vatAmount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Double getCurrentPriceWithVAT() {
        return currentPriceWithVAT;
    }

    public void setCurrentPriceWithVAT(Double currentPriceWithVAT) {
        this.currentPriceWithVAT = currentPriceWithVAT;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(Double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public Double getDiscountedPriceWithVAT() {
        return discountedPriceWithVAT;
    }

    public void setDiscountedPriceWithVAT(Double discountedPriceWithVAT) {
        this.discountedPriceWithVAT = discountedPriceWithVAT;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getTotalPriceWithVAT() {
        return totalPriceWithVAT;
    }

    public void setTotalPriceWithVAT(Double totalPriceWithVAT) {
        this.totalPriceWithVAT = totalPriceWithVAT;
    }

    public Double getTotalDiscountedPrice() {
        return totalDiscountedPrice;
    }

    public void setTotalDiscountedPrice(Double totalDiscountedPrice) {
        this.totalDiscountedPrice = totalDiscountedPrice;
    }

    public Double getTotalDiscountedPriceWithVAT() {
        return totalDiscountedPriceWithVAT;
    }

    public void setTotalDiscountedPriceWithVAT(Double totalDiscountedPriceWithVAT) {
        this.totalDiscountedPriceWithVAT = totalDiscountedPriceWithVAT;
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
        if (vatAmount != null ? !vatAmount.equals(that.vatAmount) : that.vatAmount != null) return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        if (quantityAvailable != null ? !quantityAvailable.equals(that.quantityAvailable) : that.quantityAvailable != null)
            return false;
        if (currentPrice != null ? !currentPrice.equals(that.currentPrice) : that.currentPrice != null) return false;
        if (currentPriceWithVAT != null ? !currentPriceWithVAT.equals(that.currentPriceWithVAT) : that.currentPriceWithVAT != null)
            return false;
        if (discountAmount != null ? !discountAmount.equals(that.discountAmount) : that.discountAmount != null)
            return false;
        if (discountedPrice != null ? !discountedPrice.equals(that.discountedPrice) : that.discountedPrice != null)
            return false;
        if (discountedPriceWithVAT != null ? !discountedPriceWithVAT.equals(that.discountedPriceWithVAT) : that.discountedPriceWithVAT != null)
            return false;
        if (totalPrice != null ? !totalPrice.equals(that.totalPrice) : that.totalPrice != null) return false;
        if (totalPriceWithVAT != null ? !totalPriceWithVAT.equals(that.totalPriceWithVAT) : that.totalPriceWithVAT != null)
            return false;
        if (totalDiscountedPrice != null ? !totalDiscountedPrice.equals(that.totalDiscountedPrice) : that.totalDiscountedPrice != null)
            return false;
        if (totalDiscountedPriceWithVAT != null ? !totalDiscountedPriceWithVAT.equals(that.totalDiscountedPriceWithVAT) : that.totalDiscountedPriceWithVAT != null)
            return false;
        return prices != null ? prices.equals(that.prices) : that.prices == null;
    }

    @Override
    public int hashCode() {
        int result = pnt != null ? pnt.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        result = 31 * result + (VAT != null ? VAT.hashCode() : 0);
        result = 31 * result + (vatAmount != null ? vatAmount.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (quantityAvailable != null ? quantityAvailable.hashCode() : 0);
        result = 31 * result + (currentPrice != null ? currentPrice.hashCode() : 0);
        result = 31 * result + (currentPriceWithVAT != null ? currentPriceWithVAT.hashCode() : 0);
        result = 31 * result + (discountAmount != null ? discountAmount.hashCode() : 0);
        result = 31 * result + (discountedPrice != null ? discountedPrice.hashCode() : 0);
        result = 31 * result + (discountedPriceWithVAT != null ? discountedPriceWithVAT.hashCode() : 0);
        result = 31 * result + (totalPrice != null ? totalPrice.hashCode() : 0);
        result = 31 * result + (totalPriceWithVAT != null ? totalPriceWithVAT.hashCode() : 0);
        result = 31 * result + (totalDiscountedPrice != null ? totalDiscountedPrice.hashCode() : 0);
        result = 31 * result + (totalDiscountedPriceWithVAT != null ? totalDiscountedPriceWithVAT.hashCode() : 0);
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
                ", vatAmount=" + vatAmount +
                ", quantity=" + quantity +
                ", quantityAvailable=" + quantityAvailable +
                ", currentPrice=" + currentPrice +
                ", currentPriceWithVAT=" + currentPriceWithVAT +
                ", discountAmount=" + discountAmount +
                ", discountedPrice=" + discountedPrice +
                ", discountedPriceWithVAT=" + discountedPriceWithVAT +
                ", totalPrice=" + totalPrice +
                ", totalPriceWithVAT=" + totalPriceWithVAT +
                ", totalDiscountedPrice=" + totalDiscountedPrice +
                ", totalDiscountedPriceWithVAT=" + totalDiscountedPriceWithVAT +
                ", prices=" + prices +
                '}';
    }
}
