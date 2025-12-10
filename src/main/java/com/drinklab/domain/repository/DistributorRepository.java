package com.drinklab.domain.repository;

import com.drinklab.domain.model.Distributor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface DistributorRepository extends JpaRepository<Distributor, Long> {

    @Query("FROM Distributor d join d.users u where d.id = :distributorId and u.id = :userId")
    Optional<Distributor> getDistributorByUserIdAndDistributorId(Long distributorId, Long userId);

    @Query("FROM Distributor d join d.users u where u.id = :userId")
    Optional<Distributor> getDistributorByUserIdAndDistributorId(Long userId);

    @Query("FROM Distributor r join r.users u where u.id = :userId")
    Page<Distributor> findAll(Long userId, Pageable pageable);

}
