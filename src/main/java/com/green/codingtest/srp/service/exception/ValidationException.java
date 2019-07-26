package com.green.codingtest.srp.service.exception;

/**
 * <h2>class ValidationException</h2>
 *
 * {@link RuntimeException} implementation intended to process any validation errors.
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 23.07.2019
 */
public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
