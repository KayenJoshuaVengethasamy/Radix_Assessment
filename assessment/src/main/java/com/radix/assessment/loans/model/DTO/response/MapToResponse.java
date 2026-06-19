package com.radix.assessment.loans.model.DTO.response;

import com.radix.assessment.loans.model.Loan;
import org.springframework.stereotype.Component;

@Component
public class MapToResponse {

    public LoanResponse mapToResponse(Loan loan) {
        return LoanResponse.builder()
                .loanId(loan.getLoanId())
                .loanAmount(loan.getLoanAmount())
                .remainingBalance(loan.getRemainingBalance())
                .term(loan.getTerm())
                .status(loan.getStatus())
                .build();
    }
}
