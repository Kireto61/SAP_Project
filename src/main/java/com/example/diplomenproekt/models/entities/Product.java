package com.example.diplomenproekt.models.entities;

import com.example.diplomenproekt.models.entities.enums.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Min(0)
//    @Max(99999)
    private Double price;

    @Column(nullable = false)
    @Min(0)
//    @Max(99999)
    private Double minprice;

    @Column(nullable = false)
    @Min(0)
    private Integer quantity;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryEnum category;

    @ManyToOne
    private Company company;
}
