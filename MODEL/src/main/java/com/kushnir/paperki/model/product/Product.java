package com.kushnir.paperki.model.product;

import com.kushnir.paperki.model.Brand;
import com.kushnir.paperki.model.Discount;
import com.kushnir.paperki.model.Price;
import com.kushnir.paperki.model.category.CategorySimple;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private Integer quantity;

    private Brand brand;
    private CategorySimple category;

    private ArrayList<Attribute> attributes;
    private HashMap<Integer, Price> prices;
    private Discount discount;

    private double basePrice;
    private double basePriceWithVAT;
    private double finalPrice;
    private double finalPriceWithVAT;

    private Integer VAT;
    private String metadesk;
    private String metakey;
    private String customtitle;
    private LocalDateTime createDate;
    private LocalDateTime editDate;
    private Boolean isPublished;
    private Boolean isVisible;
    private String shortDescription;
    private String fullDescription;

    public Product() {
    }

    public Product(Integer id,
                   Integer papId,
                   Integer pnt,
                   String fullName,
                   String shortName,
                   String translitName,
                   String link,
                   String countryFrom,
                   String countryMade,
                   String measure,
                   Integer availableDay,
                   Integer quantity,
                   double basePrice,
                   double basePriceWithVAT,
                   double finalPrice,
                   double finalPriceWithVAT,
                   Integer VAT,
                   Boolean isPublished,
                   Boolean isVisible,
                   Brand brand,
                   HashMap<Integer, Price> prices) {
        this.id = id;
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
        this.quantity = quantity;

        this.basePrice = basePrice;
        this.basePriceWithVAT = basePriceWithVAT;
        this.finalPrice = finalPrice;
        this.finalPriceWithVAT = finalPriceWithVAT;

        this.VAT = VAT;
        this.isPublished = isPublished;
        this.isVisible = isVisible;
        this.brand = brand;
        this.prices = prices;
    }

    public Product(Integer id,
                   Integer papId,
                   Integer pnt,
                   String fullName,
                   String shortName,
                   String translitName,
                   String link,
                   String countryFrom,
                   String countryMade,
                   String measure,
                   Integer availableDay,
                   Integer quantity,
                   Integer VAT,
                   Boolean isPublished,
                   Boolean isVisible,
                   Brand brand,
                   String shortDescription,
                   String fullDescription,
                   HashMap<Integer, Price> prices) {
        this.id = id;
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
        this.quantity = quantity;
        this.VAT = VAT;
        this.isPublished = isPublished;
        this.isVisible = isVisible;
        this.brand = brand;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public CategorySimple getCategory() {
        return category;
    }

    public void setCategory(CategorySimple category) {
        this.category = category;
    }

    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(ArrayList<Attribute> attributes) {
        this.attributes = attributes;
    }

    public HashMap<Integer, Price> getPrices() {
        return prices;
    }

    public void setPrices(HashMap<Integer, Price> prices) {
        this.prices = prices;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getBasePriceWithVAT() {
        return basePriceWithVAT;
    }

    public void setBasePriceWithVAT(double basePriceWithVAT) {
        this.basePriceWithVAT = basePriceWithVAT;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public double getFinalPriceWithVAT() {
        return finalPriceWithVAT;
    }

    public void setFinalPriceWithVAT(double finalPriceWithVAT) {
        this.finalPriceWithVAT = finalPriceWithVAT;
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

        Product product = (Product) o;

        if (Double.compare(product.basePrice, basePrice) != 0) return false;
        if (Double.compare(product.basePriceWithVAT, basePriceWithVAT) != 0) return false;
        if (Double.compare(product.finalPrice, finalPrice) != 0) return false;
        if (Double.compare(product.finalPriceWithVAT, finalPriceWithVAT) != 0) return false;
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
        if (quantity != null ? !quantity.equals(product.quantity) : product.quantity != null) return false;
        if (brand != null ? !brand.equals(product.brand) : product.brand != null) return false;
        if (category != null ? !category.equals(product.category) : product.category != null) return false;
        if (attributes != null ? !attributes.equals(product.attributes) : product.attributes != null) return false;
        if (prices != null ? !prices.equals(product.prices) : product.prices != null) return false;
        if (discount != null ? !discount.equals(product.discount) : product.discount != null) return false;
        if (VAT != null ? !VAT.equals(product.VAT) : product.VAT != null) return false;
        if (metadesk != null ? !metadesk.equals(product.metadesk) : product.metadesk != null) return false;
        if (metakey != null ? !metakey.equals(product.metakey) : product.metakey != null) return false;
        if (customtitle != null ? !customtitle.equals(product.customtitle) : product.customtitle != null) return false;
        if (createDate != null ? !createDate.equals(product.createDate) : product.createDate != null) return false;
        if (editDate != null ? !editDate.equals(product.editDate) : product.editDate != null) return false;
        if (isPublished != null ? !isPublished.equals(product.isPublished) : product.isPublished != null) return false;
        if (isVisible != null ? !isVisible.equals(product.isVisible) : product.isVisible != null) return false;
        if (shortDescription != null ? !shortDescription.equals(product.shortDescription) : product.shortDescription != null)
            return false;
        return fullDescription != null ? fullDescription.equals(product.fullDescription) : product.fullDescription == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
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
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (brand != null ? brand.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (attributes != null ? attributes.hashCode() : 0);
        result = 31 * result + (prices != null ? prices.hashCode() : 0);
        result = 31 * result + (discount != null ? discount.hashCode() : 0);
        temp = Double.doubleToLongBits(basePrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(basePriceWithVAT);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(finalPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(finalPriceWithVAT);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (VAT != null ? VAT.hashCode() : 0);
        result = 31 * result + (metadesk != null ? metadesk.hashCode() : 0);
        result = 31 * result + (metakey != null ? metakey.hashCode() : 0);
        result = 31 * result + (customtitle != null ? customtitle.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (editDate != null ? editDate.hashCode() : 0);
        result = 31 * result + (isPublished != null ? isPublished.hashCode() : 0);
        result = 31 * result + (isVisible != null ? isVisible.hashCode() : 0);
        result = 31 * result + (shortDescription != null ? shortDescription.hashCode() : 0);
        result = 31 * result + (fullDescription != null ? fullDescription.hashCode() : 0);
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
                ", quantity=" + quantity +
                ", brand=" + brand +
                ", category=" + category +
                ", attributes=" + attributes +
                ", prices=" + prices +
                ", discount=" + discount +
                ", basePrice=" + basePrice +
                ", basePriceWithVAT=" + basePriceWithVAT +
                ", finalPrice=" + finalPrice +
                ", finalPriceWithVAT=" + finalPriceWithVAT +
                ", VAT=" + VAT +
                ", metadesk='" + metadesk + '\'' +
                ", metakey='" + metakey + '\'' +
                ", customtitle='" + customtitle + '\'' +
                ", createDate=" + createDate +
                ", editDate=" + editDate +
                ", isPublished=" + isPublished +
                ", isVisible=" + isVisible +
                ", shortDescription='" + shortDescription + '\'' +
                ", fullDescription='" + fullDescription + '\'' +
                '}';
    }
}
