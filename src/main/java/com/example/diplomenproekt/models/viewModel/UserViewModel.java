package com.example.diplomenproekt.models.viewModel;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserViewModel {

    private Long id;
    private String firstName;
    private String lastName;
}
