package com.example.diplomenproekt.models.bindingModel;

public class AddToCartBindingModel {
    private Integer portion;

    public AddToCartBindingModel() {
    }


    public Integer getPortion() {
        return portion;
    }

    public AddToCartBindingModel setPortion(Integer portion) {
        this.portion = portion;
        return this;
    }
}
