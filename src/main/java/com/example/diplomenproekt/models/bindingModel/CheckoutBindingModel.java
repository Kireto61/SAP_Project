package com.example.diplomenproekt.models.bindingModel;

import javax.validation.constraints.Size;

public class CheckoutBindingModel {
    private String address;
    private Double totalPrice;

    public CheckoutBindingModel() {
    }

    @Size(min = 5, max = 20)
    public String getAddress() {
        return address;
    }

    public CheckoutBindingModel setAddress(String address) {
        this.address = address;
        return this;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public CheckoutBindingModel setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
        return this;
    }
}
