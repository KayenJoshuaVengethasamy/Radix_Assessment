/**
 * LoanServiceTest is a unit test class that verifies the behavior of the LoanServiceImplementation. It uses Mockito
 * to mock dependencies (LoanRepository and MapToLoanResponse) and ensures that loan creation and retrieval logic
 * behaves correctly under different scenarios, including successful operations and error handling.
 */

package com.radix.assessment.payments.services;

import com.radix.assessment.common.constants.ErrorConstants;
import com.radix.assessment.common.constants.loans.LoanStatus;
import com.radix.assessment.common.exception.model.CustomException;
import com.radix.assessment.loans.model.Loan;
import com.radix.assessment.loans.repository.LoanRepository;
import com.radix.assessment.payments.model.DTO.request.PaymentRequest;
import com.radix.assessment.payments.model.DTO.response.PaymentResponse;
import com.radix.assessment.payments.model.Payment;
import com.radix.assessment.payments.repository.PaymentRepository;
import com.radix.assessment.payments.services.implementation.PaymentServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private PaymentServiceImplementation paymentService;

    private Loan activeLoan;
    private Loan settledLoan;
    private PaymentRequest paymentRequest;

    @BeforeEach
    void setUp() {
        activeLoan = Loan.builder()
                .loanId(1L)
                .loanAmount(BigDecimal.valueOf(10000))
                .remainingBalance(BigDecimal.valueOf(10000))
                .term(12)
                .status(LoanStatus.ACTIVE)
                .build();

        settledLoan = Loan.builder()
                .loanId(2L)
                .loanAmount(BigDecimal.valueOf(500))
                .remainingBalance(BigDecimal.ZERO)
                .term(6)
                .status(LoanStatus.SETTLED)
                .build();

        paymentRequest = new PaymentRequest();
        paymentRequest.setLoanId(1L);
        paymentRequest.setPaymentAmount(BigDecimal.valueOf(2000));
    }

    // ─── PARTIAL PAYMENT ──────────────────────────────────────────────────────

    @Test
    @DisplayName("Should record payment and reduce remaining balance")
    void recordPayment_ValidPartialPayment_ReducesBalance() {
        Payment savedPayment = Payment.builder()
                .paymentId(1L)
                .loanId(1L)
                .paymentAmount(BigDecimal.valueOf(2000))
                .build();

        when(loanRepository.findById(1L)).thenReturn(Optional.of(activeLoan));
        when(paymentRepository.save(any(Payment.class))).thenReturn(savedPayment);
        when(loanRepository.save(any(Loan.class))).thenReturn(activeLoan);

        PaymentResponse result = paymentService.recordPayment(paymentRequest);

        assertThat(result).isNotNull();
        assertThat(result.getRemainingBalance())
                .isEqualByComparingTo(BigDecimal.valueOf(8000));
        assertThat(result.getLoanStatus()).isEqualTo(LoanStatus.ACTIVE);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(loanRepository, times(1)).save(any(Loan.class));
    }

    @Test
    @DisplayName("Should persist payment to repository")
    void recordPayment_ShouldSavePaymentToRepository() {
        Payment savedPayment = Payment.builder()
                .paymentId(1L)
                .loanId(1L)
                .paymentAmount(BigDecimal.valueOf(2000))
                .build();

        when(loanRepository.findById(1L)).thenReturn(Optional.of(activeLoan));
        when(paymentRepository.save(any(Payment.class))).thenReturn(savedPayment);
        when(loanRepository.save(any(Loan.class))).thenReturn(activeLoan);

        paymentService.recordPayment(paymentRequest);

        verify(paymentRepository, times(1)).save(any(Payment.class));
    }

    @Test
    @DisplayName("Should move loan to SETTLED when payment equals remaining balance")
    void recordPayment_ExactRemainingBalance_SettlesLoan() {
        paymentRequest.setPaymentAmount(BigDecimal.valueOf(10000));

        Payment savedPayment = Payment.builder()
                .paymentId(1L)
                .loanId(1L)
                .paymentAmount(BigDecimal.valueOf(10000))
                .build();

        when(loanRepository.findById(1L)).thenReturn(Optional.of(activeLoan));
        when(paymentRepository.save(any(Payment.class))).thenReturn(savedPayment);
        when(loanRepository.save(any(Loan.class))).thenReturn(activeLoan);

        PaymentResponse result = paymentService.recordPayment(paymentRequest);

        assertThat(result.getRemainingBalance())
                .isEqualByComparingTo(BigDecimal.ZERO);
        assertThat(result.getLoanStatus()).isEqualTo(LoanStatus.SETTLED);
    }

    @Test
    @DisplayName("Should save updated loan status to repository after settlement")
    void recordPayment_FullPayment_PersistsSettledStatus() {
        paymentRequest.setPaymentAmount(BigDecimal.valueOf(10000));

        Payment savedPayment = Payment.builder()
                .paymentId(1L)
                .loanId(1L)
                .paymentAmount(BigDecimal.valueOf(10000))
                .build();

        when(loanRepository.findById(1L)).thenReturn(Optional.of(activeLoan));
        when(paymentRepository.save(any(Payment.class))).thenReturn(savedPayment);
        when(loanRepository.save(any(Loan.class))).thenReturn(activeLoan);

        paymentService.recordPayment(paymentRequest);

        verify(loanRepository, times(1)).save(argThat(loan ->
                loan.getStatus() == LoanStatus.SETTLED &&
                        loan.getRemainingBalance().compareTo(BigDecimal.ZERO) == 0
        ));
    }

    @Test
    @DisplayName("Should throw CustomException when payment exceeds remaining balance")
    void recordPayment_ExceedsBalance_ThrowsCustomException() {
        paymentRequest.setPaymentAmount(BigDecimal.valueOf(99999));

        when(loanRepository.findById(1L)).thenReturn(Optional.of(activeLoan));

        assertThatThrownBy(() -> paymentService.recordPayment(paymentRequest))
                .isInstanceOf(CustomException.class)
                .satisfies(ex -> {
                    CustomException custom = (CustomException) ex;
                    assertThat(custom.getError())
                            .isEqualTo(ErrorConstants.PAYMENT_EXCEEDS_BALANCE);
                });

        verify(paymentRepository, never()).save(any(Payment.class));
        verify(loanRepository, never()).save(any(Loan.class));
    }

    @Test
    @DisplayName("Should not save payment or update loan on overpayment")
    void recordPayment_Overpayment_NoSideEffects() {
        paymentRequest.setPaymentAmount(BigDecimal.valueOf(99999));

        when(loanRepository.findById(1L)).thenReturn(Optional.of(activeLoan));

        assertThatThrownBy(() -> paymentService.recordPayment(paymentRequest))
                .isInstanceOf(CustomException.class);

        verifyNoInteractions(paymentRepository);
        verify(loanRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw CustomException when paying against a settled loan")
    void recordPayment_AlreadySettledLoan_ThrowsCustomException() {
        paymentRequest.setLoanId(2L);
        paymentRequest.setPaymentAmount(BigDecimal.valueOf(100));

        when(loanRepository.findById(2L)).thenReturn(Optional.of(settledLoan));

        assertThatThrownBy(() -> paymentService.recordPayment(paymentRequest))
                .isInstanceOf(CustomException.class)
                .satisfies(ex -> {
                    CustomException custom = (CustomException) ex;
                    assertThat(custom.getError())
                            .isEqualTo(ErrorConstants.LOAN_ALREADY_SETTLED);
                });

        verify(paymentRepository, never()).save(any(Payment.class));
    }

    @Test
    @DisplayName("Should throw CustomException when loan not found")
    void recordPayment_LoanNotFound_ThrowsCustomException() {
        paymentRequest.setLoanId(999L);

        when(loanRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> paymentService.recordPayment(paymentRequest))
                .isInstanceOf(CustomException.class)
                .satisfies(ex -> {
                    CustomException custom = (CustomException) ex;
                    assertThat(custom.getError()).isEqualTo(ErrorConstants.NO_LOAN);
                });

        verifyNoInteractions(paymentRepository);
    }
}