package com.radix.assessment.loans.loader;

import com.radix.assessment.common.constants.loans.LoanStatus;
import com.radix.assessment.loans.model.Loan;
import com.radix.assessment.loans.repository.LoanRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class LoanDataLoader {

    @Bean
    CommandLineRunner init(LoanRepository loanRepository) {
        return args -> {

            loanRepository.save(
                    Loan.builder()
                            .loanAmount(BigDecimal.valueOf(500000))
                            .remainingBalance(BigDecimal.valueOf(500000))
                            .term(60)
                            .status(LoanStatus.ACTIVE)
                            .build()
            );

            loanRepository.save(
                    Loan.builder()
                            .loanAmount(BigDecimal.valueOf(500))
                            .remainingBalance(BigDecimal.valueOf(0))
                            .term(60)
                            .status(LoanStatus.SETTLED)
                            .build()
            );

        };
    }
}
