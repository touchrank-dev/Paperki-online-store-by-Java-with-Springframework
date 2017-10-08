package com.kushnir.paperki.model.product;

public class CSVProduct {
    private Integer id;
    private Integer pnt;
    private Integer papId;
    private Integer groupPapId;
    private String personalGroupName;
    private String fullName;
    private String shortName;
    private String translitName;
    private String link;
    private Integer brandId;
    private String countryFrom;
    private String countryMade;
    private String barCode;
    private String measure;
    private Integer availableDay;
    private double basePrice;
    private Integer VAT;

    private Integer categoryId;
    private Integer order;

    public CSVProduct() {
    }

    public CSVProduct(Integer pnt,
                      Integer groupPapId,
                      String personalGroupName,
                      String fullName,
                      String shortName,
                      String translitName,
                      Integer brandId,
                      String countryFrom,
                      String countryMade,
                      String barCode,
                      String measure,
                      Integer availableDay,
                      double basePrice,
                      Integer VAT) {
        this.pnt = pnt;
        this.groupPapId = groupPapId;
        this.personalGroupName = personalGroupName;
        this.fullName = fullName;
        this.shortName = shortName;
        this.translitName = translitName;
        this.brandId = brandId;
        this.countryFrom = countryFrom;
        this.countryMade = countryMade;
        this.barCode = barCode;
        this.measure = measure;
        this.availableDay = availableDay;
        this.basePrice = basePrice;
        this.VAT = VAT;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPnt() {
        return pnt;
    }

    public void setPnt(Integer pnt) {
        this.pnt = pnt;
    }

    public Integer getPapId() {
        return papId;
    }

    public void setPapId(Integer papId) {
        this.papId = papId;
    }

    public Integer getGroupPapId() {
        return groupPapId;
    }

    public void setGroupPapId(Integer groupPapId) {
        this.groupPapId = groupPapId;
    }

    public String getPersonalGroupName() {
        return personalGroupName;
    }

    public void setPersonalGroupName(String personalGroupName) {
        this.personalGroupName = personalGroupName;
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

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
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

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public Integer getVAT() {
        return VAT;
    }

    public void setVAT(Integer VAT) {
        this.VAT = VAT;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CSVProduct that = (CSVProduct) o;

        if (Double.compare(that.basePrice, basePrice) != 0) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (pnt != null ? !pnt.equals(that.pnt) : that.pnt != null) return false;
        if (papId != null ? !papId.equals(that.papId) : that.papId != null) return false;
        if (groupPapId != null ? !groupPapId.equals(that.groupPapId) : that.groupPapId != null) return false;
        if (personalGroupName != null ? !personalGroupName.equals(that.personalGroupName) : that.personalGroupName != null)
            return false;
        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null) return false;
        if (shortName != null ? !shortName.equals(that.shortName) : that.shortName != null) return false;
        if (translitName != null ? !translitName.equals(that.translitName) : that.translitName != null) return false;
        if (link != null ? !link.equals(that.link) : that.link != null) return false;
        if (brandId != null ? !brandId.equals(that.brandId) : that.brandId != null) return false;
        if (countryFrom != null ? !countryFrom.equals(that.countryFrom) : that.countryFrom != null) return false;
        if (countryMade != null ? !countryMade.equals(that.countryMade) : that.countryMade != null) return false;
        if (barCode != null ? !barCode.equals(that.barCode) : that.barCode != null) return false;
        if (measure != null ? !measure.equals(that.measure) : that.measure != null) return false;
        if (availableDay != null ? !availableDay.equals(that.availableDay) : that.availableDay != null) return false;
        if (VAT != null ? !VAT.equals(that.VAT) : that.VAT != null) return false;
        if (categoryId != null ? !categoryId.equals(that.categoryId) : that.categoryId != null) return false;
        return order != null ? order.equals(that.order) : that.order == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (pnt != null ? pnt.hashCode() : 0);
        result = 31 * result + (papId != null ? papId.hashCode() : 0);
        result = 31 * result + (groupPapId != null ? groupPapId.hashCode() : 0);
        result = 31 * result + (personalGroupName != null ? personalGroupName.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        result = 31 * result + (translitName != null ? translitName.hashCode() : 0);
        result = 31 * result + (link != null ? link.hashCode() : 0);
        result = 31 * result + (brandId != null ? brandId.hashCode() : 0);
        result = 31 * result + (countryFrom != null ? countryFrom.hashCode() : 0);
        result = 31 * result + (countryMade != null ? countryMade.hashCode() : 0);
        result = 31 * result + (barCode != null ? barCode.hashCode() : 0);
        result = 31 * result + (measure != null ? measure.hashCode() : 0);
        result = 31 * result + (availableDay != null ? availableDay.hashCode() : 0);
        temp = Double.doubleToLongBits(basePrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (VAT != null ? VAT.hashCode() : 0);
        result = 31 * result + (categoryId != null ? categoryId.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CSVProduct{" +
                "id=" + id +
                ", pnt=" + pnt +
                ", papId=" + papId +
                ", groupPapId=" + groupPapId +
                ", personalGroupName='" + personalGroupName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", shortName='" + shortName + '\'' +
                ", translitName='" + translitName + '\'' +
                ", link='" + link + '\'' +
                ", brandId=" + brandId +
                ", countryFrom='" + countryFrom + '\'' +
                ", countryMade='" + countryMade + '\'' +
                ", barCode='" + barCode + '\'' +
                ", measure='" + measure + '\'' +
                ", availableDay=" + availableDay +
                ", basePrice=" + basePrice +
                ", VAT=" + VAT +
                ", categoryId=" + categoryId +
                ", order=" + order +
                '}';
    }
}
