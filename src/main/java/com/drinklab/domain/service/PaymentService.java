package com.drinklab.domain.service;


import com.drinklab.api.exceptions.customExceptions.NotFoundException;
import com.drinklab.domain.model.Payment;
import com.drinklab.domain.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Page<Payment> listAll(Pageable pageable) {
        return this.paymentRepository.findAll(pageable);
    }

    public void create(Payment payment) {
        this.paymentRepository.save(payment);
    }

    public Payment update(Long id, Payment payment) {

        payment.setId(id);

        this.findById(id);

        return this.paymentRepository.save(payment);
    }

    public Payment findById(Long id) {
        return this.paymentRepository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Recurso de id: %d, não foi encontrado", id)));
    }

    public void delete(Long id) {
        Payment payment = this.findById(id);
        this.paymentRepository.delete(payment);
    }
}
