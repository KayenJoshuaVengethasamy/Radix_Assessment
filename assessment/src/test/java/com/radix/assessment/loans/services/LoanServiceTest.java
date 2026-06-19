package com.radix.assessment.loans.services;

import com.radix.assessment.common.constants.ErrorConstants;
import com.radix.assessment.common.constants.loans.LoanStatus;
import com.radix.assessment.common.exception.model.CustomException;
import com.radix.assessment.loans.model.DTO.request.LoanRequest;
import com.radix.assessment.loans.model.DTO.response.LoanResponse;
import com.radix.assessment.loans.model.DTO.response.MapToLoanResponse;
import com.radix.assessment.loans.model.Loan;
import com.radix.assessment.loans.repository.LoanRepository;
import com.radix.assessment.loans.services.implementation.LoanServiceImplementation;
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
class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private MapToLoanResponse loanMapper;

    @InjectMocks
    private LoanServiceImplementation loanService;

    private LoanRequest loanRequest;
    private Loan savedLoan;
    private LoanResponse loanResponse;

    @BeforeEach
    void setUp() {
        loanRequest = new LoanRequest();
        loanRequest.setLoanAmount(BigDecimal.valueOf(10000));
        loanRequest.setTerm(12);

        savedLoan = Loan.builder()
                .loanId(1L)
                .loanAmount(BigDecimal.valueOf(10000))
                .remainingBalance(BigDecimal.valueOf(10000))
                .term(12)
                .status(LoanStatus.ACTIVE)
                .build();

        loanResponse = LoanResponse.builder()
                .loanId(1L)
                .loanAmount(BigDecimal.valueOf(10000))
                .remainingBalance(BigDecimal.valueOf(10000))
                .term(12)
                .status(LoanStatus.ACTIVE)
                .build();
    }

    // ─── CREATE LOAN ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("Should create loan with ACTIVE status and remainingBalance equal to loanAmount")
    void createLoan_ValidRequest_ReturnsLoanResponse() {
        when(loanRepository.save(any(Loan.class))).thenReturn(savedLoan);
        when(loanMapper.mapToResponse(savedLoan)).thenReturn(loanResponse);

        LoanResponse result = loanService.createLoan(loanRequest);

        assertThat(result).isNotNull();
        assertThat(result.getLoanId()).isEqualTo(1L);
        assertThat(result.getLoanAmount()).isEqualByComparingTo(BigDecimal.valueOf(10000));
        assertThat(result.getRemainingBalance()).isEqualByComparingTo(BigDecimal.valueOf(10000));
        assertThat(result.getStatus()).isEqualTo(LoanStatus.ACTIVE);
        assertThat(result.getTerm()).isEqualTo(12);

        verify(loanRepository, times(1)).save(any(Loan.class));
    }

    @Test
    @DisplayName("Should set remainingBalance equal to loanAmount on creation")
    void createLoan_ShouldSetRemainingBalanceToLoanAmount() {
        when(loanRepository.save(any(Loan.class))).thenReturn(savedLoan);
        when(loanMapper.mapToResponse(savedLoan)).thenReturn(loanResponse);

        LoanResponse result = loanService.createLoan(loanRequest);

        assertThat(result.getRemainingBalance())
                .isEqualByComparingTo(result.getLoanAmount());
    }

    @Test
    @DisplayName("Should set loan status to ACTIVE on creation")
    void createLoan_ShouldSetStatusToActive() {
        when(loanRepository.save(any(Loan.class))).thenReturn(savedLoan);
        when(loanMapper.mapToResponse(savedLoan)).thenReturn(loanResponse);

        LoanResponse result = loanService.createLoan(loanRequest);

        assertThat(result.getStatus()).isEqualTo(LoanStatus.ACTIVE);
    }

    @Test
    @DisplayName("Should save loan to repository on creation")
    void createLoan_ShouldPersistToRepository() {
        when(loanRepository.save(any(Loan.class))).thenReturn(savedLoan);
        when(loanMapper.mapToResponse(savedLoan)).thenReturn(loanResponse);

        loanService.createLoan(loanRequest);

        verify(loanRepository, times(1)).save(any(Loan.class));
        verifyNoMoreInteractions(loanRepository);
    }

    // ─── GET LOAN ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Should return loan details when loan exists")
    void getLoan_ExistingId_ReturnsLoanResponse() {
        when(loanRepository.findById(1L)).thenReturn(Optional.of(savedLoan));
        when(loanMapper.mapToResponse(savedLoan)).thenReturn(loanResponse);

        LoanResponse result = loanService.getLoan(1L);

        assertThat(result).isNotNull();
        assertThat(result.getLoanId()).isEqualTo(1L);
        assertThat(result.getLoanAmount()).isEqualByComparingTo(BigDecimal.valueOf(10000));
        assertThat(result.getStatus()).isEqualTo(LoanStatus.ACTIVE);

        verify(loanRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should throw CustomException with NO_LOAN error when loan not found")
    void getLoan_NonExistentId_ThrowsCustomException() {
        when(loanRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> loanService.getLoan(999L))
                .isInstanceOf(CustomException.class)
                .satisfies(ex -> {
                    CustomException custom = (CustomException) ex;
                    assertThat(custom.getError()).isEqualTo(ErrorConstants.NO_LOAN);
                });

        verify(loanRepository, times(1)).findById(999L);
    }
}
