package com.radix.assessment.loans.services.implementation;

import com.radix.assessment.common.constants.ErrorConstants;
import com.radix.assessment.common.constants.loans.LoanStatus;
import com.radix.assessment.common.exception.model.CustomException;
import com.radix.assessment.loans.model.DTO.request.LoanRequest;
import com.radix.assessment.loans.model.DTO.response.LoanResponse;
import com.radix.assessment.loans.model.DTO.response.MapToResponse;
import com.radix.assessment.loans.model.Loan;
import com.radix.assessment.loans.repository.LoanRepository;
import com.radix.assessment.loans.services.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class LoanServiceImplementation implements LoanService {

    private final LoanRepository loanRepository;
    private final MapToResponse map;

    @Override
    public LoanResponse createLoan(LoanRequest request) {

        if (request.getLoanAmount().compareTo(BigDecimal.ZERO) > 0) {
            throw new CustomException(ErrorConstants.NEGATIVE_VALUE, "Loan Amount");
        }

        if (request.getTerm() <= 0) {
            throw new CustomException(ErrorConstants.NEGATIVE_VALUE, "Term");
        }

        Loan loan = Loan.builder()
                .loanAmount(request.getLoanAmount())
                .remainingBalance(request.getLoanAmount())
                .term(request.getTerm())
                .status(LoanStatus.ACTIVE)
                .build();
        Loan saved = loanRepository.save(loan);
        return map.mapToResponse(saved);
    }


    @Override
    public LoanResponse getLoan(Long loanId) {
        Loan loan = loanRepository
                .findById(loanId)
                .orElseThrow(() -> new CustomException(ErrorConstants.NO_LOAN, loanId));
        return map.mapToResponse(loan);
    }

}