package com.kushnir.paperki.model;

public class Discount {
    private Integer productId;
    private Integer pnt;
    private DiscountType discountType;
    private Double valueDouble;
    private Integer valueInt;

    public Discount(Integer pnt, DiscountType discountType, Double valueDouble, Integer valueInt) {
        this.pnt = pnt;
        this.discountType = discountType;
        this.valueDouble = valueDouble;
        this.valueInt = valueInt;
    }

    public Discount(DiscountType discountType, Double valueDouble, Integer valueInt) {
        this.discountType = discountType;
        this.valueDouble = valueDouble;
        this.valueInt = valueInt;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getPnt() {
        return pnt;
    }

    public void setPnt(Integer pnt) {
        this.pnt = pnt;
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

        if (productId != null ? !productId.equals(discount.productId) : discount.productId != null) return false;
        if (pnt != null ? !pnt.equals(discount.pnt) : discount.pnt != null) return false;
        if (discountType != discount.discountType) return false;
        if (valueDouble != null ? !valueDouble.equals(discount.valueDouble) : discount.valueDouble != null)
            return false;
        return valueInt != null ? valueInt.equals(discount.valueInt) : discount.valueInt == null;
    }

    @Override
    public int hashCode() {
        int result = productId != null ? productId.hashCode() : 0;
        result = 31 * result + (pnt != null ? pnt.hashCode() : 0);
        result = 31 * result + (discountType != null ? discountType.hashCode() : 0);
        result = 31 * result + (valueDouble != null ? valueDouble.hashCode() : 0);
        result = 31 * result + (valueInt != null ? valueInt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "productId=" + productId +
                ", pnt=" + pnt +
                ", discountType=" + discountType +
                ", valueDouble=" + valueDouble +
                ", valueInt=" + valueInt +
                '}';
    }
}
