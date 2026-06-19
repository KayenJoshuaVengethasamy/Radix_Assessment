package com.radix.assessment.payments.services.implementation;

import com.radix.assessment.common.constants.loans.LoanStatus;
import com.radix.assessment.loans.model.Loan;
import com.radix.assessment.loans.repository.LoanRepository;
import com.radix.assessment.payments.model.DTO.request.PaymentRequest;
import com.radix.assessment.payments.model.DTO.response.PaymentResponse;
import com.radix.assessment.payments.model.Payment;
import com.radix.assessment.payments.repository.PaymentRepository;
import com.radix.assessment.payments.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentServiceImplementation implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final LoanRepository loanRepository;

    @Override
    @Transactional
    public PaymentResponse recordPayment(PaymentRequest request) {

        Loan loan = loanRepository.findById(request.getLoanId())
                .orElseThrow(() -> new RuntimeException("Loan not found with ID : " + request.getLoanId()));

        if (loan.getStatus() == LoanStatus.SETTLED){
            throw new RuntimeException("Loan is already settled");
        }

        if (request.getPaymentAmount().compareTo(loan.getRemainingBalance())>0){
            throw new RuntimeException("Payment exceeds remaining loan amount");
        }

        Payment payment = Payment.builder()
                .loanId(request.getLoanId())
                .paymentAmount(request.getPaymentAmount())
                .build();
        paymentRepository.save(payment);

        BigDecimal newBalance = loan.getRemainingBalance().subtract(request.getPaymentAmount());
        loan.setRemainingBalance(newBalance);

        if (newBalance.compareTo(BigDecimal.ZERO)==0){
            loan.setStatus(LoanStatus.SETTLED);
        }

        loanRepository.save(loan);

        return PaymentResponse.builder()
                .paymentId(payment.getPaymentId())
                .loanId(payment.getLoanId())
                .paymentAmount(payment.getPaymentAmount())
                .remainingBalance(loan.getRemainingBalance())
                .loanStatus(loan.getStatus())
                .build();
    }
}
