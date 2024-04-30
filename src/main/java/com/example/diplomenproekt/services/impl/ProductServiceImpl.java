package com.example.diplomenproekt.services.impl;

import com.example.diplomenproekt.models.bindingModel.ProductAddBindingModel;
import com.example.diplomenproekt.models.entities.Company;
import com.example.diplomenproekt.models.entities.Product;
import com.example.diplomenproekt.models.entities.ShoppingCartEntity;
import com.example.diplomenproekt.models.entities.User;
import com.example.diplomenproekt.models.entities.enums.CategoryEnum;
import com.example.diplomenproekt.models.viewModel.MaxPriceViewModel;
import com.example.diplomenproekt.models.viewModel.ProductViewModel;
import com.example.diplomenproekt.repositories.CompanyRepository;
import com.example.diplomenproekt.repositories.ProductRepository;
import com.example.diplomenproekt.repositories.UserRepository;
import com.example.diplomenproekt.services.ProductsService;
import com.example.diplomenproekt.services.ShoppingCartService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductsService {


    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final ShoppingCartService shoppingCartService;

    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper,
                              CompanyRepository companyRepository, UserRepository userRepository,
                              ShoppingCartService shoppingCartService) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    public void addProduct(ProductAddBindingModel productAddBindingModel, Object email) throws Throwable {
        Company company = companyRepository.findByName(productAddBindingModel.getCompany()).orElseThrow(()
                -> new Throwable("Company with name " + productAddBindingModel.getCompany() + " not found!"));

        Product product = new Product();
        product.setUrl(productAddBindingModel.getUrl());
        product.setCategory(CategoryEnum.valueOf(productAddBindingModel.getCategory()));
        product.setCompany(company);
        product.setPrice(productAddBindingModel.getPrice());
        product.setName(productAddBindingModel.getName());
        product.setQuantity(productAddBindingModel.getQuantity());
        product.setMinprice(productAddBindingModel.getMinPrice());
        productRepository.save(product);
    }

    @Override
    public List<ProductViewModel> getAllProducts() {
        return productRepository
                .getAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductViewModel> getAllVideocards() {
        return productRepository
                .getAllBy(CategoryEnum.VIDEOCARDS.name())
                .stream()
                .filter(product -> product.getCategory().equals(CategoryEnum.VIDEOCARDS))
                .map(product -> modelMapper.map(product, ProductViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductViewModel> getAllProcessors() {
        return productRepository
                .getAllBy(CategoryEnum.PROCESSORS.name())
                .stream()
                .filter(product -> product.getCategory().equals(CategoryEnum.PROCESSORS))
                .map(product -> modelMapper.map(product, ProductViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductViewModel> getAllHeadphones() {
        return productRepository
                .getAllBy(CategoryEnum.HEADPHONES.name())
                .stream()
                .filter(product -> product.getCategory().equals(CategoryEnum.HEADPHONES))
                .map(product -> modelMapper.map(product, ProductViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductViewModel> getAllPrinters() {
        return productRepository
                .getAllBy(CategoryEnum.PRINTERS.name())
                .stream()
                .filter(product -> product.getCategory().equals(CategoryEnum.PRINTERS))
                .map(product -> modelMapper.map(product, ProductViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductViewModel> getAllOfficeChairs() {
        return productRepository
                .getAllBy(CategoryEnum.OFFICE_CHAIRS.name())
                .stream()
                .filter(product -> product.getCategory().equals(CategoryEnum.OFFICE_CHAIRS))
                .map(product -> modelMapper.map(product, ProductViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductViewModel> getAllScanners() {
        return productRepository
                .getAllBy(CategoryEnum.SCANNERS.name())
                .stream()
                .filter(product -> product.getCategory().equals(CategoryEnum.SCANNERS))
                .map(product -> modelMapper.map(product, ProductViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductViewModel> getAllRouters() {
        return productRepository
                .getAllBy(CategoryEnum.ROUTERS.name())
                .stream()
                .filter(product -> product.getCategory().equals(CategoryEnum.ROUTERS))
                .map(product -> modelMapper.map(product, ProductViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductViewModel> getAllMice() {
        return productRepository
                .getAllBy(CategoryEnum.MICE.name())
                .stream()
                .filter(product -> product.getCategory().equals(CategoryEnum.MICE))
                .map(product -> modelMapper.map(product, ProductViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductViewModel> getAllKeyboards() {
        return productRepository
                .getAllBy(CategoryEnum.KEYBOARDS.name())
                .stream()
                .filter(product -> product.getCategory().equals(CategoryEnum.KEYBOARDS))
                .map(product -> modelMapper.map(product, ProductViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductViewModel findProductById(Long id) throws Throwable {
        return productRepository
                .gettById(id)
                .map(product -> modelMapper.map(product, ProductViewModel.class))
                .orElseThrow(() -> new Throwable("Product with id " + id + " not found!"));
    }

    @Override
    public boolean checkQuantityAvailability(Integer portion, Long id) throws Throwable {
        Integer productQuantity = productRepository.gettById(id).map(Product::getQuantity).orElseThrow(() -> new Throwable("Product with id " + id + " not found!"));
        return portion <= productQuantity;
    }

    @Override
    public void addProductToCart(Integer portion, Long id, Object email) throws Throwable {
        User user = userRepository.findByEmail(email.toString()).orElseThrow(() -> new Throwable("User with email " + email + " not found!"));
        Product product = productRepository.gettById(id).orElseThrow(() -> new Throwable("Product with id " + id + " not found!"));

        product.setQuantity(product.getQuantity() - portion);

        ShoppingCartEntity existingShoppingCart = findIfProductIsAlreadyInTheCart(product);

        if(existingShoppingCart != null){
            existingShoppingCart.setWishedQuantityForOrder(existingShoppingCart.getWishedQuantityForOrder() + portion);
            shoppingCartService.saveShoppingCart(existingShoppingCart);
        }else{
            ShoppingCartEntity shoppingCartEntity = new ShoppingCartEntity();
            shoppingCartEntity.setUser(user);
            shoppingCartEntity.setProduct(product);
            shoppingCartEntity.setWishedQuantityForOrder(portion);

            shoppingCartService.addShoppingCartEntity(shoppingCartEntity);
            Company company = companyRepository.findById(product.getCompany().getId()).get();
            company.setIncome(company.getIncome()+product.getPrice() * portion);
            companyRepository.save(company);

            user.getProductsCart().add(shoppingCartEntity);
            userRepository.save(user);
        }

        if(product.getQuantity() <= 0){
            product.setQuantity(0);
        }

        productRepository.save(product);
    }

    @Override
    public MaxPriceViewModel getProductMaxPrice() {
        List<Product> all = productRepository.findAll();
        Product product = all.stream().max(Comparator.comparing(Product::getPrice)).get();

        MaxPriceViewModel maxPriceViewModel = new MaxPriceViewModel();
        maxPriceViewModel.setPrice(product.getPrice());

        return maxPriceViewModel;
    }

    private ShoppingCartEntity findIfProductIsAlreadyInTheCart(Product product) {

        for (ShoppingCartEntity shoppingCartEntity :
                shoppingCartService.findAllShoppingCartEntities()) {

            if(shoppingCartEntity.getProduct().getName().equals(product.getName())){
                return shoppingCartEntity;
            }
        }

        return null;
    }
}
