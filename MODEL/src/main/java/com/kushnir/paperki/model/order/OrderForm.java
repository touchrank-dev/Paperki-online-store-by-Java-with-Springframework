package com.kushnir.paperki.model.order;

import com.kushnir.paperki.model.user.User;

public class OrderForm {

    private OrderType orderType;
    private User user;

    public OrderForm() {
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderForm orderForm = (OrderForm) o;

        if (orderType != orderForm.orderType) return false;
        return user != null ? user.equals(orderForm.user) : orderForm.user == null;
    }

    @Override
    public int hashCode() {
        int result = orderType != null ? orderType.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderForm{" +
                "orderType=" + orderType +
                ", user=" + user +
                '}';
    }
}
