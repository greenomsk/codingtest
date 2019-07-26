package com.green.codingtest.srp.service.exception;

/**
 * <h2>class GameNotFoundException</h2>
 *
 * {@link RuntimeException} implementation intended to process game not found case.
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 24.07.2019
 */
public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(final Long id) {
        super("Game with id=" + id + " has not been not found");
    }
}
