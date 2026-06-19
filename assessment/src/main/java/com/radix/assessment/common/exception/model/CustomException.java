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
