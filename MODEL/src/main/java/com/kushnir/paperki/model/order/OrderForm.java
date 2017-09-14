package com.kushnir.paperki.model.order;

public class OrderForm {

    private OrderType orderType;

    public OrderForm() {
    }

    public OrderForm(OrderType orderType) {
        this.orderType = orderType;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderForm orderForm = (OrderForm) o;

        return orderType == orderForm.orderType;
    }

    @Override
    public int hashCode() {
        return orderType != null ? orderType.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "OrderForm{" +
                "orderType=" + orderType +
                '}';
    }
}
