package com.kushnir.paperki.model.product;

public class StockItem {
    private Integer id;
    private Integer pnt;
    private Integer quantityAvailable;

    private Integer isStock;

    public StockItem() {
    }

    public StockItem(Integer pnt, Integer quantityAvailable) {
        this.pnt = pnt;
        this.quantityAvailable = quantityAvailable;
    }

    public StockItem(Integer id, Integer pnt, Integer quantityAvailable) {
        this.id = id;
        this.pnt = pnt;
        this.quantityAvailable = quantityAvailable;
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

    public Integer getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(Integer quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
    }

    public Integer getIsStock() {
        return isStock;
    }

    public void setIsStock(Integer isStock) {
        this.isStock = isStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StockItem stockItem = (StockItem) o;

        if (id != null ? !id.equals(stockItem.id) : stockItem.id != null) return false;
        if (pnt != null ? !pnt.equals(stockItem.pnt) : stockItem.pnt != null) return false;
        if (quantityAvailable != null ? !quantityAvailable.equals(stockItem.quantityAvailable) : stockItem.quantityAvailable != null)
            return false;
        return isStock != null ? isStock.equals(stockItem.isStock) : stockItem.isStock == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (pnt != null ? pnt.hashCode() : 0);
        result = 31 * result + (quantityAvailable != null ? quantityAvailable.hashCode() : 0);
        result = 31 * result + (isStock != null ? isStock.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StockItem{" +
                "id=" + id +
                ", pnt=" + pnt +
                ", quantityAvailable=" + quantityAvailable +
                ", isStock=" + isStock +
                '}';
    }
}
