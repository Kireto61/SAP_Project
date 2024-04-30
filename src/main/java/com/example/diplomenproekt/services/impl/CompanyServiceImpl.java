package com.example.diplomenproekt.services.impl;

import com.example.diplomenproekt.models.bindingModel.AddCompanyBindingModel;
import com.example.diplomenproekt.models.entities.Company;
import com.example.diplomenproekt.models.entities.Product;
import com.example.diplomenproekt.models.entities.User;
import com.example.diplomenproekt.models.serviceModel.CompanyServiceModel;
import com.example.diplomenproekt.repositories.CompanyRepository;
import com.example.diplomenproekt.repositories.UserRepository;
import com.example.diplomenproekt.services.CompanyService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    public CompanyServiceImpl(CompanyRepository companyRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<CompanyServiceModel> getAllCompanies(Long userId) {
        return companyRepository.getAllByUser_Id(userId).stream()
                .map(e -> this.modelMapper.map(e, CompanyServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void addCompany(AddCompanyBindingModel addCompanyBindingModel, Object userEmail) throws Throwable {
        User user = userRepository.findByEmail(userEmail.toString()).orElseThrow(() -> new Throwable("User with email " + userEmail + " not found!"));

        Company company = this.modelMapper.map(addCompanyBindingModel, Company.class);

        company.setRegistered(addCompanyBindingModel.getRegisterBoolean().equals("registered"));
        company.setUser(user);

        ArrayList<Product> products = new ArrayList<>();
        company.setCompanies(products);

        companyRepository.save(company);
    }

    @Override
    public List<CompanyServiceModel> getAllProfileCompanies(Object email) throws Throwable {
        User user = userRepository.findByEmail(email.toString()).orElseThrow(() -> new Throwable("User with email " + email + " not found!"));
        return getAllCompanies(user.getId());
    }
}
