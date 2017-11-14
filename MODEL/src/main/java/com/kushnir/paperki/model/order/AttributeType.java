package com.kushnir.paperki.model.order;

public enum AttributeType {

    // ===== type 1 (отображаемые для пользователя)
    CONTACT_NAME("Контактное лицо",1),
    CONTACT_PHONE("Контактный номер телефона", 1),
    EMAIL("Адрес электронной почты", 1),
    UNP("УНП", 1),
    ENTERPRISE_NAME("Название организации", 1),
    PAYMENT_NAME("Способ оплаты", 1),
    PAYMENT_SHORT_DESCRIPTION("Описание способа оплаты", 1),
    PAYMENT_ACCOUNT("р/с", 1),
    SHIPMENT_NAME("Способ доставки", 1),
    COMMENTS("Комментарий к заказу", 1),

    // ===== type 2 (используемые только системой)
    ORDER_TYPE_ID("ORDER_TYPE_ID", 2),
    PAYMENT_ID("PAYMENT_ID", 2),
    PAYMENT_COST("PAYMENT_COST", 2),
    SHIPMENT_ID("SHIPMENT_ID", 2),
    SHIPMENT_ADDRESS("SHIPMENT_ADDRESS", 2),
    SHIPMENT_COST("SHIPMENT_COST", 2),
    USER_ID("USER_ID", 2);


    private String description;
    private int type;

    AttributeType(String description, int type) {
        this.description = description;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
