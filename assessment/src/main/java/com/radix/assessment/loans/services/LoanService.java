/**
 * LoanService is a service layer interface that defines the contract for loan-related business operations.
 * It abstracts the implementation details and exposes core functionalities such as creating a loan and
 * retrieving a loan by ID.
 *
 * @author Kayen Joshua Vengethasamy
 * @since 2026-06-19
 */
package com.radix.assessment.loans.services;

import com.radix.assessment.loans.model.DTO.request.LoanRequest;
import com.radix.assessment.loans.model.DTO.response.LoanResponse;

public interface LoanService {

    LoanResponse createLoan(LoanRequest request);

    LoanResponse getLoan(Long loanId);

}