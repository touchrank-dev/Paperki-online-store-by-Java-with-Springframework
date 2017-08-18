package com.kushnir.paperki.model;

public class Price {
    private String type;
    private Double basePrice;
    private Integer discount;
    private Double basePriceWithVat;
    private Double overridePrice;

    public Price() {
    }

    public Price(Double basePrice, Double basePriceWithVat, Double overridePrice) {
        this.basePrice = basePrice;
        this.basePriceWithVat = basePriceWithVat;
        this.overridePrice = overridePrice;
    }

    public Double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Double basePrice) {
        this.basePrice = basePrice;
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

        if (basePrice != null ? !basePrice.equals(price.basePrice) : price.basePrice != null) return false;
        if (basePriceWithVat != null ? !basePriceWithVat.equals(price.basePriceWithVat) : price.basePriceWithVat != null)
            return false;
        return overridePrice != null ? overridePrice.equals(price.overridePrice) : price.overridePrice == null;
    }

    @Override
    public int hashCode() {
        int result = basePrice != null ? basePrice.hashCode() : 0;
        result = 31 * result + (basePriceWithVat != null ? basePriceWithVat.hashCode() : 0);
        result = 31 * result + (overridePrice != null ? overridePrice.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Price{" +
                "basePrice=" + basePrice +
                ", basePriceWithVat=" + basePriceWithVat +
                ", overridePrice=" + overridePrice +
                '}';
    }
}
