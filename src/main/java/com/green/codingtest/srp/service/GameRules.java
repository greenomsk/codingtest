package com.green.codingtest.srp.service;

import com.green.codingtest.srp.service.model.Gesture;
import com.green.codingtest.srp.service.model.Result;

/**
 * <h2>class GameRules. </h2>Contains game result decision algorithms.
 * Operates with terms 'Player' i.e. an external user and 'Opponent' i.e. game AI :)
 *
 * @see <a href='https://en.wikipedia.org/wiki/Rock%E2%80%93paper%E2%80%93scissors'>Wikipedia</a> for details
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 25.07.2019
 */
public interface GameRules {
    /**
     * Computes a game {@link Result} basing on player and opponent gestures.
     * @param playerGesture player {@link Gesture}
     * @param opponentGesture opponent {@link Gesture}
     * @return {@link Result}
     */
    Result computeResult(Gesture playerGesture, Gesture opponentGesture);

    /**
     * Computes an opponent {@link Gesture} leads to player lose.
     * @param playerGesture player {@link Gesture}
     * @return opponent {@link Gesture} leads to player lose.
     */
    Gesture computePlayerLoseGesture(Gesture playerGesture);
}
