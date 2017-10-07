package com.kushnir.paperki.model.product;

public class CSVProduct {
    Integer pnt;
    Integer groupPapId;
    String personalGroupName;
    String fullName;
    String shortName;
    Integer brandId;
    String countryFrom;
    String countryMade;
    String barCode;
    String measure;
    Integer availableDay;
    double basePrice;
    Integer VAT;

    public CSVProduct() {
    }

    public CSVProduct(Integer pnt,
                      Integer groupPapId,
                      String personalGroupName,
                      String fullName,
                      String shortName,
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
        this.brandId = brandId;
        this.countryFrom = countryFrom;
        this.countryMade = countryMade;
        this.barCode = barCode;
        this.measure = measure;
        this.availableDay = availableDay;
        this.basePrice = basePrice;
        this.VAT = VAT;
    }

    public Integer getPnt() {
        return pnt;
    }

    public void setPnt(Integer pnt) {
        this.pnt = pnt;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CSVProduct that = (CSVProduct) o;

        if (Double.compare(that.basePrice, basePrice) != 0) return false;
        if (pnt != null ? !pnt.equals(that.pnt) : that.pnt != null) return false;
        if (groupPapId != null ? !groupPapId.equals(that.groupPapId) : that.groupPapId != null) return false;
        if (personalGroupName != null ? !personalGroupName.equals(that.personalGroupName) : that.personalGroupName != null)
            return false;
        if (fullName != null ? !fullName.equals(that.fullName) : that.fullName != null) return false;
        if (shortName != null ? !shortName.equals(that.shortName) : that.shortName != null) return false;
        if (brandId != null ? !brandId.equals(that.brandId) : that.brandId != null) return false;
        if (countryFrom != null ? !countryFrom.equals(that.countryFrom) : that.countryFrom != null) return false;
        if (countryMade != null ? !countryMade.equals(that.countryMade) : that.countryMade != null) return false;
        if (barCode != null ? !barCode.equals(that.barCode) : that.barCode != null) return false;
        if (measure != null ? !measure.equals(that.measure) : that.measure != null) return false;
        if (availableDay != null ? !availableDay.equals(that.availableDay) : that.availableDay != null) return false;
        return VAT != null ? VAT.equals(that.VAT) : that.VAT == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = pnt != null ? pnt.hashCode() : 0;
        result = 31 * result + (groupPapId != null ? groupPapId.hashCode() : 0);
        result = 31 * result + (personalGroupName != null ? personalGroupName.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (shortName != null ? shortName.hashCode() : 0);
        result = 31 * result + (brandId != null ? brandId.hashCode() : 0);
        result = 31 * result + (countryFrom != null ? countryFrom.hashCode() : 0);
        result = 31 * result + (countryMade != null ? countryMade.hashCode() : 0);
        result = 31 * result + (barCode != null ? barCode.hashCode() : 0);
        result = 31 * result + (measure != null ? measure.hashCode() : 0);
        result = 31 * result + (availableDay != null ? availableDay.hashCode() : 0);
        temp = Double.doubleToLongBits(basePrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (VAT != null ? VAT.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CSVProduct{" +
                "pnt=" + pnt +
                ", groupPapId=" + groupPapId +
                ", personalGroupName='" + personalGroupName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", shortName='" + shortName + '\'' +
                ", brandId=" + brandId +
                ", countryFrom='" + countryFrom + '\'' +
                ", countryMade='" + countryMade + '\'' +
                ", barCode='" + barCode + '\'' +
                ", measure='" + measure + '\'' +
                ", availableDay=" + availableDay +
                ", basePrice=" + basePrice +
                ", VAT=" + VAT +
                '}';
    }
}
