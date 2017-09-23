package com.kushnir.paperki.model.product;

import com.kushnir.paperki.model.Discount;
import com.kushnir.paperki.model.Price;
import com.kushnir.paperki.model.Stock;

import java.util.HashMap;

public class AvailableProduct {
    private int pnt;
    private String fullName;
    private String shortName;
    private String link;
    private int VAT;
    private int quantityAvailable;
    private HashMap<Integer, Stock> stocks;
    private Discount discount;
    private HashMap<Integer, Price> prices = new HashMap<>();

    public AvailableProduct() {
    }

    public AvailableProduct(int pnt,
                            int VAT,
                            int quantityAvailable,
                            Discount discount) {
        this.pnt = pnt;
        this.VAT = VAT;
        this.quantityAvailable = quantityAvailable;
        this.discount = discount;
    }

    public AvailableProduct(int pnt,
                            int VAT,
                            int quantityAvailable,
                            Discount discount,
                            HashMap<Integer, Price> prices) {
        this.pnt = pnt;
        this.VAT = VAT;
        this.quantityAvailable = quantityAvailable;
        this.discount = discount;
        this.prices = prices;
    }

    public AvailableProduct(int pnt,
                            String fullName,
                            String shortName,
                            int VAT,
                            int quantityAvailable,
                            Discount discount) {
        this.pnt = pnt;
        this.fullName = fullName;
        this.shortName = shortName;
        this.VAT = VAT;
        this.quantityAvailable = quantityAvailable;
        this.discount = discount;
    }

    public AvailableProduct(int pnt,
                            String fullName,
                            String shortName,
                            int VAT,
                            int quantityAvailable,
                            Discount discount,
                            HashMap<Integer, Price> prices) {
        this.pnt = pnt;
        this.fullName = fullName;
        this.shortName = shortName;
        this.VAT = VAT;
        this.quantityAvailable = quantityAvailable;
        this.discount = discount;
        this.prices = prices;
    }

    public int getPnt() {
        return pnt;
    }

    public void setPnt(int pnt) {
        this.pnt = pnt;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getVAT() {
        return VAT;
    }

    public void setVAT(int VAT) {
        this.VAT = VAT;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(int quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public HashMap<Integer, Stock> getStocks() {
        return stocks;
    }

    public void setStocks(HashMap<Integer, Stock> stocks) {
        this.stocks = stocks;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public HashMap<Integer, Price> getPrices() {
        return prices;
    }

    public void setPrices(HashMap<Integer, Price> prices) {
        this.prices = prices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AvailableProduct that = (AvailableProduct) o;

        if (pnt != that.pnt) return false;
        if (VAT != that.VAT) return false;
        if (quantityAvailable != that.quantityAvailable) return false;
        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null) return false;
        if (shortName != null ? !shortName.equals(that.shortName) : that.shortName != null) return false;
        if (link != null ? !link.equals(that.link) : that.link != null) return false;
        if (stocks != null ? !stocks.equals(that.stocks) : that.stocks != null) return false;
        if (discount != null ? !discount.equals(that.discount) : that.discount != null) return false;
        return prices != null ? prices.equals(that.prices) : that.prices == null;
    }

    @Override
    public int hashCode() {
        int result = pnt;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + VAT;
        result = 31 * result + quantityAvailable;
        result = 31 * result + (stocks != null ? stocks.hashCode() : 0);
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        result = 31 * result + (prices != null ? prices.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AvailableProduct{" +
                "pnt=" + pnt +
                ", fullName='" + fullName + '\'' +
                ", shortName='" + shortName + '\'' +
                ", link='" + link + '\'' +
                ", VAT=" + VAT +
                ", quantityAvailable=" + quantityAvailable +
                ", stocks=" + stocks +
                ", discount=" + discount +
                ", prices=" + prices +
                '}';
    }
}
