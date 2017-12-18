package com.kushnir.paperki.model.password;

public class NewPasswordForm {

    private String oldPassword;
    private String newPassword;
    private String newPasswordConfirm;

    public NewPasswordForm() {
    }

    public NewPasswordForm(String newPassword, String newPasswordConfirm) {
        this.newPassword = newPassword;
        this.newPasswordConfirm = newPasswordConfirm;
    }

    public NewPasswordForm(String oldPassword, String newPassword, String newPasswordConfirm) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.newPasswordConfirm = newPasswordConfirm;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordConfirm() {
        return newPasswordConfirm;
    }

    public void setNewPasswordConfirm(String newPasswordConfirm) {
        this.newPasswordConfirm = newPasswordConfirm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewPasswordForm that = (NewPasswordForm) o;

        if (oldPassword != null ? !oldPassword.equals(that.oldPassword) : that.oldPassword != null) return false;
        if (newPassword != null ? !newPassword.equals(that.newPassword) : that.newPassword != null) return false;
        return newPasswordConfirm != null ? newPasswordConfirm.equals(that.newPasswordConfirm) : that.newPasswordConfirm == null;
    }

    @Override
    public int hashCode() {
        int result = oldPassword != null ? oldPassword.hashCode() : 0;
        result = 31 * result + (newPassword != null ? newPassword.hashCode() : 0);
        result = 31 * result + (newPasswordConfirm != null ? newPasswordConfirm.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NewPasswordForm{" +
                "oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", newPasswordConfirm='" + newPasswordConfirm + '\'' +
                '}';
    }
}
