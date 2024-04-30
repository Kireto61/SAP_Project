package com.example.diplomenproekt.services.impl;

import com.example.diplomenproekt.models.entities.HistoryEntity;
import com.example.diplomenproekt.models.entities.Product;
import com.example.diplomenproekt.models.entities.ShoppingCartEntity;
import com.example.diplomenproekt.models.entities.User;
import com.example.diplomenproekt.repositories.HistoryRepository;
import com.example.diplomenproekt.repositories.ProductRepository;
import com.example.diplomenproekt.repositories.ShoppingCartRepository;
import com.example.diplomenproekt.repositories.UserRepository;
import com.example.diplomenproekt.services.HistoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final HistoryRepository historyRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    public HistoryServiceImpl(UserRepository userRepository, ProductRepository productRepository, HistoryRepository historyRepository, ShoppingCartRepository shoppingCartRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.historyRepository = historyRepository;
        this.shoppingCartRepository = shoppingCartRepository;
    }

    @Override
    @Transactional
    public void secureCheckout(String address, Object email, Double totalPrice) throws Throwable {
        User user = userRepository.findByEmail(email.toString()).orElseThrow(() -> new Throwable("User with email " + email + " not found!"));

        HistoryEntity historyEntity = new HistoryEntity();
        historyEntity.setAddress(address);
        historyEntity.setPrice(totalPrice);
        historyEntity.setDate(LocalDate.now());
        historyEntity.setUser(user);
        historyRepository.save(historyEntity);

        setNullToShoppingCartProducts(user.getId());

        user.setProductsCart(new ArrayList<>());
        user.getHistoryProducts().add(historyEntity);
        userRepository.save(user);

        shoppingCartRepository.deleteAllByUser_Id(user.getId());

        deleteAllProductsWithZeroQuantity();
    }

    @Override
    public List<HistoryEntity> findAllHistoryProducts(Object email) throws Throwable {
        User user = userRepository.findByEmail(email.toString()).orElseThrow(() -> new Throwable("User with email " + email + " not found!"));
        return historyRepository.findAllByUser_Id(user.getId());
    }

    @Override
    public void clearHistory(Object email) throws Throwable {
        User user = userRepository.findByEmail(email.toString()).orElseThrow(() -> new Throwable("User with email " + email + " not found!"));
        user.setHistoryProducts(new ArrayList<>());
        userRepository.save(user);
        setNullToHistoryProducts(user.getId());
        deleteAllHistoryUserProducts(user.getId());
    }

    private void deleteAllHistoryUserProducts(Long userId) {
        List<HistoryEntity> allByUser_id = historyRepository.findAll();
        for (HistoryEntity historyEntity : allByUser_id) {
            if(historyEntity.getUser() == null){
                historyRepository.delete(historyEntity);
            }
        }
    }

    private void setNullToHistoryProducts(Long userId) {
        List<HistoryEntity> allByUser_id = historyRepository.findAllByUser_Id(userId);
        for (HistoryEntity historyEntity : allByUser_id) {
            historyEntity.setUser(null);
            historyRepository.save(historyEntity);
        }
    }

    private void deleteAllProductsWithZeroQuantity() {
        for (Product product : productRepository.findAll()) {
            if(product.getQuantity() <= 0){
                productRepository.delete(product);
            }
        }

    }

    private void setNullToShoppingCartProducts(Long userId) {
        List<ShoppingCartEntity> allByUser_id = shoppingCartRepository.findAllByUser_Id(userId);
        for (ShoppingCartEntity shoppingCartEntity : allByUser_id) {
            shoppingCartEntity.setProduct(null);
            shoppingCartRepository.save(shoppingCartEntity);
        }
    }
}
