package com.radix.assessment.payments.model.DTO.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {

    @NotNull(message = "loanId is required")
    private Long loanId;

    @NotNull(message = "paymentAmount is required")
    @Positive(message = "paymentAmount must be greater than zero")
    private BigDecimal paymentAmount;
}
