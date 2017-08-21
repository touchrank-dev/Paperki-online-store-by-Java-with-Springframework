package com.kushnir.paperki.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Price {
    private String type;
    private Integer quantityStart;
    private Double basePrice;
    private Integer discount;
    private Double basePriceWithVat;
    private Double overridePrice;

    private Double vatValue;

    /*
        String pattern = "##0.00";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        String format = decimalFormat.format([...]);
    */

    public Price() {
    }

    public Price(Integer quantityStart, Double basePrice, Integer VAT) {
        this.quantityStart = quantityStart;
        this.basePrice = basePrice;
        this.vatValue = 1 + (VAT/100.0);
        this.basePriceWithVat =
                new BigDecimal(this.basePrice * this.vatValue)
                        .setScale(2, RoundingMode.UP).doubleValue();
        // Math.rint(this.basePrice * this.vatValue);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getQuantityStart() {
        return quantityStart;
    }

    public void setQuantityStart(Integer quantityStart) {
        this.quantityStart = quantityStart;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Double getBasePriceWithVat() {
        return basePriceWithVat;
    }

    public void setBasePriceWithVat(Double basePriceWithVat) {
        this.basePriceWithVat = basePriceWithVat;
    }

    public Double getOverridePrice() {
        return overridePrice;
    }

    public void setOverridePrice(Double overridePrice) {
        this.overridePrice = overridePrice;
    }

    public Double getVatValue() {
        return vatValue;
    }

    public void setVatValue(Double vatValue) {
        this.vatValue = vatValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Price price = (Price) o;

        if (type != null ? !type.equals(price.type) : price.type != null) return false;
        if (quantityStart != null ? !quantityStart.equals(price.quantityStart) : price.quantityStart != null)
            return false;
        if (basePrice != null ? !basePrice.equals(price.basePrice) : price.basePrice != null) return false;
        if (discount != null ? !discount.equals(price.discount) : price.discount != null) return false;
        if (basePriceWithVat != null ? !basePriceWithVat.equals(price.basePriceWithVat) : price.basePriceWithVat != null)
            return false;
        if (overridePrice != null ? !overridePrice.equals(price.overridePrice) : price.overridePrice != null)
            return false;
        return vatValue != null ? vatValue.equals(price.vatValue) : price.vatValue == null;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (quantityStart != null ? quantityStart.hashCode() : 0);
        result = 31 * result + (basePrice != null ? basePrice.hashCode() : 0);
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        result = 31 * result + (basePriceWithVat != null ? basePriceWithVat.hashCode() : 0);
        result = 31 * result + (overridePrice != null ? overridePrice.hashCode() : 0);
        result = 31 * result + (vatValue != null ? vatValue.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Price{" +
                "type='" + type + '\'' +
                ", quantityStart=" + quantityStart +
                ", basePrice=" + basePrice +
                ", discount=" + discount +
                ", basePriceWithVat=" + basePriceWithVat +
                ", overridePrice=" + overridePrice +
                ", vatValue=" + vatValue +
                '}';
    }
}
