package com.example.diplomenproekt.repositories;

import com.example.diplomenproekt.models.entities.HistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository <HistoryEntity, Long> {
    List<HistoryEntity> findAllByUser_Id(Long id);

    void deleteAllByUser_Id(Long userId);
}
