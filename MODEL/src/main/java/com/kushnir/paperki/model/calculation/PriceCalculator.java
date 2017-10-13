package com.kushnir.paperki.model.calculation;

import com.kushnir.paperki.model.Discount;
import com.kushnir.paperki.model.DiscountType;
import com.kushnir.paperki.model.Price;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class PriceCalculator {

    public double getDouble(double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    public double getVatValue(double price, int VAT) {
        return getDouble(price * VAT/100d);
    }

    public double getPriceWithVAT(double price, int VAT) {
        return getDouble(price + getVatValue(price, VAT));
    }

    public double calculateTotalWithVAT(double total, int quantity, int VAT) {
        double totalVAT = getVatValue(total, VAT);
        return total + totalVAT;
    }

    public double calculateDiscountedPrice(Discount discount, double basePrice) {
        if (discount != null) {
            DiscountType discountType = discount.getDiscountType();
            double dValue = discount.getValueDouble();
            int iValue = discount.getValueInt();

            if (discountType != null) {
                switch (discountType) {
                    case PROCENT:
                        double val = basePrice * getDouble(iValue / 100.0);
                        return getDouble(basePrice - val);
                    case OVERRIDE:
                        return getDouble(dValue);
                    case SUBSTRACT:
                        return getDouble(basePrice - dValue);
                    default:
                        return basePrice;
                }
            } else return basePrice;
        } else return basePrice;
    }

    public double calculateQuantityPrice (HashMap<Integer, Price> prices, int quantity, double currentPrice) {
        double finalPrice = currentPrice;
        for (Map.Entry<Integer, Price> entry : prices.entrySet()) {
            Price price = entry.getValue();

            int quantityStart = price.getQuantityStart();

            if (quantity >= quantityStart) {
                finalPrice = price.getBasePrice();
            }

        }
        return finalPrice;
    }
}
