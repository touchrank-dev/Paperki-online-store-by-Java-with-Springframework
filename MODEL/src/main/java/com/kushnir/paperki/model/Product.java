package com.kushnir.paperki.model;

import java.time.LocalDateTime;
import java.util.HashMap;

public class Product {
    private Integer id;
    private Integer papId;
    private Integer pnt;
    private String fullName;
    private String shortName;
    private String translitName;
    private String link;
    private String countryFrom;
    private String countryMade;
    private String barCode;
    private String measure;
    private Integer availableDay;
    private Integer VAT;
    private String metadesk;
    private String metakey;
    private String customtitle;
    private LocalDateTime createDate;
    private LocalDateTime editDate;
    private Boolean isPublishet;
    private Boolean isVisible;

    private Brand brand;

    private String shortDescription;
    private String fullDescriotion;

    private HashMap<Integer, String[]> attributes;

    private HashMap<Integer, Price> prices;

    public Product() {
    }

    public Product(Integer papId,
                   Integer pnt,
                   String fullName,
                   String shortName,
                   String translitName,
                   String link,
                   String countryFrom,
                   String countryMade,
                   String measure,
                   Integer availableDay,
                   Integer VAT,
                   Boolean isPublishet,
                   Boolean isVisible,
                   Brand brand,
                   HashMap<Integer, String[]> attributes,
                   HashMap<Integer, Price> prices) {
        this.papId = papId;
        this.pnt = pnt;
        this.fullName = fullName;
        this.shortName = shortName;
        this.translitName = translitName;
        this.link = link;
        this.countryFrom = countryFrom;
        this.countryMade = countryMade;
        this.measure = measure;
        this.availableDay = availableDay;
        this.VAT = VAT;
        this.isPublishet = isPublishet;
        this.isVisible = isVisible;
        this.brand = brand;
        this.attributes = attributes;
        this.prices = prices;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
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

    public String getCountryFrom() {
        return countryFrom;
    }

    public void setCountryFrom(String countryFrom) {
        this.countryFrom = countryFrom;
    }

    public String getCountryMade() {
        return countryMade;
    }

    public void setCountryMade(String countryMade) {
        this.countryMade = countryMade;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public Integer getAvailableDay() {
        return availableDay;
    }

    public void setAvailableDay(Integer availableDay) {
        this.availableDay = availableDay;
    }

    public Integer getVAT() {
        return VAT;
    }

    public void setVAT(Integer VAT) {
        this.VAT = VAT;
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

    public Boolean getPublishet() {
        return isPublishet;
    }

    public void setPublishet(Boolean publishet) {
        isPublishet = publishet;
    }

    public Boolean getVisible() {
        return isVisible;
    }

    public void setVisible(Boolean visible) {
        isVisible = visible;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescriotion() {
        return fullDescriotion;
    }

    public void setFullDescriotion(String fullDescriotion) {
        this.fullDescriotion = fullDescriotion;
    }

    public HashMap<Integer, String[]> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<Integer, String[]> attributes) {
        this.attributes = attributes;
    }

    public HashMap<Integer, Price> getPrices() {
        return prices;
    }

    public void setPrices(HashMap<Integer, Price> prices) {
        this.prices = prices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (id != null ? !id.equals(product.id) : product.id != null) return false;
        if (papId != null ? !papId.equals(product.papId) : product.papId != null) return false;
        if (pnt != null ? !pnt.equals(product.pnt) : product.pnt != null) return false;
        if (fullName != null ? !fullName.equals(product.fullName) : product.fullName != null) return false;
        if (shortName != null ? !shortName.equals(product.shortName) : product.shortName != null) return false;
        if (translitName != null ? !translitName.equals(product.translitName) : product.translitName != null)
            return false;
        if (link != null ? !link.equals(product.link) : product.link != null) return false;
        if (countryFrom != null ? !countryFrom.equals(product.countryFrom) : product.countryFrom != null) return false;
        if (countryMade != null ? !countryMade.equals(product.countryMade) : product.countryMade != null) return false;
        if (barCode != null ? !barCode.equals(product.barCode) : product.barCode != null) return false;
        if (measure != null ? !measure.equals(product.measure) : product.measure != null) return false;
        if (availableDay != null ? !availableDay.equals(product.availableDay) : product.availableDay != null)
            return false;
        if (VAT != null ? !VAT.equals(product.VAT) : product.VAT != null) return false;
        if (metadesk != null ? !metadesk.equals(product.metadesk) : product.metadesk != null) return false;
        if (metakey != null ? !metakey.equals(product.metakey) : product.metakey != null) return false;
        if (customtitle != null ? !customtitle.equals(product.customtitle) : product.customtitle != null) return false;
        if (createDate != null ? !createDate.equals(product.createDate) : product.createDate != null) return false;
        if (editDate != null ? !editDate.equals(product.editDate) : product.editDate != null) return false;
        if (isPublishet != null ? !isPublishet.equals(product.isPublishet) : product.isPublishet != null) return false;
        if (isVisible != null ? !isVisible.equals(product.isVisible) : product.isVisible != null) return false;
        if (brand != null ? !brand.equals(product.brand) : product.brand != null) return false;
        if (shortDescription != null ? !shortDescription.equals(product.shortDescription) : product.shortDescription != null)
            return false;
        if (fullDescriotion != null ? !fullDescriotion.equals(product.fullDescriotion) : product.fullDescriotion != null)
            return false;
        if (attributes != null ? !attributes.equals(product.attributes) : product.attributes != null) return false;
        return prices != null ? prices.equals(product.prices) : product.prices == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (papId != null ? papId.hashCode() : 0);
        result = 31 * result + (pnt != null ? pnt.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        result = 31 * result + (translitName != null ? translitName.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (countryFrom != null ? countryFrom.hashCode() : 0);
        result = 31 * result + (countryMade != null ? countryMade.hashCode() : 0);
        result = 31 * result + (barCode != null ? barCode.hashCode() : 0);
        result = 31 * result + (measure != null ? measure.hashCode() : 0);
        result = 31 * result + (availableDay != null ? availableDay.hashCode() : 0);
        result = 31 * result + (VAT != null ? VAT.hashCode() : 0);
        result = 31 * result + (metadesk != null ? metadesk.hashCode() : 0);
        result = 31 * result + (metakey != null ? metakey.hashCode() : 0);
        result = 31 * result + (customtitle != null ? customtitle.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (editDate != null ? editDate.hashCode() : 0);
        result = 31 * result + (isPublishet != null ? isPublishet.hashCode() : 0);
        result = 31 * result + (isVisible != null ? isVisible.hashCode() : 0);
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (shortDescription != null ? shortDescription.hashCode() : 0);
        result = 31 * result + (fullDescriotion != null ? fullDescriotion.hashCode() : 0);
        result = 31 * result + (attributes != null ? attributes.hashCode() : 0);
        result = 31 * result + (prices != null ? prices.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", papId=" + papId +
                ", pnt=" + pnt +
                ", fullName='" + fullName + '\'' +
                ", shortName='" + shortName + '\'' +
                ", translitName='" + translitName + '\'' +
                ", link='" + link + '\'' +
                ", countryFrom='" + countryFrom + '\'' +
                ", countryMade='" + countryMade + '\'' +
                ", barCode='" + barCode + '\'' +
                ", measure='" + measure + '\'' +
                ", availableDay=" + availableDay +
                ", VAT=" + VAT +
                ", metadesk='" + metadesk + '\'' +
                ", metakey='" + metakey + '\'' +
                ", customtitle='" + customtitle + '\'' +
                ", createDate=" + createDate +
                ", editDate=" + editDate +
                ", isPublishet=" + isPublishet +
                ", isVisible=" + isVisible +
                ", brand=" + brand +
                ", shortDescription='" + shortDescription + '\'' +
                ", fullDescriotion='" + fullDescriotion + '\'' +
                ", attributes=" + attributes +
                ", prices=" + prices +
                '}';
    }
}
