/**
 * LoanRepository is a Spring Data JPA repository interface that provides data access operations for the Loan entity.
 * It enables standard CRUD functionality without requiring boilerplate SQL or implementation code.
 *
 * @author Kayen Joshua Vengethasamy
 * @since 2026-06-19
 */

package com.radix.assessment.loans.repository;

import com.radix.assessment.loans.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
