package com.kushnir.paperki.model.category;

public class CategorySimple {

    private Integer id;
    private Integer papId;
    private String translitName;

    public CategorySimple() {
    }

    public CategorySimple(Integer id, Integer papId, String translitName) {
        this.id = id;
        this.papId = papId;
        this.translitName = translitName;
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

    public String getTranslitName() {
        return translitName;
    }

    public void setTranslitName(String translitName) {
        this.translitName = translitName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategorySimple that = (CategorySimple) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (papId != null ? !papId.equals(that.papId) : that.papId != null) return false;
        return translitName != null ? translitName.equals(that.translitName) : that.translitName == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (papId != null ? papId.hashCode() : 0);
        result = 31 * result + (translitName != null ? translitName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CategorySimple{" +
                "id=" + id +
                ", papId=" + papId +
                ", translitName='" + translitName + '\'' +
                '}';
    }
}
