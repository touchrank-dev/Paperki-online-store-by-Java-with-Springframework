package com.kushnir.paperki.model;

public class Price {

    private int quantityStart;
    private double basePrice;
    private double basePriceWithVAT;

    public Price() {
    }

    public Price(int quantityStart, double basePrice, double basePriceWithVAT) {
        this.quantityStart = quantityStart;
        this.basePrice = basePrice;
        this.basePriceWithVAT = basePriceWithVAT;
    }

    public int getQuantityStart() {
        return quantityStart;
    }

    public void setQuantityStart(int quantityStart) {
        this.quantityStart = quantityStart;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getBasePriceWithVAT() {
        return basePriceWithVAT;
    }

    public void setBasePriceWithVAT(double basePriceWithVAT) {
        this.basePriceWithVAT = basePriceWithVAT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Price price = (Price) o;

        if (quantityStart != price.quantityStart) return false;
        if (Double.compare(price.basePrice, basePrice) != 0) return false;
        return Double.compare(price.basePriceWithVAT, basePriceWithVAT) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = quantityStart;
        temp = Double.doubleToLongBits(basePrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(basePriceWithVAT);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Price{" +
                "quantityStart=" + quantityStart +
                ", basePrice=" + basePrice +
                ", basePriceWithVAT=" + basePriceWithVAT +
                '}';
    }
}
