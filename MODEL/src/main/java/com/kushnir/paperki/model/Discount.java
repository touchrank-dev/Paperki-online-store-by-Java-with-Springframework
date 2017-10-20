package com.kushnir.paperki.model;

public class Discount {
    private DiscountType discountType;
    private Double valueDouble;
    private Integer valueInt;

    public Discount(DiscountType discountType, Double valueDouble, Integer valueInt) {
        this.discountType = discountType;
        this.valueDouble = valueDouble;
        this.valueInt = valueInt;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public Double getValueDouble() {
        return valueDouble;
    }

    public void setValueDouble(Double valueDouble) {
        this.valueDouble = valueDouble;
    }

    public Integer getValueInt() {
        return valueInt;
    }

    public void setValueInt(Integer valueInt) {
        this.valueInt = valueInt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Discount discount = (Discount) o;

        if (discountType != discount.discountType) return false;
        if (valueDouble != null ? !valueDouble.equals(discount.valueDouble) : discount.valueDouble != null)
            return false;
        return valueInt != null ? valueInt.equals(discount.valueInt) : discount.valueInt == null;
    }

    @Override
    public int hashCode() {
        int result = discountType != null ? discountType.hashCode() : 0;
        result = 31 * result + (valueDouble != null ? valueDouble.hashCode() : 0);
        result = 31 * result + (valueInt != null ? valueInt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "discountType=" + discountType +
                ", valueDouble=" + valueDouble +
                ", valueInt=" + valueInt +
                '}';
    }
}
