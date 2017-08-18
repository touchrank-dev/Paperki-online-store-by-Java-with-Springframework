package com.kushnir.paperki.model;

public class Brand {
    private String tName;
    private String name;

    public Brand() {
    }

    public Brand(String tName, String name) {
        this.tName = tName;
        this.name = name;
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Brand brand = (Brand) o;

        if (tName != null ? !tName.equals(brand.tName) : brand.tName != null) return false;
        return name != null ? name.equals(brand.name) : brand.name == null;
    }

    @Override
    public int hashCode() {
        int result = tName != null ? tName.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "tName='" + tName + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
