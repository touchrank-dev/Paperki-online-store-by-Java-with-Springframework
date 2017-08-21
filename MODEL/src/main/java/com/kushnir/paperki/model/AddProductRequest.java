package com.kushnir.paperki.model;

public class AddProductRequest {
    private Integer pnt;
    private Integer quantity;

    public AddProductRequest() {
    }

    public AddProductRequest(Integer pnt, Integer quantity) {
        this.pnt = pnt;
        this.quantity = quantity;
    }

    public Integer getPnt() {
        return pnt;
    }

    public void setPnt(Integer pnt) {
        this.pnt = pnt;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddProductRequest that = (AddProductRequest) o;

        if (pnt != null ? !pnt.equals(that.pnt) : that.pnt != null) return false;
        return quantity != null ? quantity.equals(that.quantity) : that.quantity == null;
    }

    @Override
    public int hashCode() {
        int result = pnt != null ? pnt.hashCode() : 0;
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AddProductRequest{" +
                "pnt=" + pnt +
                ", quantity=" + quantity +
                '}';
    }
}
