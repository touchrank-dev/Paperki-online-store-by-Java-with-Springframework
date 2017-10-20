package com.kushnir.paperki.model;

public class Brand {
    private Integer id;
    private Integer papId;
    private String tName;
    private String name;
    private String icon;
    private String shortDescription;
    private String fullDescription;


    public Brand() {
    }

    public Brand(String tName, String name) {
        this.tName = tName;
        this.name = name;
    }

    public Brand(Integer papId, String tName, String name) {
        this.papId = papId;
        this.tName = tName;
        this.name = name;
    }

    public Brand(Integer papId, String tName, String name, String icon) {
        this.papId = papId;
        this.tName = tName;
        this.name = name;
        this.icon = icon;
    }

    public Brand(Integer id, Integer papId, String tName, String name) {
        this.id = id;
        this.papId = papId;
        this.tName = tName;
        this.name = name;
    }

    public Brand(Integer id, Integer papId, String tName,
                 String name, String icon, String shortDescription, String fullDescription) {
        this.id = id;
        this.papId = papId;
        this.tName = tName;
        this.name = name;
        this.icon = icon;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPapId() {
        return papId;
    }

    public void setPapId(Integer papId) {
        this.papId = papId;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Brand brand = (Brand) o;

        if (id != null ? !id.equals(brand.id) : brand.id != null) return false;
        if (papId != null ? !papId.equals(brand.papId) : brand.papId != null) return false;
        if (tName != null ? !tName.equals(brand.tName) : brand.tName != null) return false;
        if (name != null ? !name.equals(brand.name) : brand.name != null) return false;
        if (icon != null ? !icon.equals(brand.icon) : brand.icon != null) return false;
        if (shortDescription != null ? !shortDescription.equals(brand.shortDescription) : brand.shortDescription != null)
            return false;
        return fullDescription != null ? fullDescription.equals(brand.fullDescription) : brand.fullDescription == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (papId != null ? papId.hashCode() : 0);
        result = 31 * result + (tName != null ? tName.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        result = 31 * result + (shortDescription != null ? shortDescription.hashCode() : 0);
        result = 31 * result + (fullDescription != null ? fullDescription.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Brand{" +
                "id=" + id +
                ", papId=" + papId +
                ", tName='" + tName + '\'' +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", fullDescription='" + fullDescription + '\'' +
                '}';
    }
}
