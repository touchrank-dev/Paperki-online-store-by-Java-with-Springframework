package com.kushnir.paperki.model.user;

public class EnterpriseErrorForm {
    private String name;
    private String unp;
    private String address;

    private Boolean isErrors = false;

    public Boolean isErrors(){return this.isErrors;}

    public EnterpriseErrorForm() {
    }

    public EnterpriseErrorForm(String name, String unp, String address, Boolean isErrors) {
        this.name = name;
        this.unp = unp;
        this.address = address;
        this.isErrors = isErrors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.isErrors = true;
    }

    public String getUnp() {
        return unp;
    }

    public void setUnp(String unp) {
        this.unp = unp;
        this.isErrors = true;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        this.isErrors = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EnterpriseErrorForm that = (EnterpriseErrorForm) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (unp != null ? !unp.equals(that.unp) : that.unp != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        return isErrors != null ? isErrors.equals(that.isErrors) : that.isErrors == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (unp != null ? unp.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (isErrors != null ? isErrors.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EnterpriseErrorForm{" +
                "name='" + name + '\'' +
                ", unp='" + unp + '\'' +
                ", address='" + address + '\'' +
                ", isErrors=" + isErrors +
                '}';
    }
}
