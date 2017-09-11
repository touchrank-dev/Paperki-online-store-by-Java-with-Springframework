package com.kushnir.paperki.model.delivery;

public class Delivery {
    private int id;
    private int idOrderType;
    private String name;
    private String shortDescription;
    private String fullDescription;
    private String link;
    private String icon;
    private double minCartTotal;
    private double price;

    public Delivery() {
    }

    public Delivery(int id,
                    int idOrderType,
                    String name,
                    String shortDescription,
                    String fullDescription,
                    String link,
                    String icon,
                    double minCartTotal,
                    double price) {
        this.id = id;
        this.idOrderType = idOrderType;
        this.name = name;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.link = link;
        this.icon = icon;
        this.minCartTotal = minCartTotal;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdOrderType() {
        return idOrderType;
    }

    public void setIdOrderType(int idOrderType) {
        this.idOrderType = idOrderType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getMinCartTotal() {
        return minCartTotal;
    }

    public void setMinCartTotal(double minCartTotal) {
        this.minCartTotal = minCartTotal;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Delivery delivery = (Delivery) o;

        if (id != delivery.id) return false;
        if (idOrderType != delivery.idOrderType) return false;
        if (Double.compare(delivery.minCartTotal, minCartTotal) != 0) return false;
        if (Double.compare(delivery.price, price) != 0) return false;
        if (name != null ? !name.equals(delivery.name) : delivery.name != null) return false;
        if (shortDescription != null ? !shortDescription.equals(delivery.shortDescription) : delivery.shortDescription != null)
            return false;
        if (fullDescription != null ? !fullDescription.equals(delivery.fullDescription) : delivery.fullDescription != null)
            return false;
        if (link != null ? !link.equals(delivery.link) : delivery.link != null) return false;
        return icon != null ? icon.equals(delivery.icon) : delivery.icon == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + idOrderType;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (shortDescription != null ? shortDescription.hashCode() : 0);
        result = 31 * result + (fullDescription != null ? fullDescription.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        temp = Double.doubleToLongBits(minCartTotal);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Delivery{" +
                "id=" + id +
                ", idOrderType=" + idOrderType +
                ", name='" + name + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", fullDescription='" + fullDescription + '\'' +
                ", link='" + link + '\'' +
                ", icon='" + icon + '\'' +
                ", minCartTotal=" + minCartTotal +
                ", price=" + price +
                '}';
    }
}
