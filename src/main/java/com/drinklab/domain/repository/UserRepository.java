package com.drinklab.domain.repository;

import com.drinklab.domain.model.UserEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM UserEntity u WHERE u.email = :email")
    boolean userExistsByEmail(String email);

    @Query("FROM UserEntity u join fetch u.group")
    List<UserEntity> findAll();

    Optional<UserEntity> findByEmail(String email);

}
