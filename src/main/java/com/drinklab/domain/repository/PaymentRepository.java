package com.drinklab.domain.repository;

import com.drinklab.domain.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> { }
