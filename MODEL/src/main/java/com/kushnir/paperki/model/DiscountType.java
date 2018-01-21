package com.kushnir.paperki.model;

public enum DiscountType {
    OVERRIDE(1),
    PROCENT(2),
    SUBTRACT(3);

    private Integer type;

    DiscountType(Integer type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public static DiscountType getType(Integer typeId) {
        switch (typeId) {
            case 1:return OVERRIDE;
            case 2:return PROCENT;
            case 3:return SUBTRACT;
            default: return null;
        }
    }

}
