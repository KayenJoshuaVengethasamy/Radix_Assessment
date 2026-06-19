package com.radix.assessment.loans.model.DTO.response;

import com.radix.assessment.common.constants.loans.LoanStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class LoanResponse {
    private Long loanId;
    private BigDecimal loanAmount;
    private BigDecimal remainingBalance;
    private Integer term;
    private LoanStatus status;
}