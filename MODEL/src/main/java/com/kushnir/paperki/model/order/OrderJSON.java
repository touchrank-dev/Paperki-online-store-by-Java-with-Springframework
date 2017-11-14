package com.kushnir.paperki.model.order;

import com.kushnir.paperki.model.delivery.ShipmentInfo;
import com.kushnir.paperki.model.payment.PaymentInfo;
import com.kushnir.paperki.model.product.Item;

import java.time.LocalDateTime;
import java.util.List;

public class OrderJSON {
    private int id;
    private String link;
    private String orderToken;
    private String orderNumber;
    private String papOrderNumber;
    private String orderDate;
    private int orderType;
    private PaymentInfo payment;
    private ShipmentInfo shipment;
    private Object customerInfo;
    private String comments;
    private List<Item> items;

    public OrderJSON() {
    }

    public OrderJSON(int id,
                     String link,
                     String orderToken,
                     String orderNumber,
                     String papOrderNumber,
                     String orderDate,
                     int orderType,
                     PaymentInfo payment,
                     ShipmentInfo shipment,
                     Object customerInfo,
                     String comments,
                     List<Item> items) {
        this.id = id;
        this.link = link;
        this.orderToken = orderToken;
        this.orderNumber = orderNumber;
        this.papOrderNumber = papOrderNumber;
        this.orderDate = orderDate;
        this.orderType = orderType;
        this.payment = payment;
        this.shipment = shipment;
        this.customerInfo = customerInfo;
        this.comments = comments;
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getOrderToken() {
        return orderToken;
    }

    public void setOrderToken(String orderToken) {
        this.orderToken = orderToken;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getPapOrderNumber() {
        return papOrderNumber;
    }

    public void setPapOrderNumber(String papOrderNumber) {
        this.papOrderNumber = papOrderNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public PaymentInfo getPayment() {
        return payment;
    }

    public void setPayment(PaymentInfo payment) {
        this.payment = payment;
    }

    public ShipmentInfo getShipment() {
        return shipment;
    }

    public void setShipment(ShipmentInfo shipment) {
        this.shipment = shipment;
    }

    public Object getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(Object customerInfo) {
        this.customerInfo = customerInfo;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderJSON orderJSON = (OrderJSON) o;

        if (id != orderJSON.id) return false;
        if (orderType != orderJSON.orderType) return false;
        if (link != null ? !link.equals(orderJSON.link) : orderJSON.link != null) return false;
        if (orderToken != null ? !orderToken.equals(orderJSON.orderToken) : orderJSON.orderToken != null) return false;
        if (orderNumber != null ? !orderNumber.equals(orderJSON.orderNumber) : orderJSON.orderNumber != null)
            return false;
        if (papOrderNumber != null ? !papOrderNumber.equals(orderJSON.papOrderNumber) : orderJSON.papOrderNumber != null)
            return false;
        if (orderDate != null ? !orderDate.equals(orderJSON.orderDate) : orderJSON.orderDate != null) return false;
        if (payment != null ? !payment.equals(orderJSON.payment) : orderJSON.payment != null) return false;
        if (shipment != null ? !shipment.equals(orderJSON.shipment) : orderJSON.shipment != null) return false;
        if (customerInfo != null ? !customerInfo.equals(orderJSON.customerInfo) : orderJSON.customerInfo != null)
            return false;
        if (comments != null ? !comments.equals(orderJSON.comments) : orderJSON.comments != null) return false;
        return items != null ? items.equals(orderJSON.items) : orderJSON.items == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (orderToken != null ? orderToken.hashCode() : 0);
        result = 31 * result + (orderNumber != null ? orderNumber.hashCode() : 0);
        result = 31 * result + (papOrderNumber != null ? papOrderNumber.hashCode() : 0);
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + orderType;
        result = 31 * result + (payment != null ? payment.hashCode() : 0);
        result = 31 * result + (shipment != null ? shipment.hashCode() : 0);
        result = 31 * result + (customerInfo != null ? customerInfo.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (items != null ? items.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OrderJSON{" +
                "id=" + id +
                ", link='" + link + '\'' +
                ", orderToken='" + orderToken + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", papOrderNumber='" + papOrderNumber + '\'' +
                ", orderDate=" + orderDate +
                ", orderType=" + orderType +
                ", payment=" + payment +
                ", shipment=" + shipment +
                ", customerInfo=" + customerInfo +
                ", comments='" + comments + '\'' +
                ", items=" + items +
                '}';
    }
}
