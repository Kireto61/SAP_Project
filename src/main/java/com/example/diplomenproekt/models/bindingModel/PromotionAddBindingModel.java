package com.example.diplomenproekt.models.bindingModel;

import javax.validation.constraints.NotBlank;

public class PromotionAddBindingModel {

    private Double promotion;
    private String category;
    private String company;


    public PromotionAddBindingModel() {
    }

    public Double getPromotion() {
        return promotion;
    }

    public PromotionAddBindingModel setPromotion(Double promotion) {
        this.promotion = promotion;
        return this;
    }


    @NotBlank
    public String getCategory() {
        return category;
    }

    public PromotionAddBindingModel setCategory(String category) {
        this.category = category;
        return this;
    }

    @NotBlank
    public String getCompany() {
        return company;
    }

    public PromotionAddBindingModel setCompany(String company) {
        this.company = company;
        return this;
    }

}
