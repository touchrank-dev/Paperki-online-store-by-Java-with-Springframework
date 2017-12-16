package com.kushnir.paperki.model.user;

public class EnterpriseInfo {
    private Integer id;
    private String login;
    private String name;
    private String email;
    private String phone;
    private String unp;
    private String orgName;
    private String billingAddress;
    private String account;

    public EnterpriseInfo() {
    }

    public EnterpriseInfo(String phone, String unp, String orgName) {
        this.phone = phone;
        this.unp = unp;
        this.orgName = orgName;
    }

    public EnterpriseInfo(String email, String phone, String unp, String orgName) {
        this.email = email;
        this.phone = phone;
        this.unp = unp;
        this.orgName = orgName;
    }

    public EnterpriseInfo(Integer id, String email, String phone, String unp, String orgName) {
        this.id = id;
        this.email = email;
        this.phone = phone;
        this.unp = unp;
        this.orgName = orgName;
    }

    public EnterpriseInfo(String login,
                          String name,
                          String email,
                          String phone,
                          String unp,
                          String orgName,
                          String billingAddress,
                          String account) {
        this.login = login;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.unp = unp;
        this.orgName = orgName;
        this.billingAddress = billingAddress;
        this.account = account;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUnp() {
        return unp;
    }

    public void setUnp(String unp) {
        this.unp = unp;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EnterpriseInfo that = (EnterpriseInfo) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (phone != null ? !phone.equals(that.phone) : that.phone != null) return false;
        if (unp != null ? !unp.equals(that.unp) : that.unp != null) return false;
        if (orgName != null ? !orgName.equals(that.orgName) : that.orgName != null) return false;
        if (billingAddress != null ? !billingAddress.equals(that.billingAddress) : that.billingAddress != null)
            return false;
        return account != null ? account.equals(that.account) : that.account == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (unp != null ? unp.hashCode() : 0);
        result = 31 * result + (orgName != null ? orgName.hashCode() : 0);
        result = 31 * result + (billingAddress != null ? billingAddress.hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "EnterpriseInfo{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", unp='" + unp + '\'' +
                ", orgName='" + orgName + '\'' +
                ", billingAddress='" + billingAddress + '\'' +
                ", account='" + account + '\'' +
                '}';
    }
}
