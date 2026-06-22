/**
 * LoanRequest is used to capture and validate input data when creating a new loan. It ensures that incoming requests
 * contain valid values before they reach the service layer using Bean Validation annotations.
 *
 * @author Kayen Joshua Vengethasamy
 * @since 2026-06-19
 */

package com.radix.assessment.loans.model.DTO.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanRequest {

    @NotNull
    @Positive
    private BigDecimal loanAmount;
    @NotNull
    @Positive
    private Integer term;

}
