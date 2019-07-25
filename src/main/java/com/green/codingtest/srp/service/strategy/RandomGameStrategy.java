package com.green.codingtest.srp.service.strategy;

import com.green.codingtest.srp.service.model.Gesture;
import com.green.codingtest.srp.service.model.Move;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * class RandomGameStrategy
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 23.07.2019
 */
@Component
public class RandomGameStrategy implements GameStrategy {

    @Override
    public String id() {
        return "game.strategy.random";
    }

    @Override
    public Gesture throwGesture(final Long gameId) {
        final int randomGestureIndex = new Random().nextInt(Gesture.values().length);
        return Gesture.values()[randomGestureIndex];
    }
}
