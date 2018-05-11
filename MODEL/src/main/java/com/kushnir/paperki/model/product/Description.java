package com.kushnir.paperki.model.product;

public class Description {
    private Integer pnt;
    private String fullDescription;
    private String shortDescription;
    private String metadesk;
    private String metakey;
    private String customtitle;

    public Description() {
    }

    public Description(Integer pnt, String fullDescription, String shortDescription) {
        this.pnt = pnt;
        this.fullDescription = fullDescription;
        this.shortDescription = shortDescription;
    }

    public Integer getPnt() {
        return pnt;
    }

    public void setPnt(Integer pnt) {
        this.pnt = pnt;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Description that = (Description) o;

        if (pnt != null ? !pnt.equals(that.pnt) : that.pnt != null) return false;
        if (fullDescription != null ? !fullDescription.equals(that.fullDescription) : that.fullDescription != null)
            return false;
        if (shortDescription != null ? !shortDescription.equals(that.shortDescription) : that.shortDescription != null)
            return false;
        if (metadesk != null ? !metadesk.equals(that.metadesk) : that.metadesk != null) return false;
        if (metakey != null ? !metakey.equals(that.metakey) : that.metakey != null) return false;
        return customtitle != null ? customtitle.equals(that.customtitle) : that.customtitle == null;
    }

    @Override
    public int hashCode() {
        int result = pnt != null ? pnt.hashCode() : 0;
        result = 31 * result + (fullDescription != null ? fullDescription.hashCode() : 0);
        result = 31 * result + (shortDescription != null ? shortDescription.hashCode() : 0);
        result = 31 * result + (metadesk != null ? metadesk.hashCode() : 0);
        result = 31 * result + (metakey != null ? metakey.hashCode() : 0);
        result = 31 * result + (customtitle != null ? customtitle.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Description{" +
                "pnt=" + pnt +
                ", fullDescription='" + fullDescription + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", metadesk='" + metadesk + '\'' +
                ", metakey='" + metakey + '\'' +
                ", customtitle='" + customtitle + '\'' +
                '}';
    }
}
