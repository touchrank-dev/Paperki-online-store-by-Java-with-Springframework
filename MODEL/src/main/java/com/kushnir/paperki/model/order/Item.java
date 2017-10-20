package com.kushnir.paperki.model.order;

public class Item {
    private int id_order;
    private int id_product;
    private int pnt;
    private String product_full_name;
    private int VAT;
    private double base_price;
    private double base_price_with_vat;
    private double discounted_price;
    private double discounted_price_with_vat;
    private int quantity;
    private double total;
    private double total_with_vat;

    public Item() {
    }

    public Item(int id_order,
                int id_product,
                int pnt,
                String product_full_name,
                int VAT,
                double base_price,
                double base_price_with_vat,
                double discounted_price,
                double discounted_price_with_vat,
                int quantity,
                double total,
                double total_with_vat) {
        this.id_order = id_order;
        this.id_product = id_product;
        this.pnt = pnt;
        this.product_full_name = product_full_name;
        this.VAT = VAT;
        this.base_price = base_price;
        this.base_price_with_vat = base_price_with_vat;
        this.discounted_price = discounted_price;
        this.discounted_price_with_vat = discounted_price_with_vat;
        this.quantity = quantity;
        this.total = total;
        this.total_with_vat = total_with_vat;
    }

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public int getPnt() {
        return pnt;
    }

    public void setPnt(int pnt) {
        this.pnt = pnt;
    }

    public String getProduct_full_name() {
        return product_full_name;
    }

    public void setProduct_full_name(String product_full_name) {
        this.product_full_name = product_full_name;
    }

    public int getVAT() {
        return VAT;
    }

    public void setVAT(int VAT) {
        this.VAT = VAT;
    }

    public double getBase_price() {
        return base_price;
    }

    public void setBase_price(double base_price) {
        this.base_price = base_price;
    }

    public double getBase_price_with_vat() {
        return base_price_with_vat;
    }

    public void setBase_price_with_vat(double base_price_with_vat) {
        this.base_price_with_vat = base_price_with_vat;
    }

    public double getDiscounted_price() {
        return discounted_price;
    }

    public void setDiscounted_price(double discounted_price) {
        this.discounted_price = discounted_price;
    }

    public double getDiscounted_price_with_vat() {
        return discounted_price_with_vat;
    }

    public void setDiscounted_price_with_vat(double discounted_price_with_vat) {
        this.discounted_price_with_vat = discounted_price_with_vat;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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
}
