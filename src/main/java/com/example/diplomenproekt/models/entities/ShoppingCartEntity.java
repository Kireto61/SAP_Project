package com.example.diplomenproekt.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shopping_cart")
public class ShoppingCartEntity extends BaseEntity{

    @Column(nullable = false)
    private Integer wishedQuantityForOrder;

    @OneToOne
    private Product product;

    @ManyToOne
    private User user;

}
