/**
 * ErrorConstants is the enum that centralizes all error definitions. Each constant represents a validation error
 * associated with a business rules and contains the HTTP status code and a descriptive error message. This ensures
 * consistent error handling and response generation across the application while avoiding hardcoded status codes
 * and messages throughout the codebase.
 *
 * @author Kayen Joshua Vengethasamy
 * @since 2026-06-19
 */

package com.radix.assessment.common.constants;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorConstants {

    NO_LOAN(HttpStatus.NOT_FOUND, "No loan found for loan ID: %d"),
    NEGATIVE_VALUE(HttpStatus.NOT_ACCEPTABLE, "The value provided for the %s cannot be less than or equal to 0"),
    LOAN_ALREADY_SETTLED(HttpStatus.UNPROCESSABLE_CONTENT, "Loan %d is already settled. No further payments accepted."),
    PAYMENT_EXCEEDS_BALANCE(HttpStatus.UNPROCESSABLE_CONTENT, "Payment amount of %.2f exceeds remaining loan balance of %.2f");

    private final HttpStatus code;
    private final String message;

    ErrorConstants(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }

}

