package com.example.diplomenproekt.models.bindingModel;

import javax.validation.constraints.Size;

public class ProfileUpdateBindingModel {
    private String firstName;
    private String lastName;
    private String email;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

    public ProfileUpdateBindingModel() {
    }

    @Size(min = 3, max = 20)
    public String getFirstName() {
        return firstName;
    }

    public ProfileUpdateBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Size(min = 3, max = 20)
    public String getLastName() {
        return lastName;
    }

    public ProfileUpdateBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Size(min = 3, max = 20)
    public String getEmail() {
        return email;
    }

    public ProfileUpdateBindingModel setEmail(String email) {
        this.email = email;
        return this;
    }

    @Size(min = 3, max = 20)
    public String getOldPassword() {
        return oldPassword;
    }

    public ProfileUpdateBindingModel setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
        return this;
    }

    @Size(min = 3, max = 20)
    public String getNewPassword() {
        return newPassword;
    }

    public ProfileUpdateBindingModel setNewPassword(String newPassword) {
        this.newPassword = newPassword;
        return this;
    }

    @Size(min = 3, max = 20)
    public String getConfirmPassword() {
        return confirmPassword;
    }

    public ProfileUpdateBindingModel setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
        return this;
    }
}
