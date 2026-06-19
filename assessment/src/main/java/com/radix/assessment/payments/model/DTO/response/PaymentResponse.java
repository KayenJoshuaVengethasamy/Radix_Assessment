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