package com.kushnir.paperki.model.order;

public class OrderErrorForm {

    private String message;

    private Boolean isErrors = false;
    public Boolean isErrors(){return this.isErrors;}

    public void setErrors(String message) {
        this.message = message;
        isErrors = true;
    }
}
