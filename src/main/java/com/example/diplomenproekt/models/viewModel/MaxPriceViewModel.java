package com.example.diplomenproekt.models.viewModel;

public class MaxPriceViewModel {
    private Double price;

    public MaxPriceViewModel() {
    }

    public Double getPrice() {
        return price;
    }

    public MaxPriceViewModel setPrice(Double price) {
        this.price = price;
        return this;
    }
}
