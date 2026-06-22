/**
 * LoanController exposes the HTTP endpoint /loans for managing loans.It acts as the entry point for loan requests,
 * delegating business logic to the LoanService and returning the appropriate HTTP response. It supports creating a
 * new loan and retrieving an existing loan by the loan ID.
 *
 * @author Kayen Joshua Vengethasamy
 * @since 2026-06-19
 */

package com.radix.assessment.loans.controller;

import com.radix.assessment.loans.model.DTO.request.LoanRequest;
import com.radix.assessment.loans.model.DTO.response.LoanResponse;
import com.radix.assessment.loans.services.LoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @PostMapping
    public ResponseEntity<LoanResponse> createLoan(@Valid @RequestBody LoanRequest request) {
        LoanResponse response = loanService.createLoan(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{loanId}")
    public ResponseEntity<LoanResponse> getLoan(@PathVariable Long loanId) {
        return ResponseEntity.ok(loanService.getLoan(loanId));
    }

}