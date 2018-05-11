package com.kushnir.paperki.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Objects;

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
    private Boolean isPublished = false;
    private Boolean isVisible;
    private Integer order;

    /* table: categories_description */
    private String shortDescription;
    private String fullDescription;

    private Integer parent;
    private String parentName;
    private String parentLink;
    private Integer parentPapId;
    private HashMap<Integer, Category> children;

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
                    Integer order,
                    Integer parent,
                    String parentName,
                    String parentLink,
                    String metadesk,
                    String metakey,
                    String customtitle) {
        this.id = id;
        this.name = name;
        this.translitName = translitName;
        this.link = link;
        this.icon = icon;
        this.order = order;
        this.parent = parent;
        this.parentName = parentName;
        this.parentLink = parentLink;
        this.metadesk = metadesk;
        this.metakey = metakey;
        this.customtitle = customtitle;
    }

    public Category(Integer id,
                    String name,
                    String translitName,
                    String link,
                    String icon,
                    Integer order,
                    Integer parent) {
        this.id = id;
        this.name = name;
        this.translitName = translitName;
        this.link = link;
        this.icon = icon;
        this.order = order;
        this.parent = parent;
    }

    public Category(Integer id,
                    String name,
                    String translitName,
                    String link,
                    String icon,
                    Integer order,
                    HashMap<Integer, Category> children) {
        this.id = id;
        this.name = name;
        this.translitName = translitName;
        this.link = link;
        this.icon = icon;
        this.order = order;
        this.children = children;
    }

    public Category(Integer id,
                    Integer papId,
                    String name,
                    String translitName,
                    String link,
                    String icon,
                    String metadesk,
                    String metakey,
                    String customtitle,
                    Boolean isPublished,
                    Boolean isVisible,
                    Integer order,
                    Integer parent) {
        this.id = id;
        this.papId = papId;
        this.name = name;
        this.translitName = translitName;
        this.link = link;
        this.icon = icon;
        this.metadesk = metadesk;
        this.metakey = metakey;
        this.customtitle = customtitle;
        this.isPublished = isPublished;
        this.isVisible = isVisible;
        this.order = order;
        this.parent = parent;
    }

    public Category(Integer id,
                    Integer papId,
                    String name,
                    String translitName,
                    String link,
                    Boolean isPublished,
                    Boolean isVisible,
                    Integer order,
                    Integer parent) {
        this.id = id;
        this.papId = papId;
        this.name = name;
        this.translitName = translitName;
        this.link = link;
        this.isPublished = isPublished;
        this.isVisible = isVisible;
        this.order = order;
        this.parent = parent;
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

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentLink() {
        return parentLink;
    }

    public void setParentLink(String parentLink) {
        this.parentLink = parentLink;
    }

    public Integer getParentPapId() {
        return parentPapId;
    }

    public void setParentPapId(Integer parentPapId) {
        this.parentPapId = parentPapId;
    }

    public HashMap<Integer, Category> getChildren() {
        return children;
    }

    public void setChildren(HashMap<Integer, Category> children) {
        this.children = children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) &&
                Objects.equals(papId, category.papId) &&
                Objects.equals(name, category.name) &&
                Objects.equals(translitName, category.translitName) &&
                Objects.equals(link, category.link) &&
                Objects.equals(icon, category.icon) &&
                Objects.equals(metadesk, category.metadesk) &&
                Objects.equals(metakey, category.metakey) &&
                Objects.equals(customtitle, category.customtitle) &&
                Objects.equals(createDate, category.createDate) &&
                Objects.equals(editDate, category.editDate) &&
                Objects.equals(isPublished, category.isPublished) &&
                Objects.equals(isVisible, category.isVisible) &&
                Objects.equals(order, category.order) &&
                Objects.equals(shortDescription, category.shortDescription) &&
                Objects.equals(fullDescription, category.fullDescription) &&
                Objects.equals(parent, category.parent) &&
                Objects.equals(parentName, category.parentName) &&
                Objects.equals(parentLink, category.parentLink) &&
                Objects.equals(parentPapId, category.parentPapId) &&
                Objects.equals(children, category.children);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, papId, name, translitName, link, icon, metadesk, metakey, customtitle, createDate, editDate, isPublished, isVisible, order, shortDescription, fullDescription, parent, parentName, parentLink, parentPapId, children);
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
                ", parentName='" + parentName + '\'' +
                ", parentLink='" + parentLink + '\'' +
                ", parentPapId=" + parentPapId +
                ", children=" + children +
                '}';
    }
}
