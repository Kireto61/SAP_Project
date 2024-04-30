package com.example.diplomenproekt.models.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "company")
public class Company extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String mol;

    @Column(nullable = false, unique = true)
    private Integer vat;

    @Column(nullable = false)
    private String address;

    @Column(name = "is_registered", nullable = false)
    private boolean isRegistered;

    @ManyToOne
    private User user;

    @Column(nullable = false, columnDefinition = "double default 0" )
    private Double income;

    @OneToMany(mappedBy = "company")
    private List<Promotion> promotion;

    @OneToMany(mappedBy = "company")
    private List<Product> companies;
}
