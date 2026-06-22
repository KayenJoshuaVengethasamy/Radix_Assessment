/**
 * PaymentController is a REST controller that exposes an endpoint for processing loan payments.
 * It receives payment requests from clients, validates them, delegates the business logic to the PaymentService,
 * and returns a structured response confirming the recorded payment.
 *
 * @author Kayen Joshua Vengethasamy
 * @since 2026-06-19
 */

package com.radix.assessment.payments.controller;

import com.radix.assessment.payments.model.DTO.request.PaymentRequest;
import com.radix.assessment.payments.model.DTO.response.PaymentResponse;
import com.radix.assessment.payments.services.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> makePayment(@Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.recordPayment(request));
    }

}
