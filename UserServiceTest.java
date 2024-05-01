package com.example.diplomenproekt.services;

import com.example.diplomenproekt.models.bindingModel.ProfileUpdateBindingModel;
import com.example.diplomenproekt.models.bindingModel.UserLoginBindingModel;
import com.example.diplomenproekt.models.bindingModel.UserRegisterBindingModel;
import com.example.diplomenproekt.models.serviceModel.UserLoginServiceModel;
import com.example.diplomenproekt.models.serviceModel.UserServiceModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Mock
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        List<UserServiceModel> users = new ArrayList<>();
        // Add some test data to the list
        users.add(new UserServiceModel());
        users.add(new UserServiceModel());
        when(userService.getAllUsers()).thenReturn(users);

        List<UserServiceModel> result = userService.getAllUsers();
        assertEquals(2, result.size());
    }

    @Test
    public void testLogin() {
        UserLoginBindingModel userLoginBindingModel = new UserLoginBindingModel();
        // Set user login details
        userLoginBindingModel.setUsername("test");
        userLoginBindingModel.setPassword("password");

        UserLoginServiceModel userLoginServiceModel = new UserLoginServiceModel();
        // Set user login service model
        userLoginServiceModel.setUsername("test");

        when(userService.login(userLoginBindingModel)).thenReturn(Optional.of(userLoginServiceModel));

        Optional<UserLoginServiceModel> result = userService.login(userLoginBindingModel);
        assertEquals(userLoginServiceModel.getUsername(), result.get().getUsername());
    }

    @Test
    public void testRegister() {
        UserRegisterBindingModel userRegisterBindingModel = new UserRegisterBindingModel();
        // Set user registration details
        userRegisterBindingModel.setUsername("test");
        userRegisterBindingModel.setEmail("test@example.com");
        userRegisterBindingModel.setPassword("password");

        when(userService.register(userRegisterBindingModel)).thenReturn(Optional.of(userRegisterBindingModel));

        Optional<UserRegisterBindingModel> result = userService.register(userRegisterBindingModel);
        assertEquals(userRegisterBindingModel.getUsername(), result.get().getUsername());
    }


}
