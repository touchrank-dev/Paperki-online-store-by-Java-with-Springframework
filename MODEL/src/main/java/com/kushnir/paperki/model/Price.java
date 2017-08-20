package com.kushnir.paperki.model;

public class Price {
    private String type;
    private Integer quantityStart;
    private Integer quantityEnd;
    private Double basePrice;
    private Integer discount;
    private Double basePriceWithVat;
    private Double overridePrice;

    private Double VatValue;

    public Price() {
    }

    public Price(Integer quantityStart, Integer quantityEnd, Double basePrice, Integer VAT) {
        this.quantityStart = quantityStart;
        this.quantityEnd = quantityEnd;
        this.basePrice = basePrice;
        this.basePriceWithVat = basePrice * Math.rint(VAT/100.0);
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

    public Integer getQuantityEnd() {
        return quantityEnd;
    }

    public void setQuantityEnd(Integer quantityEnd) {
        this.quantityEnd = quantityEnd;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Price price = (Price) o;

        if (type != null ? !type.equals(price.type) : price.type != null) return false;
        if (quantityStart != null ? !quantityStart.equals(price.quantityStart) : price.quantityStart != null)
            return false;
        if (quantityEnd != null ? !quantityEnd.equals(price.quantityEnd) : price.quantityEnd != null) return false;
        if (basePrice != null ? !basePrice.equals(price.basePrice) : price.basePrice != null) return false;
        if (discount != null ? !discount.equals(price.discount) : price.discount != null) return false;
        if (basePriceWithVat != null ? !basePriceWithVat.equals(price.basePriceWithVat) : price.basePriceWithVat != null)
            return false;
        return overridePrice != null ? overridePrice.equals(price.overridePrice) : price.overridePrice == null;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (quantityStart != null ? quantityStart.hashCode() : 0);
        result = 31 * result + (quantityEnd != null ? quantityEnd.hashCode() : 0);
        result = 31 * result + (basePrice != null ? basePrice.hashCode() : 0);
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        result = 31 * result + (basePriceWithVat != null ? basePriceWithVat.hashCode() : 0);
        result = 31 * result + (overridePrice != null ? overridePrice.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Price{" +
                "type='" + type + '\'' +
                ", quantityStart=" + quantityStart +
                ", quantityEnd=" + quantityEnd +
                ", basePrice=" + basePrice +
                ", discount=" + discount +
                ", basePriceWithVat=" + basePriceWithVat +
                ", overridePrice=" + overridePrice +
                '}';
    }
}
