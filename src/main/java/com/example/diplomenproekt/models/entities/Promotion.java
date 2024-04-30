package com.example.diplomenproekt.models.entities;

import com.example.diplomenproekt.models.entities.enums.CategoryEnum;
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
@Table(name = "promotion")
public class Promotion extends BaseEntity {

    @Column(nullable = false,columnDefinition = "double default 0")
    private Double amount;

    @Enumerated(EnumType.STRING)
    private CategoryEnum category;

    @ManyToOne
    private Company company;
}
