/**
 * Loan is a JPA entity that represents a loan record in the database. It defines the structure of the loans table
 * and stores key loan attributes such as amount, remaining balance, term, and status. It also includes basic
 * validation and persistence metadata to enforce data integrity at the database and application levels.
 *
 * @author Kayen Joshua Vengethasamy
 * @since 2026-06-19
 */

package com.radix.assessment.loans.model;

import com.radix.assessment.common.constants.loans.LoanStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "loans")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Loan {

    @Description("unique identifier for the loan")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanId;

    @Description("the total amount of the loan")
    @NotNull
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal loanAmount;

    @Description("the remaining loan amount")
    @NotNull
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal remainingBalance;

    @Description("the duration of the loan in months")
    @Positive
    @Column(nullable = false)
    private Integer term;

    @Description("ACTIVE or SETTLED, depending on whether the loan has been repaid in full")
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoanStatus status;

}
