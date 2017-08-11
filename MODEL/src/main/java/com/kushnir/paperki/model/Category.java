package com.kushnir.paperki.model;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Category {

    /* table: categories */
    private Integer id;
    private Integer papId;
    private String name;
    private String translitName;
    private String link;
    private String icon;
    private String metadesk;
    private String metakey;
    private String customtitle;
    private LocalDateTime createDate;
    private LocalDateTime editDate;
    private Boolean isPublished;
    private Boolean isVisible;
    private Integer order;

    /* table: categories_description */
    private String shortDescription;
    private String fullDescription;

    private Integer parent;
    private HashMap<Integer, Category> parents;
    private HashMap<Integer, Category> childs = new HashMap<Integer, Category>();

    public Category() {
    }

    public Category(Integer papId,
                    String name,
                    String metadesk,
                    String metakey,
                    String customtitle,
                    Integer order,
                    Integer parent,
                    String shortDescription,
                    String fullDescription) {
        this.papId = papId;
        this.name = name;
        this.metadesk = metadesk;
        this.metakey = metakey;
        this.customtitle = customtitle;
        this.order = order;
        this.parent = parent;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
    }

    public Category(Integer id,
                    String name,
                    String translitName,
                    String link,
                    String icon,
                    Integer order) {
        this.id = id;
        this.name = name;
        this.translitName = translitName;
        this.link = link;
        this.icon = icon;
        this.order = order;
    }

    public Category(Integer id,
                    String name,
                    String translitName,
                    String link,
                    String icon,
                    Integer order,
                    HashMap<Integer, Category> parents,
                    HashMap<Integer, Category> childs) {
        this.id = id;
        this.name = name;
        this.translitName = translitName;
        this.link = link;
        this.icon = icon;
        this.order = order;
        this.parents = parents;
        this.childs = childs;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getMetadesk() {
        return metadesk;
    }

    public void setMetadesk(String metadesk) {
        this.metadesk = metadesk;
    }

    public String getMetakey() {
        return metakey;
    }

    public void setMetakey(String metakey) {
        this.metakey = metakey;
    }

    public String getCustomtitle() {
        return customtitle;
    }

    public void setCustomtitle(String customtitle) {
        this.customtitle = customtitle;
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

    public Boolean getVisible() {
        return isVisible;
    }

    public void setVisible(Boolean visible) {
        isVisible = visible;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
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

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public HashMap<Integer, Category> getParents() {
        return parents;
    }

    public void setParents(HashMap<Integer, Category> parents) {
        this.parents = parents;
    }

    public HashMap<Integer, Category> getChilds() {
        return childs;
    }

    public void setChilds(HashMap<Integer, Category> childs) {
        this.childs = childs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        if (id != null ? !id.equals(category.id) : category.id != null) return false;
        if (papId != null ? !papId.equals(category.papId) : category.papId != null) return false;
        if (name != null ? !name.equals(category.name) : category.name != null) return false;
        if (translitName != null ? !translitName.equals(category.translitName) : category.translitName != null)
            return false;
        if (link != null ? !link.equals(category.link) : category.link != null) return false;
        if (icon != null ? !icon.equals(category.icon) : category.icon != null) return false;
        if (metadesk != null ? !metadesk.equals(category.metadesk) : category.metadesk != null) return false;
        if (metakey != null ? !metakey.equals(category.metakey) : category.metakey != null) return false;
        if (customtitle != null ? !customtitle.equals(category.customtitle) : category.customtitle != null)
            return false;
        if (createDate != null ? !createDate.equals(category.createDate) : category.createDate != null) return false;
        if (editDate != null ? !editDate.equals(category.editDate) : category.editDate != null) return false;
        if (isPublished != null ? !isPublished.equals(category.isPublished) : category.isPublished != null)
            return false;
        if (isVisible != null ? !isVisible.equals(category.isVisible) : category.isVisible != null) return false;
        if (order != null ? !order.equals(category.order) : category.order != null) return false;
        if (shortDescription != null ? !shortDescription.equals(category.shortDescription) : category.shortDescription != null)
            return false;
        if (fullDescription != null ? !fullDescription.equals(category.fullDescription) : category.fullDescription != null)
            return false;
        if (parent != null ? !parent.equals(category.parent) : category.parent != null) return false;
        if (parents != null ? !parents.equals(category.parents) : category.parents != null) return false;
        return childs != null ? childs.equals(category.childs) : category.childs == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (papId != null ? papId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (translitName != null ? translitName.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        result = 31 * result + (metadesk != null ? metadesk.hashCode() : 0);
        result = 31 * result + (metakey != null ? metakey.hashCode() : 0);
        result = 31 * result + (customtitle != null ? customtitle.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (editDate != null ? editDate.hashCode() : 0);
        result = 31 * result + (isPublished != null ? isPublished.hashCode() : 0);
        result = 31 * result + (isVisible != null ? isVisible.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        result = 31 * result + (shortDescription != null ? shortDescription.hashCode() : 0);
        result = 31 * result + (fullDescription != null ? fullDescription.hashCode() : 0);
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        result = 31 * result + (parents != null ? parents.hashCode() : 0);
        result = 31 * result + (childs != null ? childs.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", papId=" + papId +
                ", name='" + name + '\'' +
                ", translitName='" + translitName + '\'' +
                ", link='" + link + '\'' +
                ", icon='" + icon + '\'' +
                ", metadesk='" + metadesk + '\'' +
                ", metakey='" + metakey + '\'' +
                ", customtitle='" + customtitle + '\'' +
                ", createDate=" + createDate +
                ", editDate=" + editDate +
                ", isPublished=" + isPublished +
                ", isVisible=" + isVisible +
                ", order=" + order +
                ", shortDescription='" + shortDescription + '\'' +
                ", fullDescription='" + fullDescription + '\'' +
                ", parent=" + parent +
                ", parents=" + parents +
                ", childs=" + childs +
                '}';
    }
}
