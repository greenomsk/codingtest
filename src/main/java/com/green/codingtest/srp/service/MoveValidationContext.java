package com.green.codingtest.srp.service;

/**
 * <h2>class MoveValidationContext</h2>.
 * Contains information necessary for player move validation.
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 23.07.2019
 */
public interface MoveValidationContext {
    /**
     * Returns player gesture string representation.
     * @return player gesture string representation.
     */
    String gesture();
}
