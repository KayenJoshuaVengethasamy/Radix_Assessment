/**
 * PaymentResponse is a Data Transfer Object (DTO) used to return payment details to API clients after a payment
 * has been successfully processed. It provides a structured summary of the payment transaction and the updated
 * state of the associated loan.
 *
 * @author Kayen Joshua Vengethasamy
 * @since 2026-06-19
 */
package com.radix.assessment.payments.model.DTO.response;

import com.radix.assessment.common.constants.loans.LoanStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class PaymentResponse {

    private Long paymentId;
    private Long loanId;
    private BigDecimal paymentAmount;
    private BigDecimal remainingBalance;
    private LoanStatus loanStatus;

}