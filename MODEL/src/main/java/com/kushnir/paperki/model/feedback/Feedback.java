package com.kushnir.paperki.model.feedback;

import java.time.LocalDate;

public class Feedback {

    private int idFeedback;
    private int idUser;
    private String userName;
    private String email;
    private String ipAddress;
    private int rate;
    private String text;
    private LocalDate createDate;
    private Boolean approve;
    private Boolean isPublished;

    public Feedback() {
    }

    public Feedback(String userName,
                    String email,
                    String text) {
        this.userName = userName;
        this.email = email;
        this.text = text;
    }

    public Feedback(int idUser,
                    String userName,
                    String email,
                    String ipAddress,
                    String text) {
        this.idUser = idUser;
        this.userName = userName;
        this.email = email;
        this.ipAddress = ipAddress;
        this.text = text;
    }

    public Feedback(int idUser,
                    String userName,
                    String email,
                    String ipAddress,
                    int rate,
                    String text,
                    LocalDate createDate,
                    Boolean approve,
                    Boolean isPublished) {
        this.idUser = idUser;
        this.userName = userName;
        this.email = email;
        this.ipAddress = ipAddress;
        this.rate = rate;
        this.text = text;
        this.createDate = createDate;
        this.approve = approve;
        this.isPublished = isPublished;
    }

    public Feedback(int idFeedback,
                    int idUser,
                    String userName,
                    String email,
                    String ipAddress,
                    int rate,
                    String text,
                    LocalDate createDate,
                    Boolean approve,
                    Boolean isPublished) {
        this.idFeedback = idFeedback;
        this.idUser = idUser;
        this.userName = userName;
        this.email = email;
        this.ipAddress = ipAddress;
        this.rate = rate;
        this.text = text;
        this.createDate = createDate;
        this.approve = approve;
        this.isPublished = isPublished;
    }

    public int getIdFeedback() {
        return idFeedback;
    }

    public void setIdFeedback(int idFeedback) {
        this.idFeedback = idFeedback;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public Boolean getApprove() {
        return approve;
    }

    public void setApprove(Boolean approve) {
        this.approve = approve;
    }

    public Boolean getPublished() {
        return isPublished;
    }

    public void setPublished(Boolean published) {
        isPublished = published;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Feedback feedback = (Feedback) o;

        if (idFeedback != feedback.idFeedback) return false;
        if (idUser != feedback.idUser) return false;
        if (rate != feedback.rate) return false;
        if (userName != null ? !userName.equals(feedback.userName) : feedback.userName != null) return false;
        if (email != null ? !email.equals(feedback.email) : feedback.email != null) return false;
        if (ipAddress != null ? !ipAddress.equals(feedback.ipAddress) : feedback.ipAddress != null) return false;
        if (text != null ? !text.equals(feedback.text) : feedback.text != null) return false;
        if (createDate != null ? !createDate.equals(feedback.createDate) : feedback.createDate != null) return false;
        if (approve != null ? !approve.equals(feedback.approve) : feedback.approve != null) return false;
        return isPublished != null ? isPublished.equals(feedback.isPublished) : feedback.isPublished == null;
    }

    @Override
    public int hashCode() {
        int result = idFeedback;
        result = 31 * result + idUser;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (ipAddress != null ? ipAddress.hashCode() : 0);
        result = 31 * result + rate;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (createDate != null ? createDate.hashCode() : 0);
        result = 31 * result + (approve != null ? approve.hashCode() : 0);
        result = 31 * result + (isPublished != null ? isPublished.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "idFeedback=" + idFeedback +
                ", idUser=" + idUser +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", rate=" + rate +
                ", text='" + text + '\'' +
                ", createDate=" + createDate +
                ", approve=" + approve +
                ", isPublished=" + isPublished +
                '}';
    }
}
