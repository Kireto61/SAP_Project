package com.example.diplomenproekt.models.bindingModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class AddCompanyBindingModel {
    private String name;
    private String mol;
    private Integer vat;
    private String address;
    private String registerBoolean;

    public AddCompanyBindingModel() {
    }

    @Size(min = 5, max = 20)
    @NotBlank
    public String getName() {
        return name;
    }

    public AddCompanyBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    @Size(min = 5, max = 20)
    @NotBlank
    public String getMol() {
        return mol;
    }

    public AddCompanyBindingModel setMol(String mol) {
        this.mol = mol;
        return this;
    }

    @Positive
    @NotNull
    public Integer getVat() {
        return vat;
    }

    public AddCompanyBindingModel setVat(Integer vat) {
        this.vat = vat;
        return this;
    }

    @Size(min = 5, max = 20)
    @NotBlank
    public String getAddress() {
        return address;
    }

    public AddCompanyBindingModel setAddress(String address) {
        this.address = address;
        return this;
    }

    @NotBlank
    public String getRegisterBoolean() {
        return registerBoolean;
    }

    public AddCompanyBindingModel setRegisterBoolean(String registerBoolean) {
        this.registerBoolean = registerBoolean;
        return this;
    }
}
