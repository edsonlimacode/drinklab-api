package com.drinklab.api.mapper;


import com.drinklab.api.dto.payment.PaymentRequestDto;
import com.drinklab.api.dto.payment.PaymentResponseDto;
import com.drinklab.domain.model.Payment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    Payment toEntity(PaymentRequestDto categoryRequestDto);

    PaymentResponseDto toDto(Payment category);

    List<PaymentResponseDto> toListDto(List<Payment> category);

}
