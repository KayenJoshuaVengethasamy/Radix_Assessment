/**
 * MapToLoanResponse is a Spring component responsible for converting a Loan entity into a LoanResponse DTO.
 * It acts as a mapper layer that separates internal database models from external API response structures,
 * ensuring clean architecture and consistent response formatting.
 *
 * @author Kayen Joshua Vengethasamy
 * @since 2026-06-19
 */

package com.radix.assessment.loans.model.DTO.response;

import com.radix.assessment.loans.model.Loan;
import org.springframework.stereotype.Component;

@Component
public class MapToLoanResponse {

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
