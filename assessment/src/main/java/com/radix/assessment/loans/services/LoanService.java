package com.radix.assessment.loans.services;

import com.radix.assessment.loans.model.DTO.request.LoanRequest;
import com.radix.assessment.loans.model.DTO.response.LoanResponse;

public interface LoanService {

    LoanResponse createLoan(LoanRequest request);

    LoanResponse getLoan(Long loanId);

}