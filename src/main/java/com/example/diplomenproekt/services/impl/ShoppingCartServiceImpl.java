package com.example.diplomenproekt.services.impl;

import com.example.diplomenproekt.models.entities.ShoppingCartEntity;
import com.example.diplomenproekt.repositories.ShoppingCartRepository;
import com.example.diplomenproekt.services.ShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository) {
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    public void addShoppingCartEntity(ShoppingCartEntity shoppingCartEntity) {
        shoppingCartRepository.save(shoppingCartEntity);
    }

    @Override
    public List<ShoppingCartEntity> findAllShoppingCartEntities() {
        return shoppingCartRepository.findAll();
    }

    @Override
    public void saveShoppingCart(ShoppingCartEntity existingShoppingCart) {
        shoppingCartRepository.save(existingShoppingCart);
    }
}
