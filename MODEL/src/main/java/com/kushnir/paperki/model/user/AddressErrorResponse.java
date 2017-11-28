package com.kushnir.paperki.model.user;

public class AddressErrorResponse {
    private String index;
    private String city;
    private String Street;
    private String house;
    private String housePart;
    private String houseOffice;
    private String description;

    private Boolean isErrors = false;

    public Boolean isErrors(){return this.isErrors;}

    public AddressErrorResponse() {
    }

    public AddressErrorResponse(String index, String city, String street, String house, String housePart, String houseOffice) {
        this.index = index;
        this.city = city;
        Street = street;
        this.house = house;
        this.housePart = housePart;
        this.houseOffice = houseOffice;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
        this.isErrors = true;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
        this.isErrors = true;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
        this.isErrors = true;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
        this.isErrors = true;
    }

    public String getHousePart() {
        return housePart;
    }

    public void setHousePart(String housePart) {
        this.housePart = housePart;
        this.isErrors = true;
    }

    public String getHouseOffice() {
        return houseOffice;
    }

    public void setHouseOffice(String houseOffice) {
        this.houseOffice = houseOffice;
        this.isErrors = true;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.isErrors = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddressErrorResponse that = (AddressErrorResponse) o;

        if (index != null ? !index.equals(that.index) : that.index != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (Street != null ? !Street.equals(that.Street) : that.Street != null) return false;
        if (house != null ? !house.equals(that.house) : that.house != null) return false;
        if (housePart != null ? !housePart.equals(that.housePart) : that.housePart != null) return false;
        if (houseOffice != null ? !houseOffice.equals(that.houseOffice) : that.houseOffice != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        return isErrors != null ? isErrors.equals(that.isErrors) : that.isErrors == null;
    }

    @Override
    public int hashCode() {
        int result = index != null ? index.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (Street != null ? Street.hashCode() : 0);
        result = 31 * result + (house != null ? house.hashCode() : 0);
        result = 31 * result + (housePart != null ? housePart.hashCode() : 0);
        result = 31 * result + (houseOffice != null ? houseOffice.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (isErrors != null ? isErrors.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AddressErrorResponse{" +
                "index='" + index + '\'' +
                ", city='" + city + '\'' +
                ", Street='" + Street + '\'' +
                ", house='" + house + '\'' +
                ", housePart='" + housePart + '\'' +
                ", houseOffice='" + houseOffice + '\'' +
                ", description='" + description + '\'' +
                ", isErrors=" + isErrors +
                '}';
    }
}
