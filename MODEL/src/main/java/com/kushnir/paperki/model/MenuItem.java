package com.kushnir.paperki.model;

import java.time.LocalDateTime;

public class MenuItem {
    private Integer id;
    private Integer menu;
    private String name;
    private String translitName;
    private String link;
    private LocalDateTime createDate;
    private LocalDateTime editDate;
    private Boolean isPublished;

    public MenuItem() {
    }

    public MenuItem(Integer id,
                    Integer menu,
                    String name,
                    String translitName,
                    String link,
                    LocalDateTime createDate,
                    LocalDateTime editDate,
                    Boolean isPublished) {
        this.id = id;
        this.menu = menu;
        this.name = name;
        this.translitName = translitName;
        this.link = link;
        this.createDate = createDate;
        this.editDate = editDate;
        this.isPublished = isPublished;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMenu() {
        return menu;
    }

    public void setMenu(Integer menu) {
        this.menu = menu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTranslitName() {
        return translitName;
    }

    public void setTranslitName(String translitName) {
        this.translitName = translitName;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getEditDate() {
        return editDate;
    }

    public void setEditDate(LocalDateTime editDate) {
        this.editDate = editDate;
    }

    public Boolean getPublished() {
        return isPublished;
    }

    public void setPublished(Boolean published) {
        isPublished = published;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuItem menuItem = (MenuItem) o;

        if (id != null ? !id.equals(menuItem.id) : menuItem.id != null) return false;
        if (menu != null ? !menu.equals(menuItem.menu) : menuItem.menu != null) return false;
        if (name != null ? !name.equals(menuItem.name) : menuItem.name != null) return false;
        if (translitName != null ? !translitName.equals(menuItem.translitName) : menuItem.translitName != null)
            return false;
        if (link != null ? !link.equals(menuItem.link) : menuItem.link != null) return false;
        if (createDate != null ? !createDate.equals(menuItem.createDate) : menuItem.createDate != null) return false;
        if (editDate != null ? !editDate.equals(menuItem.editDate) : menuItem.editDate != null) return false;
        return isPublished != null ? isPublished.equals(menuItem.isPublished) : menuItem.isPublished == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (menu != null ? menu.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (translitName != null ? translitName.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (editDate != null ? editDate.hashCode() : 0);
        result = 31 * result + (isPublished != null ? isPublished.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "id=" + id +
                ", menu=" + menu +
                ", name='" + name + '\'' +
                ", translitName='" + translitName + '\'' +
                ", link='" + link + '\'' +
                ", createDate=" + createDate +
                ", editDate=" + editDate +
                ", isPublished=" + isPublished +
                '}';
    }
}
