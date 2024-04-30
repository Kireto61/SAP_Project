package com.example.diplomenproekt.web;

import com.example.diplomenproekt.models.bindingModel.AddToCartBindingModel;
import com.example.diplomenproekt.models.bindingModel.CheckoutBindingModel;
import com.example.diplomenproekt.models.bindingModel.ProductAddBindingModel;
import com.example.diplomenproekt.services.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductsService productsService;
    private final ModelMapper modelMapper;
    private final HttpSession httpSession;
    private final CompanyService companyService;
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;
    private final HistoryService historyService;

    public ProductController(ProductsService productsService, ModelMapper modelMapper, HttpSession httpSession, CompanyService companyService, ShoppingCartService shoppingCartService, UserService userService, HistoryService historyService) {
        this.productsService = productsService;
        this.modelMapper = modelMapper;
        this.httpSession = httpSession;
        this.companyService = companyService;
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
        this.historyService = historyService;
    }

    @GetMapping("/add-product")
    public String addProduct(Model model) throws Throwable {

        if (httpSession.getAttribute("email") == null) {
            return "redirect:/";
        }

        if (!model.containsAttribute("email")) {
            model.addAttribute("email", httpSession.getAttribute("email"));
        }

        model.addAttribute("companies", companyService.getAllProfileCompanies(httpSession.getAttribute("email")));

        return "add-product";
    }

    @PostMapping("/add-product")
    public String addConfirm(@Valid ProductAddBindingModel productAddBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws Throwable {

        if (httpSession.getAttribute("email") == null) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("productAddBindingModel", productAddBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.productAddBindingModel", bindingResult);

            return "redirect:/products/add-product";
        }

        productsService.addProduct(productAddBindingModel, httpSession.getAttribute("email"));

        return "redirect:/";
    }

    @GetMapping("/catalog/{category}")
    public String catalog(Model model, @PathVariable String category) {

        if (httpSession.getAttribute("email") == null) {
            return "redirect:/";
        }

        if (!model.containsAttribute("email")) {
            model.addAttribute("email", httpSession.getAttribute("email"));
        }

        switch (category) {
            case "all":
                model.addAttribute("products", productsService.getAllProducts());
                break;
            case "videocards":
                model.addAttribute("products", productsService.getAllVideocards());
                break;
            case "processors":
                model.addAttribute("products", productsService.getAllProcessors());
                break;
            case "headphones":
                model.addAttribute("products", productsService.getAllHeadphones());
                break;
            case "printers":
                model.addAttribute("products", productsService.getAllPrinters());
                break;
            case "office_chairs":
                model.addAttribute("products", productsService.getAllOfficeChairs());
                break;
            case "scanners":
                model.addAttribute("products", productsService.getAllScanners());
                break;
            case "routers":
                model.addAttribute("products", productsService.getAllRouters());
                break;
            case "mice":
                model.addAttribute("products", productsService.getAllMice());
                break;
            case "keyboards":
                model.addAttribute("products", productsService.getAllKeyboards());
                break;
        }

        return "products-catalog";
    }

    @GetMapping("/{id}/details")
    public String details(@PathVariable Long id, Model model) throws Throwable {

        if (httpSession.getAttribute("email") == null) {
            return "redirect:/";
        }

        if (!model.containsAttribute("email")) {
            model.addAttribute("email", httpSession.getAttribute("email"));
        }

        if (!model.containsAttribute("canBuy")) {
            model.addAttribute("canBuy", true);
        }

        model.addAttribute("product", productsService.findProductById(id));

        return "details";
    }

    @PostMapping("/{id}/details")
    public String confirmDetails(@PathVariable Long id, AddToCartBindingModel addToCartBindingModel, RedirectAttributes redirectAttributes) throws Throwable {

        boolean canBuy = productsService.checkQuantityAvailability(addToCartBindingModel.getPortion(), id);

        if (!canBuy || addToCartBindingModel.getPortion() <= 0) {

            redirectAttributes.addFlashAttribute("addToCartBindingModel", addToCartBindingModel);
            redirectAttributes.addFlashAttribute("canBuy", false);

            return "redirect:/products/" + id + "/details";
        }

        productsService.addProductToCart(addToCartBindingModel.getPortion(), id, httpSession.getAttribute("email"));

        return "redirect:/products/shopping-cart";
    }

    @GetMapping("/shopping-cart")
    public String checkout(Model model) throws Throwable {

        if (httpSession.getAttribute("email") == null) {
            return "redirect:/";
        }

        if (!model.containsAttribute("email")) {
            model.addAttribute("email", httpSession.getAttribute("email"));
        }

        model.addAttribute("products", shoppingCartService.findAllShoppingCartEntities());
        model.addAttribute("productsCount", shoppingCartService.findAllShoppingCartEntities().size());
        model.addAttribute("totalPrice", userService.findProductsCartTotalPrice(httpSession.getAttribute("email")));
        model.addAttribute("totalPriceDelivery", userService.findProductsCartTotalPrice(httpSession.getAttribute("email")) + 5);

        return "shopping-cart";
    }

    @PostMapping("/shopping-cart")
    public String checkoutConfirm(@Valid CheckoutBindingModel checkoutBindingModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws Throwable {

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("checkoutBindingModel", checkoutBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.checkoutBindingModel", bindingResult);

            return "redirect:/products/shopping-cart";
        }

        historyService.secureCheckout(checkoutBindingModel.getAddress(), httpSession.getAttribute("email"), checkoutBindingModel.getTotalPrice());

        return "redirect:/products/order-details";
    }

    @GetMapping("/order-details")
    public String orderDetails(Model model) {

        if (httpSession.getAttribute("email") == null) {
            return "redirect:/";
        }

        if (!model.containsAttribute("email")) {
            model.addAttribute("email", httpSession.getAttribute("email"));
        }

        return "delivery";
    }

    @GetMapping("/my-history")
    public String history(Model model) throws Throwable {

        if (httpSession.getAttribute("email") == null) {
            return "redirect:/";
        }

        if (!model.containsAttribute("email")) {
            model.addAttribute("email", httpSession.getAttribute("email"));
        }

        model.addAttribute("historyProducts", historyService.findAllHistoryProducts(httpSession.getAttribute("email")));

        return "history";
    }

    @PostMapping("/my-history")
    public String clearHistory() throws Throwable {
        historyService.clearHistory(httpSession.getAttribute("email"));
        return "redirect:/products/my-history";
    }

    @ModelAttribute
    public CheckoutBindingModel checkoutBindingModel() {
        return new CheckoutBindingModel();
    }

    @ModelAttribute
    public ProductAddBindingModel productAddBindingModel() {
        return new ProductAddBindingModel();
    }

    @ModelAttribute
    public AddToCartBindingModel addToCartBindingModel() {
        return new AddToCartBindingModel();
    }
}
