/**
 * LoanResponse is used to represent loan data returned to API clients.
 * It provides a structured and controlled view of a loan entity, exposing only the relevant fields
 * needed by consumers while hiding internal implementation details.
 *
 * @author Kayen Joshua Vengethasamy
 * @since 2026-06-19
 */

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