package com.radix.assessment.loans.repository;

import com.radix.assessment.loans.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
