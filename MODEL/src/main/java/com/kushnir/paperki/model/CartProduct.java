package com.kushnir.paperki.model;

import java.util.HashMap;

public class CartProduct {
    private Integer pnt;
    private String fullName;
    private String shortName;
    private Integer VAT;                        // значение налога
    private Double vatValue;                    // множитель налога
    private Double vatAmount;                   // сумма налога
    private Integer quantity;                   // количество в корзине
    private Double currentPrice;                // обычная цена
    private Double currentPriceWithVAT;         // обычная цена с налогом
    private Double discountAmount;              // размер скидки
    private Double discountedPrice;             // цена со скидкой
    private Double discountedPriceWithVAT;      // цена со скидкой и налогом
    private Double finallyPrice;                // окончательная цена со скидкой
    private Double finallyPriceWithVAT;         // окончательная цена со скидкой и налогом
    private Double total;                       // сумма
    private Double totalWithVAT;                // сумма с налогом
    private HashMap<Integer, Price> prices = new HashMap<>();

    public CartProduct() {}

    public CartProduct(Integer pnt, String fullName, String shortName, Integer VAT) {
        this.pnt = pnt;
        this.fullName = fullName;
        this.shortName = shortName;
        this.VAT = VAT;
    }

    public CartProduct(Integer pnt,
                       String fullName,
                       String shortName,
                       Integer VAT,
                       Double vatValue,
                       Double vatAmount,
                       Integer quantity,
                       Double currentPrice,
                       Double currentPriceWithVAT,
                       Double discountAmount,
                       Double discountedPrice,
                       Double discountedPriceWithVAT,
                       Double finallyPrice,
                       Double finallyPriceWithVAT,
                       Double total,
                       Double totalWithVAT,
                       HashMap<Integer, Price> prices) {
        this.pnt = pnt;
        this.fullName = fullName;
        this.shortName = shortName;
        this.VAT = VAT;
        this.vatValue = vatValue;
        this.vatAmount = vatAmount;
        this.quantity = quantity;
        this.currentPrice = currentPrice;
        this.currentPriceWithVAT = currentPriceWithVAT;
        this.discountAmount = discountAmount;
        this.discountedPrice = discountedPrice;
        this.discountedPriceWithVAT = discountedPriceWithVAT;
        this.finallyPrice = finallyPrice;
        this.finallyPriceWithVAT = finallyPriceWithVAT;
        this.total = total;
        this.totalWithVAT = totalWithVAT;
        this.prices = prices;
    }

    public CartProduct(Integer pnt,
                       String fullName,
                       String shortName,
                       Integer VAT,
                       Double vatValue,
                       Double vatAmount,
                       Integer quantity,
                       Double currentPrice,
                       Double currentPriceWithVAT,
                       Double discountAmount,
                       Double discountedPrice,
                       Double discountedPriceWithVAT,
                       Double finallyPrice,
                       Double finallyPriceWithVAT,
                       Double total,
                       Double totalWithVAT) {
        this.pnt = pnt;
        this.fullName = fullName;
        this.shortName = shortName;
        this.VAT = VAT;
        this.vatValue = vatValue;
        this.vatAmount = vatAmount;
        this.quantity = quantity;
        this.currentPrice = currentPrice;
        this.currentPriceWithVAT = currentPriceWithVAT;
        this.discountAmount = discountAmount;
        this.discountedPrice = discountedPrice;
        this.discountedPriceWithVAT = discountedPriceWithVAT;
        this.finallyPrice = finallyPrice;
        this.finallyPriceWithVAT = finallyPriceWithVAT;
        this.total = total;
        this.totalWithVAT = totalWithVAT;
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

    public Double getVatValue() {
        return vatValue;
    }

    public void setVatValue(Double vatValue) {
        this.vatValue = vatValue;
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

    public Double getFinallyPrice() {
        return finallyPrice;
    }

    public void setFinallyPrice(Double finallyPrice) {
        this.finallyPrice = finallyPrice;
    }

    public Double getFinallyPriceWithVAT() {
        return finallyPriceWithVAT;
    }

    public void setFinallyPriceWithVAT(Double finallyPriceWithVAT) {
        this.finallyPriceWithVAT = finallyPriceWithVAT;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getTotalWithVAT() {
        return totalWithVAT;
    }

    public void setTotalWithVAT(Double totalWithVAT) {
        this.totalWithVAT = totalWithVAT;
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
        if (vatValue != null ? !vatValue.equals(that.vatValue) : that.vatValue != null) return false;
        if (vatAmount != null ? !vatAmount.equals(that.vatAmount) : that.vatAmount != null) return false;
        if (quantity != null ? !quantity.equals(that.quantity) : that.quantity != null) return false;
        if (currentPrice != null ? !currentPrice.equals(that.currentPrice) : that.currentPrice != null) return false;
        if (currentPriceWithVAT != null ? !currentPriceWithVAT.equals(that.currentPriceWithVAT) : that.currentPriceWithVAT != null)
            return false;
        if (discountAmount != null ? !discountAmount.equals(that.discountAmount) : that.discountAmount != null)
            return false;
        if (discountedPrice != null ? !discountedPrice.equals(that.discountedPrice) : that.discountedPrice != null)
            return false;
        if (discountedPriceWithVAT != null ? !discountedPriceWithVAT.equals(that.discountedPriceWithVAT) : that.discountedPriceWithVAT != null)
            return false;
        if (finallyPrice != null ? !finallyPrice.equals(that.finallyPrice) : that.finallyPrice != null) return false;
        if (finallyPriceWithVAT != null ? !finallyPriceWithVAT.equals(that.finallyPriceWithVAT) : that.finallyPriceWithVAT != null)
            return false;
        if (total != null ? !total.equals(that.total) : that.total != null) return false;
        if (totalWithVAT != null ? !totalWithVAT.equals(that.totalWithVAT) : that.totalWithVAT != null) return false;
        return prices != null ? prices.equals(that.prices) : that.prices == null;
    }

    @Override
    public int hashCode() {
        int result = pnt != null ? pnt.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        result = 31 * result + (VAT != null ? VAT.hashCode() : 0);
        result = 31 * result + (vatValue != null ? vatValue.hashCode() : 0);
        result = 31 * result + (vatAmount != null ? vatAmount.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (currentPrice != null ? currentPrice.hashCode() : 0);
        result = 31 * result + (currentPriceWithVAT != null ? currentPriceWithVAT.hashCode() : 0);
        result = 31 * result + (discountAmount != null ? discountAmount.hashCode() : 0);
        result = 31 * result + (discountedPrice != null ? discountedPrice.hashCode() : 0);
        result = 31 * result + (discountedPriceWithVAT != null ? discountedPriceWithVAT.hashCode() : 0);
        result = 31 * result + (finallyPrice != null ? finallyPrice.hashCode() : 0);
        result = 31 * result + (finallyPriceWithVAT != null ? finallyPriceWithVAT.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        result = 31 * result + (totalWithVAT != null ? totalWithVAT.hashCode() : 0);
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
                ", vatValue=" + vatValue +
                ", vatAmount=" + vatAmount +
                ", quantity=" + quantity +
                ", currentPrice=" + currentPrice +
                ", currentPriceWithVAT=" + currentPriceWithVAT +
                ", discountAmount=" + discountAmount +
                ", discountedPrice=" + discountedPrice +
                ", discountedPriceWithVAT=" + discountedPriceWithVAT +
                ", finallyPrice=" + finallyPrice +
                ", finallyPriceWithVAT=" + finallyPriceWithVAT +
                ", total=" + total +
                ", totalWithVAT=" + totalWithVAT +
                ", prices=" + prices +
                '}';
    }
}
