package com.example.diplomenproekt.services.impl;

import com.example.diplomenproekt.models.bindingModel.ProfileUpdateBindingModel;
import com.example.diplomenproekt.models.bindingModel.UserLoginBindingModel;
import com.example.diplomenproekt.models.bindingModel.UserRegisterBindingModel;
import com.example.diplomenproekt.models.entities.User;
import com.example.diplomenproekt.models.serviceModel.UserLoginServiceModel;
import com.example.diplomenproekt.models.serviceModel.UserServiceModel;
import com.example.diplomenproekt.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JavaMailSender emailSender;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userService = new UserServiceImpl(userRepository, modelMapper, passwordEncoder, emailSender);
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = new ArrayList<>();
        // Add some test data to the list
        users.add(new User());
        users.add(new User());
        when(userRepository.findAll()).thenReturn(users);

        List<UserServiceModel> result = userService.getAllUsers();
        assertEquals(2, result.size());
    }

    @Test
    public void testLogin() {
        UserLoginBindingModel userLoginBindingModel = new UserLoginBindingModel();
        // Set user login details
        userLoginBindingModel.setEmail("test@example.com");
        userLoginBindingModel.setPassword("password");

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        when(userRepository.getByEmail(userLoginBindingModel.getEmail())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(userLoginBindingModel.getPassword(), user.getPassword())).thenReturn(true);

        Optional<UserLoginServiceModel> result = userService.login(userLoginBindingModel);
        assertTrue(result.isPresent());
        assertEquals(user.getEmail(), result.get().getEmail());
    }

    @Test
    public void testRegister() {
        UserRegisterBindingModel userRegisterBindingModel = new UserRegisterBindingModel();
        // Set user registration details
        userRegisterBindingModel.setFirstName("John");
        userRegisterBindingModel.setLastName("Doe");
        userRegisterBindingModel.setEmail("test@example.com");
        userRegisterBindingModel.setPassword("password");

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(userRepository.findByEmail(userRegisterBindingModel.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(userRegisterBindingModel.getPassword())).thenReturn("encodedPassword");
        when(userRepository.saveAndFlush(any(User.class))).thenReturn(user);

        Optional<UserRegisterBindingModel> result = userService.register(userRegisterBindingModel);
        assertTrue(result.isPresent());
        assertEquals(userRegisterBindingModel.getFirstName(), result.get().getFirstName());
        assertEquals(userRegisterBindingModel.getLastName(), result.get().getLastName());
        assertEquals(userRegisterBindingModel.getEmail(), result.get().getEmail());
    }

}
