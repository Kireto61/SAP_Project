package com.example.diplomenproekt.repositories;

import com.example.diplomenproekt.models.entities.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Long> {

    void deleteAllByUser_Id(Long userId);

    List<ShoppingCartEntity> findAllByUser_Id(Long userId);
}
