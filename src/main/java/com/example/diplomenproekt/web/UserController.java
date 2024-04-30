package com.example.diplomenproekt.web;

import com.example.diplomenproekt.models.bindingModel.ProfileUpdateBindingModel;
import com.example.diplomenproekt.models.bindingModel.UserLoginBindingModel;
import com.example.diplomenproekt.models.bindingModel.UserRegisterBindingModel;
import com.example.diplomenproekt.models.serviceModel.CompanyServiceModel;
import com.example.diplomenproekt.models.serviceModel.UserLoginServiceModel;
import com.example.diplomenproekt.models.serviceModel.UserServiceModel;
import com.example.diplomenproekt.models.viewModel.CompanyViewModel;
import com.example.diplomenproekt.models.viewModel.UserViewModel;
import com.example.diplomenproekt.services.CompanyService;
import com.example.diplomenproekt.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final CompanyService companyService;
    private final ModelMapper modelMapper;
    private final HttpSession httpSession;

    public UserController(UserService userService, CompanyService companyService, ModelMapper modelMapper, HttpSession httpSession) {
        this.userService = userService;
        this.companyService = companyService;
        this.modelMapper = modelMapper;
        this.httpSession = httpSession;
    }

    @GetMapping("/all")
    public String getUsers(Model model) {

        if (!model.containsAttribute("email")) {
            model.addAttribute("email", httpSession.getAttribute("email"));
        }

        if (httpSession.getAttribute("email") == null) {
            return "redirect:/";
        }


        List<UserServiceModel> users = userService.getAllUsers();
        model.addAttribute("users",
                users.stream()
                        .map(e -> this.modelMapper.map(e, UserViewModel.class))
                        .collect(Collectors.toList())
        );
        return "users";
    }

    @GetMapping("/{userId}/confirm-email")
    public String confirmEmail(@PathVariable("userId") Long userId, Model model) {

        userService.confirm(userId);

        return "login";
    }

    @GetMapping("/{userId}/companies")
    public String getCompany(@PathVariable("userId") Long userId, Model model) {

        if (!model.containsAttribute("email")) {
            model.addAttribute("email", httpSession.getAttribute("email"));
        }

        if (httpSession.getAttribute("email") == null) {
            return "redirect:/";
        }
        List<CompanyServiceModel> users = companyService.getAllCompanies(userId);
        model.addAttribute("companies",
                users.stream()
                        .map(e -> this.modelMapper.map(e, CompanyViewModel.class))
                        .collect(Collectors.toList())
        );
        return "companies";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute UserLoginBindingModel userModel,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {

        Optional<UserLoginServiceModel> user = userService.login(userModel);

        if (!user.isPresent()) {
            String errors = "Invalid username or password.";
            redirectAttributes.addFlashAttribute("emailError", userModel.getEmail());
            redirectAttributes.addFlashAttribute("errors", errors);
            return "redirect:/users/login";
        } else {
            session.setAttribute("email", user.get().getEmail());
            redirectAttributes.addFlashAttribute("email", userModel.getEmail());
        }

        return "redirect:/users/all";
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        if (!model.containsAttribute("email")) {
            model.addAttribute("email", httpSession.getAttribute("email"));
        }

        if (httpSession.getAttribute("email") != null) {
            return "redirect:/";
        }

        return "login";
    }

    @GetMapping("/register")
    public String getRegister(@ModelAttribute("user") UserRegisterBindingModel userRegisterBindingModel) {

        if (httpSession.getAttribute("email") != null) {
            return "redirect:/";
        }

        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@Valid @ModelAttribute("user") UserRegisterBindingModel userRegisterBindingModel,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "/register";
        }

        Optional<UserRegisterBindingModel> user = this.userService.register(userRegisterBindingModel);

        if (user.isPresent()) {
            return "redirect:/users/login";
        } else {
            redirectAttributes.addFlashAttribute("ubm", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("error", "This email already exist");
            return "redirect:/users/register";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String profile(Model model) throws Throwable {

        if (httpSession.getAttribute("email") == null) {
            return "redirect:/";
        }

        if (!model.containsAttribute("email")) {
            model.addAttribute("email", httpSession.getAttribute("email"));
        }

        if (!model.containsAttribute("companies")) {
            model.addAttribute("companies", companyService.getAllProfileCompanies(httpSession.getAttribute("email")));
        }

        if (!model.containsAttribute("info")) {
            model.addAttribute("profileUpdateBindingModel", userService.getUserInfo(httpSession.getAttribute("email")));
        }

        if (!model.containsAttribute("oldPassNewMatchFalse")) {
            model.addAttribute("oldPassNewMatchFalse", false);
        }

        if (!model.containsAttribute("matchPassesFalse")) {
            model.addAttribute("matchPassesFalse", false);
        }

        return "profile";
    }

    @GetMapping("/profile/errors")
    public String profileErrors(Model model) throws Throwable {

        if (httpSession.getAttribute("email") == null) {
            return "redirect:/";
        }

        if (!model.containsAttribute("email")) {
            model.addAttribute("email", httpSession.getAttribute("email"));
        }

        if (!model.containsAttribute("companies")) {
            model.addAttribute("companies", companyService.getAllProfileCompanies(httpSession.getAttribute("email")));
        }

        return "profile";
    }

    @PostMapping("/profile")
    public String confirmProfile(@Valid ProfileUpdateBindingModel profileUpdateBindingModel,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) throws Throwable {

        if (httpSession.getAttribute("email") == null) {
            return "redirect:/";
        }

        if(!userService.checkIfPasswordExists(profileUpdateBindingModel.getOldPassword(), httpSession.getAttribute("email"))){
            redirectAttributes.addFlashAttribute("profileUpdateBindingModel", profileUpdateBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.profileUpdateBindingModel",bindingResult);
            redirectAttributes.addFlashAttribute("oldPassNewMatchFalse", true);

            return "redirect:/users/profile/errors";
        }

        if(bindingResult.hasErrors() || !profileUpdateBindingModel.getNewPassword().equals(profileUpdateBindingModel.getConfirmPassword())){
            redirectAttributes.addFlashAttribute("profileUpdateBindingModel", profileUpdateBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.profileUpdateBindingModel",bindingResult);

            if(!profileUpdateBindingModel.getNewPassword().equals(profileUpdateBindingModel.getConfirmPassword())){
                redirectAttributes.addFlashAttribute("matchPassesFalse", true);
            }

            return "redirect:/users/profile/errors";
        }

        userService.updateProfile(profileUpdateBindingModel, httpSession.getAttribute("email"));

        return "redirect:/users/profile";
    }

    @ModelAttribute
    public ProfileUpdateBindingModel profileUpdateBindingModel(){
        return new ProfileUpdateBindingModel();
    }

}
