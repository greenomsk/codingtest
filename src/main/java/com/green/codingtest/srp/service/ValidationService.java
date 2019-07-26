package com.green.codingtest.srp.service;

/**
 * <h2>class ValidationService</h2>
 *
 * Base interface of service tier validators.
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 23.07.2019
 */
public interface ValidationService {
    /**
     * Validates {@link MoveValidationContext} containing information about player move.
     * In case of invalid player move {@link com.green.codingtest.srp.service.exception.ValidationException}
     * will be thrown.
     * @param moveValidationContext {@link MoveValidationContext}
     */
    void validate(MoveValidationContext moveValidationContext);
}
