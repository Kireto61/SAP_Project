package com.example.diplomenproekt.services;

import com.example.diplomenproekt.models.bindingModel.ProductAddBindingModel;
import com.example.diplomenproekt.models.viewModel.MaxPriceViewModel;
import com.example.diplomenproekt.models.viewModel.ProductViewModel;

import java.util.List;

public interface ProductsService {
    void addProduct(ProductAddBindingModel productAddBindingModel, Object email) throws Throwable;

    List<ProductViewModel> getAllProducts();

    List<ProductViewModel> getAllVideocards();

    List<ProductViewModel> getAllProcessors();

    List<ProductViewModel> getAllHeadphones();

    List<ProductViewModel> getAllPrinters();

    List<ProductViewModel> getAllOfficeChairs();

    List<ProductViewModel> getAllScanners();

    List<ProductViewModel> getAllRouters();

    List<ProductViewModel> getAllMice();

    List<ProductViewModel> getAllKeyboards();

    ProductViewModel findProductById(Long id) throws Throwable;

    boolean checkQuantityAvailability(Integer portion, Long id) throws Throwable;

    void addProductToCart(Integer portion, Long id, Object email) throws Throwable;

    MaxPriceViewModel getProductMaxPrice();
}
