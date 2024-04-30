package com.example.diplomenproekt.models.viewModel;

import com.example.diplomenproekt.models.entities.enums.CategoryEnum;

public class ProductViewModel {
    private Long id;
    private String name;
    private Double price;
    private CategoryEnum category;
    private Integer quantity;
    private String url;

    public ProductViewModel() {
    }

    public String getName() {
        return name;
    }

    public ProductViewModel setName(String name) {
        this.name = name;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public ProductViewModel setPrice(Double price) {
        this.price = price;
        return this;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public ProductViewModel setCategory(CategoryEnum category) {
        this.category = category;
        return this;
    }

    public Long getId() {
        return id;
    }

    public ProductViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ProductViewModel setQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public ProductViewModel setUrl(String url) {
        this.url = url;
        return this;
    }
}
