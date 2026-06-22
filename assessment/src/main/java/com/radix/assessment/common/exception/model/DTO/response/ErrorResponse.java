/**
 * ErrorResponse is used to standardize error responses returned by the application. It encapsulates the HTTP status
 * code and a descriptive error message, providing a consistent structure for handling and displaying errors to the client.
 *
 * @author Kayen Joshua Vengethasamy
 * @since 2026-06-19
 */
package com.radix.assessment.common.exception.model.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

    private int status;
    private String message;

}
