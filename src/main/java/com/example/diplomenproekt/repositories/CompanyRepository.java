package com.example.diplomenproekt.repositories;

import com.example.diplomenproekt.models.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> getAllByUser_Id(Long userId);

    Optional<Company> findByName(String company);
}
