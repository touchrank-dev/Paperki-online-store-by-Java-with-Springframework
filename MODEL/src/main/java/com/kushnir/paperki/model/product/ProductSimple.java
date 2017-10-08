package com.kushnir.paperki.model.product;

import com.kushnir.paperki.model.category.CategorySimple;

public class ProductSimple {

    private Integer id;
    private Integer papId;
    private Integer pnt;
    private String translitName;

    private CategorySimple category;

    public ProductSimple() {
    }

    public ProductSimple(Integer id, Integer papId, Integer pnt, String translitName, CategorySimple category) {
        this.id = id;
        this.papId = papId;
        this.pnt = pnt;
        this.translitName = translitName;
        this.category = category;
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

    public Integer getPnt() {
        return pnt;
    }

    public void setPnt(Integer pnt) {
        this.pnt = pnt;
    }

    public String getTranslitName() {
        return translitName;
    }

    public void setTranslitName(String translitName) {
        this.translitName = translitName;
    }

    public CategorySimple getCategory() {
        return category;
    }

    public void setCategory(CategorySimple category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductSimple that = (ProductSimple) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (papId != null ? !papId.equals(that.papId) : that.papId != null) return false;
        if (pnt != null ? !pnt.equals(that.pnt) : that.pnt != null) return false;
        if (translitName != null ? !translitName.equals(that.translitName) : that.translitName != null) return false;
        return category != null ? category.equals(that.category) : that.category == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (papId != null ? papId.hashCode() : 0);
        result = 31 * result + (pnt != null ? pnt.hashCode() : 0);
        result = 31 * result + (translitName != null ? translitName.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ProductSimple{" +
                "id=" + id +
                ", papId=" + papId +
                ", pnt=" + pnt +
                ", translitName='" + translitName + '\'' +
                ", category=" + category +
                '}';
    }
}
