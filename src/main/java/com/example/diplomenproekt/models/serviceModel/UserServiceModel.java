package com.example.diplomenproekt.models.serviceModel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserServiceModel {

    private Long id;
    private String firstName;
    private String lastName;
}
