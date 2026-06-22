/**
 * PaymentRepository is a Spring Data JPA repository interface that provides database access operations for the
 * Payment entity. It enables standard CRUD functionality without requiring any manual implementation.
 *
 * @author Kayen Joshua Vengethasamy
 * @since 2026-06-19
 */

package com.radix.assessment.payments.repository;

import com.radix.assessment.payments.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
