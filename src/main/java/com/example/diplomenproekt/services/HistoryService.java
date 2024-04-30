package com.example.diplomenproekt.services;

import com.example.diplomenproekt.models.entities.HistoryEntity;

import java.util.List;

public interface HistoryService {
    void secureCheckout(String address, Object email, Double totalPrice) throws Throwable;

    List<HistoryEntity> findAllHistoryProducts(Object email) throws Throwable;

    void clearHistory(Object email) throws Throwable;

}
