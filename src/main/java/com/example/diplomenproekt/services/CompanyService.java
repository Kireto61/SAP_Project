package com.example.diplomenproekt.services;

import com.example.diplomenproekt.models.bindingModel.AddCompanyBindingModel;
import com.example.diplomenproekt.models.serviceModel.CompanyServiceModel;

import java.util.List;

public interface CompanyService {
    List<CompanyServiceModel> getAllCompanies(Long userId);

    void addCompany(AddCompanyBindingModel addCompanyBindingModel, Object userEmail) throws Throwable;

    List<CompanyServiceModel> getAllProfileCompanies(Object email) throws Throwable;
}
