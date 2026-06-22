/**
 * CustomException is a custom runtime exception used to represent business specific errors. It wraps a value
 * from the ErrorConstants enum value. When thrown, it generates a formatted error message and allows the global
 * exception handler to return a response based on the defined error metadata.
 *
 * @author Kayen Joshua Vengethasamy
 * @since 2026-06-19
 */

package com.radix.assessment.common.exception.model;

import com.radix.assessment.common.constants.ErrorConstants;
import lombok.Generated;
import lombok.Getter;

@Generated
@Getter
public class CustomException extends RuntimeException {

    private final ErrorConstants error;

    public CustomException(ErrorConstants error, Object... args) {
        super(String.format(error.getMessage(), args));
        this.error = error;
    }

}
