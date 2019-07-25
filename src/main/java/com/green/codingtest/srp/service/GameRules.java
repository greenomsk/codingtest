package com.green.codingtest.srp.service;

import com.green.codingtest.srp.service.model.Gesture;
import com.green.codingtest.srp.service.model.Result;

/**
 * class GameRules
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 25.07.2019
 */
public interface GameRules {
    Result computeResult(Gesture playerGesture, Gesture opponentGesture);
    Gesture computePlayerLoseGesture(Gesture playerGesture);
}
