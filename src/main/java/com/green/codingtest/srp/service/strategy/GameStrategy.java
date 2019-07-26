package com.green.codingtest.srp.service.strategy;

import com.green.codingtest.srp.service.model.Gesture;

/**
 * <h2>class GameStrategy</h2>
 *
 * Strategy pattern for implementation of variety of gestures throwing algorithms.
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 23.07.2019
 */
public interface GameStrategy {
    /**
     * Returns unique game strategy identifier.
     * @return unique game strategy identifier.
     */
    String id();

    /**
     * Throws opponent gesture related to game identified by gameId.
     * @param gameId game identifier
     * @return opponent {@link Gesture}
     */
    Gesture throwGesture(Long gameId);
}
