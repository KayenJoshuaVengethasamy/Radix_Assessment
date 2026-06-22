/**
 * PaymentRequest is a Data Transfer Object (DTO) used to capture and validate incoming payment data from API clients.
 * It ensures that all required fields are present and that the payment amount is a valid positive value before the
 * request reaches the service layer.
 *
 * @author Kayen Joshua Vengethasamy
 * @since 2026-06-19
 */

package com.radix.assessment.payments.model.DTO.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {

    @NotNull
    private Long loanId;

    @NotNull(message = "paymentAmount is required")
    @Positive(message = "paymentAmount must be greater than zero")
    private BigDecimal paymentAmount;

}
