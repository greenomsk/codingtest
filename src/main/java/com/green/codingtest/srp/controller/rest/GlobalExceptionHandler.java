package com.green.codingtest.srp.controller.rest;

import com.green.codingtest.srp.controller.model.ErrorResultJson;
import com.green.codingtest.srp.controller.model.ErrorSubCode;
import com.green.codingtest.srp.service.exception.GameNotFoundException;
import com.green.codingtest.srp.service.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * class GlobalExceptionHandler
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 23.07.2019
 */
@Slf4j
@SuppressWarnings("unused")
@ControllerAdvice
@RequestMapping(produces = APPLICATION_JSON_UTF8_VALUE)
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResultJson> onValidationError(final ValidationException ex) {
        log.error("Exception has been thrown", ex);
        return ResponseEntity
            .badRequest()
            .body(
                new ErrorResultJson()
                    .setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                    .setSubCode(ErrorSubCode.SERVICE_VALIDATION_ERROR.getValue())
                    .setMessage(ex.getMessage())
            );
    }

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<ErrorResultJson> onGameNotFoundError(final GameNotFoundException ex) {
        log.error("Exception has been thrown", ex);
        return ResponseEntity
            .badRequest()
            .body(
                new ErrorResultJson()
                    .setCode(String.valueOf(HttpStatus.NOT_FOUND.value()))
                    .setSubCode(ErrorSubCode.SERVICE_ERROR.getValue())
                    .setMessage(ex.getMessage())
            );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResultJson> onGeneralError(final Exception ex) {
        log.error("Exception has been thrown", ex);
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                new ErrorResultJson()
                    .setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                    .setSubCode(ErrorSubCode.UNEXPECTED_ERROR.getValue())
                    .setMessage(ex.getMessage())
            );
    }
}
