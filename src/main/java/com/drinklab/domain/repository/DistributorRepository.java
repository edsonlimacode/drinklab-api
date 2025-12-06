package com.drinklab.domain.repository;

import com.drinklab.domain.model.Distributor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DistributorRepository extends JpaRepository<Distributor, Long> {

    @Query("FROM Distributor d join d.users u where u.id = :userId")
    Optional<Distributor> getDistributorByUserId(Long userId);

}
