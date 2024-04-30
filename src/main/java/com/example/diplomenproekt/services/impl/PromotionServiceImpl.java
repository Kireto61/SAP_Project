package com.example.diplomenproekt.services.impl;

import com.example.diplomenproekt.models.bindingModel.PromotionAddBindingModel;
import com.example.diplomenproekt.models.entities.Company;
import com.example.diplomenproekt.models.entities.Promotion;
import com.example.diplomenproekt.models.entities.enums.CategoryEnum;
import com.example.diplomenproekt.repositories.CompanyRepository;
import com.example.diplomenproekt.repositories.PromotionRepository;
import com.example.diplomenproekt.services.PromotionService;
import org.springframework.stereotype.Service;

@Service
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;
    private final CompanyRepository companyRepository;

    public PromotionServiceImpl(PromotionRepository promotionRepository, CompanyRepository companyRepository) {
        this.promotionRepository = promotionRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public void addPromotion(PromotionAddBindingModel promotionAddBindingModel) throws Throwable {
        Company company = companyRepository.findByName(promotionAddBindingModel.getCompany()).orElseThrow(()
                -> new Throwable("Company with name " + promotionAddBindingModel.getCompany() + " not found!"));

        Promotion promotion = new Promotion();

        promotion.setAmount(promotionAddBindingModel.getPromotion());
        promotion.setCategory(CategoryEnum.valueOf(promotionAddBindingModel.getCategory()));
        promotion.setCompany(company);
        promotionRepository.save(promotion);
    }
}
