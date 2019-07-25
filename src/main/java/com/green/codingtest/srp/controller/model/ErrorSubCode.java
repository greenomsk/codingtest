package com.green.codingtest.srp.controller.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * class ErrorSubCode
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 23.07.2019
 */
@RequiredArgsConstructor
@Getter
public enum ErrorSubCode {
    UNEXPECTED_ERROR("1001"),
    SERVICE_VALIDATION_ERROR("1002"),
    SERVICE_ERROR("1003");

    private final String value;
}
