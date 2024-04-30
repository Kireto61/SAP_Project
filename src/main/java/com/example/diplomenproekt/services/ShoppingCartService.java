package com.example.diplomenproekt.services;

import com.example.diplomenproekt.models.entities.ShoppingCartEntity;

import java.util.List;

public interface ShoppingCartService {
    void addShoppingCartEntity(ShoppingCartEntity shoppingCartEntity);

    List<ShoppingCartEntity> findAllShoppingCartEntities();

    void saveShoppingCart(ShoppingCartEntity existingShoppingCart);
}
