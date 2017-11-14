package com.kushnir.paperki.model.user;

public class Address {

    private Integer ownerId;
    private Integer type;
    private String index;
    private String city;
    private String Street;
    private String house;
    private String housePart;
    private String houseOffice;

    private Integer userId;

    private String value;
    private String description;

    private Integer id;

    public Address() {
    }

    public Address(Integer ownerId,
                   Integer type,
                   String index,
                   String city,
                   String street,
                   String house,
                   String housePart,
                   String houseOffice,
                   String description) {
        this.ownerId = ownerId;
        this.type = type;
        this.index = index;
        this.city = city;
        this.Street = street;
        this.house = house;
        this.housePart = housePart;
        this.houseOffice = houseOffice;
        this.description = description;
    }

    public Address(Integer userId,
                   Integer ownerId,
                   Integer type,
                   String index,
                   String city,
                   String street,
                   String house,
                   String housePart,
                   String houseOffice,
                   String description) {
        this.userId = userId;
        this.ownerId = ownerId;
        this.type = type;
        this.index = index;
        this.city = city;
        this.Street = street;
        this.house = house;
        this.housePart = housePart;
        this.houseOffice = houseOffice;
        this.description = description;
    }

    public Address(Integer ownerId,
                   Integer type,
                   String index,
                   String city,
                   String street,
                   String house,
                   String housePart,
                   String houseOffice,
                   Integer userId,
                   String value,
                   String description,
                   Integer id) {
        this.ownerId = ownerId;
        this.type = type;
        this.index = index;
        this.city = city;
        this.Street = street;
        this.house = house;
        this.housePart = housePart;
        this.houseOffice = houseOffice;
        this.userId = userId;
        this.value = value;
        this.description = description;
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getHousePart() {
        return housePart;
    }

    public void setHousePart(String housePart) {
        this.housePart = housePart;
    }

    public String getHouseOffice() {
        return houseOffice;
    }

    public void setHouseOffice(String houseOffice) {
        this.houseOffice = houseOffice;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (ownerId != null ? !ownerId.equals(address.ownerId) : address.ownerId != null) return false;
        if (type != null ? !type.equals(address.type) : address.type != null) return false;
        if (index != null ? !index.equals(address.index) : address.index != null) return false;
        if (city != null ? !city.equals(address.city) : address.city != null) return false;
        if (Street != null ? !Street.equals(address.Street) : address.Street != null) return false;
        if (house != null ? !house.equals(address.house) : address.house != null) return false;
        if (housePart != null ? !housePart.equals(address.housePart) : address.housePart != null) return false;
        if (houseOffice != null ? !houseOffice.equals(address.houseOffice) : address.houseOffice != null) return false;
        if (userId != null ? !userId.equals(address.userId) : address.userId != null) return false;
        if (value != null ? !value.equals(address.value) : address.value != null) return false;
        if (description != null ? !description.equals(address.description) : address.description != null) return false;
        return id != null ? id.equals(address.id) : address.id == null;
    }

    @Override
    public int hashCode() {
        int result = ownerId != null ? ownerId.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (index != null ? index.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (Street != null ? Street.hashCode() : 0);
        result = 31 * result + (house != null ? house.hashCode() : 0);
        result = 31 * result + (housePart != null ? housePart.hashCode() : 0);
        result = 31 * result + (houseOffice != null ? houseOffice.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Address{" +
                "ownerId=" + ownerId +
                ", type=" + type +
                ", index='" + index + '\'' +
                ", city='" + city + '\'' +
                ", Street='" + Street + '\'' +
                ", house='" + house + '\'' +
                ", housePart='" + housePart + '\'' +
                ", houseOffice='" + houseOffice + '\'' +
                ", userId=" + userId +
                ", value='" + value + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                '}';
    }
}
