package com.radix.assessment.payments.services;

import com.radix.assessment.payments.model.DTO.request.PaymentRequest;
import com.radix.assessment.payments.model.DTO.response.PaymentResponse;

public interface PaymentService {

    PaymentResponse recordPayment(PaymentRequest request);

}
