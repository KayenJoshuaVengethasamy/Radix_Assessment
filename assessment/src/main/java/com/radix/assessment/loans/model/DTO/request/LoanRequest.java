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
