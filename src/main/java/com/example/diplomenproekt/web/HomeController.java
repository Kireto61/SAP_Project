package com.example.diplomenproekt.web;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public String getHome(HttpSession session, Model model) {
        Object email = session.getAttribute("email");
        if (email == null) {
            return "index";
        }
        model.addAttribute("email", email);
        return "redirect:/products/catalog/all";
    }
}
