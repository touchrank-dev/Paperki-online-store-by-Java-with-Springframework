package com.kushnir.paperki.model.password;

public class NewPasswordErrorForm {

    private String oldPassword;
    private String newPassword;
    private String newPasswordConfirm;

    private Boolean isErrors = false;

    public Boolean isErrors(){return this.isErrors;}

    public NewPasswordErrorForm() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
        this.isErrors = true;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        this.isErrors = true;
    }

    public String getNewPasswordConfirm() {
        return newPasswordConfirm;
    }

    public void setNewPasswordConfirm(String newPasswordConfirm) {
        this.newPasswordConfirm = newPasswordConfirm;
        this.isErrors = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewPasswordErrorForm that = (NewPasswordErrorForm) o;

        if (oldPassword != null ? !oldPassword.equals(that.oldPassword) : that.oldPassword != null) return false;
        if (newPassword != null ? !newPassword.equals(that.newPassword) : that.newPassword != null) return false;
        if (newPasswordConfirm != null ? !newPasswordConfirm.equals(that.newPasswordConfirm) : that.newPasswordConfirm != null)
            return false;
        return isErrors != null ? isErrors.equals(that.isErrors) : that.isErrors == null;
    }

    @Override
    public int hashCode() {
        int result = oldPassword != null ? oldPassword.hashCode() : 0;
        result = 31 * result + (newPassword != null ? newPassword.hashCode() : 0);
        result = 31 * result + (newPasswordConfirm != null ? newPasswordConfirm.hashCode() : 0);
        result = 31 * result + (isErrors != null ? isErrors.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NewPasswordErrorForm{" +
                "oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", newPasswordConfirm='" + newPasswordConfirm + '\'' +
                ", isErrors=" + isErrors +
                '}';
    }
}
