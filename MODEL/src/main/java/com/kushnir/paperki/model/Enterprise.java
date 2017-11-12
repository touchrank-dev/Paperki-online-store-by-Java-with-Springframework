package com.kushnir.paperki.model;

import java.util.ArrayList;

public class Enterprise {
    private Integer id;
    private Integer userId;
    private String UNP;
    private String enterpriseName;
    private String billingAddress;
    private String accountNumber;
    private String bankName;
    private Integer bankCode;

    private ArrayList<BillingAccount> billingAccounts = new ArrayList<>();

    public ArrayList<BillingAccount> getBillingAccount() {
        return billingAccounts;
    }

    public void setBillingAccount(ArrayList<BillingAccount> billingAccount) {
        this.billingAccounts = billingAccount;
    }

    public Enterprise() {
    }

    public Enterprise(Integer userId, String UNP, String enterpriseName) {
        this.userId = userId;
        this.UNP = UNP;
        this.enterpriseName = enterpriseName;
    }

    public Enterprise(Integer id, Integer userId, String UNP, String enterpriseName) {
        this.id = id;
        this.userId = userId;
        this.UNP = UNP;
        this.enterpriseName = enterpriseName;
    }

    public Enterprise(Integer userId,
                      String UNP,
                      String enterpriseName,
                      String billingAddress) {
        this.userId = userId;
        this.UNP = UNP;
        this.enterpriseName = enterpriseName;
        this.billingAddress = billingAddress;
    }

    public Enterprise(Integer userId,
                      String UNP,
                      String enterpriseName,
                      String billingAddress,
                      String accountNumber,
                      String bankName,
                      Integer bankCode) {
        this.userId = userId;
        this.UNP = UNP;
        this.enterpriseName = enterpriseName;
        this.billingAddress = billingAddress;
        this.accountNumber = accountNumber;
        this.bankName = bankName;
        this.bankCode = bankCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUNP() {
        return UNP;
    }

    public void setUNP(String UNP) {
        this.UNP = UNP;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Enterprise that = (Enterprise) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        if (UNP != null ? !UNP.equals(that.UNP) : that.UNP != null) return false;
        if (enterpriseName != null ? !enterpriseName.equals(that.enterpriseName) : that.enterpriseName != null)
            return false;
        if (billingAddress != null ? !billingAddress.equals(that.billingAddress) : that.billingAddress != null)
            return false;
        if (accountNumber != null ? !accountNumber.equals(that.accountNumber) : that.accountNumber != null)
            return false;
        if (bankName != null ? !bankName.equals(that.bankName) : that.bankName != null) return false;
        return bankCode != null ? bankCode.equals(that.bankCode) : that.bankCode == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (UNP != null ? UNP.hashCode() : 0);
        result = 31 * result + (enterpriseName != null ? enterpriseName.hashCode() : 0);
        result = 31 * result + (billingAddress != null ? billingAddress.hashCode() : 0);
        result = 31 * result + (accountNumber != null ? accountNumber.hashCode() : 0);
        result = 31 * result + (bankName != null ? bankName.hashCode() : 0);
        result = 31 * result + (bankCode != null ? bankCode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Enterprise{" +
                "id=" + id +
                ", userId=" + userId +
                ", UNP='" + UNP + '\'' +
                ", enterpriseName='" + enterpriseName + '\'' +
                ", billingAddress='" + billingAddress + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankCode=" + bankCode +
                ", billingAccounts=" + billingAccounts +
                '}';
    }
}
