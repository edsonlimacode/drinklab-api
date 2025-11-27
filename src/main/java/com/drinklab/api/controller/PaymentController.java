package com.drinklab.api.controller;

import com.drinklab.api.dto.payment.PaymentRequestDto;
import com.drinklab.api.dto.payment.PaymentResponseDto;
import com.drinklab.api.mapper.PaymentMapper;
import com.drinklab.core.security.CheckAuthority;
import com.drinklab.domain.model.Payment;
import com.drinklab.domain.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentMapper mapper;

    @CheckAuthority.MasterOrAdmin
    @GetMapping
    public ResponseEntity<Page<PaymentResponseDto>> listAll(Pageable pageable) {
        Page<Payment> payments = this.paymentService.listAll(pageable);

        var paymentListDto = this.mapper.toListDto(payments.getContent());

        var paymentResponseDtos = new PageImpl<>(paymentListDto, pageable, payments.getTotalElements());
        return ResponseEntity.ok(paymentResponseDtos);
    }

    @CheckAuthority.Master
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody PaymentRequestDto paymentRequestDto) {
        Payment payment = this.mapper.toEntity(paymentRequestDto);
        this.paymentService.create(payment);
    }

    @CheckAuthority.Master
    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponseDto> update(@PathVariable Long id,@Valid @RequestBody PaymentRequestDto paymentRequestDto) {
        Payment payment = this.mapper.toEntity(paymentRequestDto);
        Payment paymentUpdated = this.paymentService.update(id, payment);

        PaymentResponseDto paymentResponseDto = this.mapper.toDto(paymentUpdated);

        return ResponseEntity.ok(paymentResponseDto);
    }

    @CheckAuthority.MasterOrAdmin
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDto> findById(@PathVariable Long id) {
        Payment payment = this.paymentService.findById(id);

        PaymentResponseDto paymentResponseDto = this.mapper.toDto(payment);

        return ResponseEntity.ok(paymentResponseDto);
    }

    @CheckAuthority.Master
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.paymentService.delete(id);
    }

}
