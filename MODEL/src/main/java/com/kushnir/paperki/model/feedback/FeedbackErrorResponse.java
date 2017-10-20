package com.kushnir.paperki.model.feedback;

public class FeedbackErrorResponse {
    private String userName;
    private String email;
    private String text;

    private Boolean isErrors = false;
    public Boolean isErrors(){return this.isErrors;}

    public FeedbackErrorResponse() {
    }

    public FeedbackErrorResponse(String userName, String email, String text) {
        this.userName = userName;
        this.email = email;
        this.text = text;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        this.isErrors = true;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        this.isErrors = true;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        this.isErrors = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FeedbackErrorResponse that = (FeedbackErrorResponse) o;

        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        return text != null ? text.equals(that.text) : that.text == null;
    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FeedbackErrorResponse{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
