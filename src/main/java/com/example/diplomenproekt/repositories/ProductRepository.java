package com.example.diplomenproekt.repositories;

import com.example.diplomenproekt.models.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT products.id,products.category,products.name,\n" +
            "       GREATEST((price * (1 - IFNULL(promotion.amount / 100,0))),products.minprice)as price,\n" +
            "       products.quantity,products.url,products.company_id,products.minprice" +
            "       FROM products\n" +
            "        left outer join promotion on promotion.company_id = products.company_id\n" +
            "    and products.category = promotion.category"+
            "    WHERE category = ?1", nativeQuery = true)
    List<Product> getAllBy(String type);


    @Query(value = "SELECT products.id,products.category,products.name,\n" +
            "       GREATEST((price * (1 - IFNULL(promotion.amount / 100,0))),products.minprice)as price,\n" +
            "       products.quantity,products.url,products.company_id,products.minprice" +
            "       FROM products\n" +
            "        left outer join promotion on promotion.company_id = products.company_id\n" +
            "    and products.category = promotion.category"+
            "    WHERE products.id = ?1", nativeQuery = true)
    Optional<Product> gettById(Long id);


    @Query(value = "SELECT products.id,products.category,products.name,\n" +
            "       GREATEST((price * (1 - IFNULL(promotion.amount / 100,0))),products.minprice)as price,\n" +
            "       products.quantity,products.url,products.company_id,products.minprice" +
            "       FROM products\n" +
            "        left outer join promotion on promotion.company_id = products.company_id\n" +
            "    and products.category = promotion.category", nativeQuery = true)
    List<Product> getAll();
}
