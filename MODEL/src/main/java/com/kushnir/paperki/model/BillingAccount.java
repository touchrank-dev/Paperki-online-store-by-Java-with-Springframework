package com.kushnir.paperki.model;

public class BillingAccount {
    private Integer id;
    private Integer enterpriseId;
    private String accountNumber;
    private String bankName;
    private Integer bankCode;
    private Boolean isPrimary;

    public BillingAccount() {
    }

    public BillingAccount(String accountNumber, String bankName, Integer bankCode) {
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.bankCode = bankCode;
    }

    public BillingAccount(Integer enterpriseId, String accountNumber, String bankName, Integer bankCode) {
        this.enterpriseId = enterpriseId;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.bankCode = bankCode;
    }

    public BillingAccount(Integer id, Integer enterpriseId, String accountNumber, String bankName, Integer bankCode) {
        this.id = id;
        this.enterpriseId = enterpriseId;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.bankCode = bankCode;
    }

    public BillingAccount(Integer id,
                          Integer enterpriseId,
                          String accountNumber,
                          String bankName,
                          Integer bankCode,
                          Boolean isPrimary) {
        this.id = id;
        this.enterpriseId = enterpriseId;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.bankCode = bankCode;
        this.isPrimary = isPrimary;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Integer getBankCode() {
        return bankCode;
    }

    public void setBankCode(Integer bankCode) {
        this.bankCode = bankCode;
    }

    public Boolean getPrimary() {
        return isPrimary;
    }

    public void setPrimary(Boolean primary) {
        isPrimary = primary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BillingAccount that = (BillingAccount) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (enterpriseId != null ? !enterpriseId.equals(that.enterpriseId) : that.enterpriseId != null) return false;
        if (accountNumber != null ? !accountNumber.equals(that.accountNumber) : that.accountNumber != null)
            return false;
        if (bankName != null ? !bankName.equals(that.bankName) : that.bankName != null) return false;
        if (bankCode != null ? !bankCode.equals(that.bankCode) : that.bankCode != null) return false;
        return isPrimary != null ? isPrimary.equals(that.isPrimary) : that.isPrimary == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (enterpriseId != null ? enterpriseId.hashCode() : 0);
        result = 31 * result + (accountNumber != null ? accountNumber.hashCode() : 0);
        result = 31 * result + (bankName != null ? bankName.hashCode() : 0);
        result = 31 * result + (bankCode != null ? bankCode.hashCode() : 0);
        result = 31 * result + (isPrimary != null ? isPrimary.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BillingAccount{" +
                "id=" + id +
                ", enterpriseId=" + enterpriseId +
                ", accountNumber='" + accountNumber + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankCode=" + bankCode +
                ", isPrimary=" + isPrimary +
                '}';
    }
}
