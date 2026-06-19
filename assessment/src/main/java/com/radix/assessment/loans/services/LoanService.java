package com.radix.assessment.loans.services;

import com.radix.assessment.common.constants.loans.LoanStatus;
import com.radix.assessment.loans.model.DTO.request.LoanRequest;
import com.radix.assessment.loans.model.DTO.response.LoanResponse;
import com.radix.assessment.loans.model.Loan;
import com.radix.assessment.loans.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

public interface LoanService {

    LoanResponse createLoan(LoanRequest request);

    LoanResponse getLoan(Long loanId);

}