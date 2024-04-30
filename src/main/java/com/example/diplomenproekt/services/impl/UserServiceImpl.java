package com.example.diplomenproekt.services.impl;

import com.example.diplomenproekt.models.bindingModel.ProfileUpdateBindingModel;
import com.example.diplomenproekt.models.bindingModel.UserLoginBindingModel;
import com.example.diplomenproekt.models.bindingModel.UserRegisterBindingModel;
import com.example.diplomenproekt.models.entities.User;
import com.example.diplomenproekt.models.serviceModel.UserLoginServiceModel;
import com.example.diplomenproekt.models.serviceModel.UserServiceModel;
import com.example.diplomenproekt.repositories.UserRepository;
import com.example.diplomenproekt.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender emailSender;


    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, JavaMailSender emailSender) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.emailSender = emailSender;
    }


    @Override
    public List<UserServiceModel> getAllUsers() {
        return userRepository.findAll().stream()
                .map(e -> this.modelMapper.map(e, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserLoginServiceModel> login(UserLoginBindingModel userModel) {
        Optional<User> byEmail = this.userRepository.getByEmail(userModel.getEmail());

        if (byEmail.isPresent()) {

            if (!byEmail.get().isConfirmed()) {
                return Optional.empty();
            }

            if (!passwordEncoder.matches(userModel.getPassword(), byEmail.get().getPassword())) {
                return Optional.empty();
            }
        }

        return
                byEmail.map(e -> this.modelMapper.map(e, UserLoginServiceModel.class));
    }

    @Override
    public Optional<UserRegisterBindingModel> register(UserRegisterBindingModel userRegisterBindingModel) {
        if (this.userRepository.findByEmail(userRegisterBindingModel.getEmail()).isPresent()) {
            return Optional.empty();
        }
        User user = new User();
        user.setCompanies(new ArrayList<>());
        user.setProductsCart(new ArrayList<>());
        user.setHistoryProducts(new ArrayList<>());
        user.setFirstName(userRegisterBindingModel.getFirstName());
        user.setLastName(userRegisterBindingModel.getLastName());
        user.setEmail(userRegisterBindingModel.getEmail());
        user.setConfirmed(false);
        user.setPassword(passwordEncoder.encode(userRegisterBindingModel.getPassword()));
        user = this.userRepository.saveAndFlush(user);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("kireto.office@gmail.com");
        message.setTo(userRegisterBindingModel.getEmail());
        message.setSubject("Email Confirmation");

        message.setText("http://localhost:8080/users/" + user.getId() + "/confirm-email");
        this.emailSender.send(message);

        return Optional.of(user).map(e -> this.modelMapper.map(e, UserRegisterBindingModel.class));
    }

    @Override
    public ProfileUpdateBindingModel getUserInfo(Object email) throws Throwable {
        return userRepository
                .findByEmail(email.toString())
                .map(e -> modelMapper.map(e, ProfileUpdateBindingModel.class))
                .orElseThrow(() -> new Throwable("User with email " + email + " not found!"));
    }

    @Override
    public void updateProfile(ProfileUpdateBindingModel profileUpdateBindingModel, Object email) throws Throwable {
        User user = userRepository.findByEmail(email.toString()).orElseThrow(() -> new Throwable("User with email " + email + " not found!"));

        user.setFirstName(profileUpdateBindingModel.getFirstName());
        user.setLastName(profileUpdateBindingModel.getLastName());
        user.setPassword(passwordEncoder.encode(profileUpdateBindingModel.getNewPassword()));
        user.setEmail(profileUpdateBindingModel.getEmail());

        this.userRepository.save(user);
    }

    @Override
    public boolean checkIfPasswordExists(String oldPassword, Object email) throws Throwable {
        User user = userRepository.findByEmail(email.toString()).orElseThrow(() -> new Throwable("User with email " + email + " not found!"));

        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    @Override
    public Double findProductsCartTotalPrice(Object email) throws Throwable {
        User user = userRepository.findByEmail(email.toString()).orElseThrow(() -> new Throwable("User with email " + email + " not found!"));

        return user.getProductsCart()
                .stream()
                .mapToDouble(value -> value.getProduct().getPrice() * value.getWishedQuantityForOrder())
                .sum();
    }

    @Override
    public void confirm(Long userId) {
        Optional<User> byId = userRepository.findById(userId);
        byId.get().setConfirmed(true);

        userRepository.saveAndFlush(byId.get());
    }
}
