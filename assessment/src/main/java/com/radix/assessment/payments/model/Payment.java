package com.radix.assessment.payments.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jdk.jfr.Description;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Description("unique identifier for the payment")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Description("the loan associated with the payment")
    @NotNull
    @Column(nullable = false)
    private Long loanId;

    @Description("the amount paid towards the loan")
    @NotNull
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal paymentAmount;

}
