package com.example.diplomenproekt.web;

import com.example.diplomenproekt.models.viewModel.MaxPriceViewModel;
import com.example.diplomenproekt.services.ProductsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MaxPriceRestController {

    private final ProductsService productsService;

    public MaxPriceRestController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("/api/products/max-price")
    public ResponseEntity<MaxPriceViewModel> getMaxPrice(){
        return ResponseEntity.ok(productsService.getProductMaxPrice());
    }
}
