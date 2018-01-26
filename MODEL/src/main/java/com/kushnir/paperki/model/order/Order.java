package com.kushnir.paperki.model.order;

import com.kushnir.paperki.model.delivery.Delivery;
import com.kushnir.paperki.model.payment.Payment;
import com.kushnir.paperki.model.product.CartProduct;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Order {

    private int id;
    private int id_order_type;
    private String order_type_name;
    private String token_order;
    private String order_number;
    private String pap_order_number;
    private int id_user;
    private int id_order_status;
    private String orderStatusName;
    private double total;
    private double total_with_vat;
    private double vat_total;
    private double total_discount;
    private int coupon_id;
    private Payment payment;
    private double paymentCost;
    private Delivery delivery;
    private double deliveryCost;
    private double final_total;
    private double final_total_with_vat;
    private LocalDateTime create_date;
    private LocalDateTime edit_date;
    private String comments;

    private List<Attribute> attributes;
    private List<CartProduct> items;

    public String getStrCreateDate() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(this.create_date);
    }

    public Order() {
    }

    public Order(Integer id,
                 Integer id_order_status,
                 Integer id_order_type,
                 String token_order,
                 String order_number,
                 String pap_order_number,
                 Integer id_user,
                 double total,
                 double total_with_vat,
                 double vat_total,
                 double total_discount,
                 Integer coupon_id,
                 double paymentCost,
                 double deliveryCost,
                 double final_total,
                 double final_total_with_vat
                 /*LocalDateTime create_date,
                 LocalDateTime edit_date*/
                 ) {
        this.id = id;
        this.id_order_status = id_order_status;
        this.id_order_type = id_order_type;
        this.token_order = token_order;
        this.order_number = order_number;
        this.pap_order_number = pap_order_number;
        this.id_user = id_user;
        this.total = total;
        this.total_with_vat = total_with_vat;
        this.vat_total = vat_total;
        this.total_discount = total_discount;
        this.coupon_id = coupon_id;
        this.paymentCost = paymentCost;
        this.deliveryCost = deliveryCost;
        this.final_total = final_total;
        this.final_total_with_vat = final_total_with_vat;
        /*this.create_date = create_date;
        this.edit_date = edit_date;*/
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_order_type() {
        return id_order_type;
    }

    public void setId_order_type(int id_order_type) {
        this.id_order_type = id_order_type;
    }

    public String getOrder_type_name() {
        return order_type_name;
    }

    public void setOrder_type_name(String order_type_name) {
        this.order_type_name = order_type_name;
    }

    public String getToken_order() {
        return token_order;
    }

    public void setToken_order(String token_order) {
        this.token_order = token_order;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getPap_order_number() {
        return pap_order_number;
    }

    public void setPap_order_number(String pap_order_number) {
        this.pap_order_number = pap_order_number;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_order_status() {
        return id_order_status;
    }

    public void setId_order_status(int id_order_status) {
        this.id_order_status = id_order_status;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotal_with_vat() {
        return total_with_vat;
    }

    public void setTotal_with_vat(double total_with_vat) {
        this.total_with_vat = total_with_vat;
    }

    public double getVat_total() {
        return vat_total;
    }

    public void setVat_total(double vat_total) {
        this.vat_total = vat_total;
    }

    public double getTotal_discount() {
        return total_discount;
    }

    public void setTotal_discount(double total_discount) {
        this.total_discount = total_discount;
    }

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public double getPaymentCost() {
        return paymentCost;
    }

    public void setPaymentCost(double paymentCost) {
        this.paymentCost = paymentCost;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public double getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(double deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public double getFinal_total() {
        return final_total;
    }

    public void setFinal_total(double final_total) {
        this.final_total = final_total;
    }

    public double getFinal_total_with_vat() {
        return final_total_with_vat;
    }

    public void setFinal_total_with_vat(double final_total_with_vat) {
        this.final_total_with_vat = final_total_with_vat;
    }

    public LocalDateTime getCreate_date() {
        return create_date;
    }

    public void setCreate_date(LocalDateTime create_date) {
        this.create_date = create_date;
    }

    public LocalDateTime getEdit_date() {
        return edit_date;
    }

    public void setEdit_date(LocalDateTime edit_date) {
        this.edit_date = edit_date;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public List<CartProduct> getItems() {
        return items;
    }

    public void setItems(List<CartProduct> items) {
        this.items = items;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (id != order.id) return false;
        if (id_order_type != order.id_order_type) return false;
        if (id_user != order.id_user) return false;
        if (id_order_status != order.id_order_status) return false;
        if (Double.compare(order.total, total) != 0) return false;
        if (Double.compare(order.total_with_vat, total_with_vat) != 0) return false;
        if (Double.compare(order.vat_total, vat_total) != 0) return false;
        if (Double.compare(order.total_discount, total_discount) != 0) return false;
        if (coupon_id != order.coupon_id) return false;
        if (Double.compare(order.paymentCost, paymentCost) != 0) return false;
        if (Double.compare(order.deliveryCost, deliveryCost) != 0) return false;
        if (Double.compare(order.final_total, final_total) != 0) return false;
        if (Double.compare(order.final_total_with_vat, final_total_with_vat) != 0) return false;
        if (order_type_name != null ? !order_type_name.equals(order.order_type_name) : order.order_type_name != null)
            return false;
        if (token_order != null ? !token_order.equals(order.token_order) : order.token_order != null) return false;
        if (order_number != null ? !order_number.equals(order.order_number) : order.order_number != null) return false;
        if (pap_order_number != null ? !pap_order_number.equals(order.pap_order_number) : order.pap_order_number != null)
            return false;
        if (orderStatusName != null ? !orderStatusName.equals(order.orderStatusName) : order.orderStatusName != null)
            return false;
        if (payment != null ? !payment.equals(order.payment) : order.payment != null) return false;
        if (delivery != null ? !delivery.equals(order.delivery) : order.delivery != null) return false;
        if (create_date != null ? !create_date.equals(order.create_date) : order.create_date != null) return false;
        if (edit_date != null ? !edit_date.equals(order.edit_date) : order.edit_date != null) return false;
        if (comments != null ? !comments.equals(order.comments) : order.comments != null) return false;
        if (attributes != null ? !attributes.equals(order.attributes) : order.attributes != null) return false;
        return items != null ? items.equals(order.items) : order.items == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + id_order_type;
        result = 31 * result + (order_type_name != null ? order_type_name.hashCode() : 0);
        result = 31 * result + (token_order != null ? token_order.hashCode() : 0);
        result = 31 * result + (order_number != null ? order_number.hashCode() : 0);
        result = 31 * result + (pap_order_number != null ? pap_order_number.hashCode() : 0);
        result = 31 * result + id_user;
        result = 31 * result + id_order_status;
        result = 31 * result + (orderStatusName != null ? orderStatusName.hashCode() : 0);
        temp = Double.doubleToLongBits(total);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(total_with_vat);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(vat_total);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(total_discount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + coupon_id;
        result = 31 * result + (payment != null ? payment.hashCode() : 0);
        temp = Double.doubleToLongBits(paymentCost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (delivery != null ? delivery.hashCode() : 0);
        temp = Double.doubleToLongBits(deliveryCost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(final_total);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(final_total_with_vat);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (create_date != null ? create_date.hashCode() : 0);
        result = 31 * result + (edit_date != null ? edit_date.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (attributes != null ? attributes.hashCode() : 0);
        result = 31 * result + (items != null ? items.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", id_order_type=" + id_order_type +
                ", order_type_name='" + order_type_name + '\'' +
                ", token_order='" + token_order + '\'' +
                ", order_number='" + order_number + '\'' +
                ", pap_order_number='" + pap_order_number + '\'' +
                ", id_user=" + id_user +
                ", id_order_status=" + id_order_status +
                ", orderStatusName='" + orderStatusName + '\'' +
                ", total=" + total +
                ", total_with_vat=" + total_with_vat +
                ", vat_total=" + vat_total +
                ", total_discount=" + total_discount +
                ", coupon_id=" + coupon_id +
                ", payment=" + payment +
                ", paymentCost=" + paymentCost +
                ", delivery=" + delivery +
                ", deliveryCost=" + deliveryCost +
                ", final_total=" + final_total +
                ", final_total_with_vat=" + final_total_with_vat +
                ", create_date=" + create_date +
                ", edit_date=" + edit_date +
                ", comments='" + comments + '\'' +
                ", attributes=" + attributes +
                ", items=" + items +
                '}';
    }
}
