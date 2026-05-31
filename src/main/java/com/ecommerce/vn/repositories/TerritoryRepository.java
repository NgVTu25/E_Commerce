package com.ecommerce.vn.repositories;

import com.ecommerce.vn.models.entitis.Territories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TerritoryRepository extends JpaRepository<Territories, String> {

    @Query("SELECT t FROM Territories t LEFT JOIN FETCH t.region")
    List<Territories> findAllWithRegion();

    @Query("SELECT t FROM Territories t LEFT JOIN FETCH t.region WHERE t.id = :id")
    Optional<Territories> findByIdWithRegion(String id);
}
