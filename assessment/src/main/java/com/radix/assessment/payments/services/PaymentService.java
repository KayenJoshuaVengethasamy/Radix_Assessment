/**
 * PaymentService is a service layer interface that defines the contract for payment-related business operations.
 * It abstracts the implementation details and exposes a single operation for recording loan payments, ensuring loose
 * coupling between the controller and service implementation.
 *
 * @author Kayen Joshua Vengethasamy
 * @since 2026-06-19
 */

package com.radix.assessment.payments.services;

import com.radix.assessment.payments.model.DTO.request.PaymentRequest;
import com.radix.assessment.payments.model.DTO.response.PaymentResponse;

public interface PaymentService {

    PaymentResponse recordPayment(PaymentRequest request);

}
