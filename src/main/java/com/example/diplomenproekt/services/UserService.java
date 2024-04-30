package com.example.diplomenproekt.services;

import com.example.diplomenproekt.models.bindingModel.ProfileUpdateBindingModel;
import com.example.diplomenproekt.models.bindingModel.UserLoginBindingModel;
import com.example.diplomenproekt.models.bindingModel.UserRegisterBindingModel;
import com.example.diplomenproekt.models.serviceModel.UserLoginServiceModel;
import com.example.diplomenproekt.models.serviceModel.UserServiceModel;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserServiceModel> getAllUsers();

    Optional<UserLoginServiceModel> login(UserLoginBindingModel userModel);

    Optional<UserRegisterBindingModel> register(UserRegisterBindingModel userRegisterBindingModel);

    ProfileUpdateBindingModel getUserInfo(Object email) throws Throwable;

    void updateProfile(ProfileUpdateBindingModel profileUpdateBindingModel, Object email) throws Throwable;

    boolean checkIfPasswordExists(String oldPassword, Object object) throws Throwable;

    Double findProductsCartTotalPrice(Object email) throws Throwable;

    void confirm(Long userId);
}
