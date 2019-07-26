package com.green.codingtest.srp.service;

import com.green.codingtest.srp.service.model.Gesture;
import com.green.codingtest.srp.service.model.Result;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Objects.isNull;

/**
 * <h2>class GameRulesImpl</h2>
 *
 * {@link GameRules} concrete implementation
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 25.07.2019
 */
@Component
public class GameRulesImpl implements GameRules {
    private static final Result DEFAULT_RESULT = Result.DRAW;
    private static final Gesture DEFAULT_GESTURE = Gesture.ROCK;

    private static final Map<Pair<Gesture, Gesture>, Result> GAME_RULES =
        new HashMap<Pair<Gesture, Gesture>, Result>() {{
            put(Pair.of(Gesture.PAPER, Gesture.ROCK), Result.WIN);
            put(Pair.of(Gesture.ROCK, Gesture.SCISSORS), Result.WIN);
            put(Pair.of(Gesture.SCISSORS, Gesture.PAPER), Result.WIN);

            put(Pair.of(Gesture.ROCK, Gesture.PAPER), Result.LOSE);
            put(Pair.of(Gesture.SCISSORS, Gesture.ROCK), Result.LOSE);
            put(Pair.of(Gesture.PAPER, Gesture.SCISSORS), Result.LOSE);
        }};

    @Override
    public Result computeResult(final Gesture playerGesture, final Gesture opponentGesture) {
        final Result result = GAME_RULES.get(Pair.of(playerGesture, opponentGesture));
        return isNull(result) ? DEFAULT_RESULT : result;
    }

    @Override
    public Gesture computePlayerLoseGesture(final Gesture playerGesture) {
        return Stream.of(Gesture.values())
            .filter(i -> computeResult(playerGesture, i) == Result.LOSE)
            .findFirst()
            .orElse(DEFAULT_GESTURE);
    }
}
