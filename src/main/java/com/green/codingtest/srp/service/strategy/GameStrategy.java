package com.green.codingtest.srp.service.strategy;

import com.green.codingtest.srp.service.model.Gesture;

/**
 * class GameStrategy
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 23.07.2019
 */
public interface GameStrategy {
    String id();
    Gesture throwGesture(Long gameId);
}
