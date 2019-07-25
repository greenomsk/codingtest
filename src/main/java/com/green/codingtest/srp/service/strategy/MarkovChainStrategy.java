package com.green.codingtest.srp.service.strategy;

import com.green.codingtest.srp.datasource.dao.MoveLogDao;
import com.green.codingtest.srp.datasource.dto.MoveLogDto;
import com.green.codingtest.srp.service.GameRules;
import com.green.codingtest.srp.service.model.Gesture;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * class MarkovChainStrategy
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 25.07.2019
 */
@Component
public class MarkovChainStrategy implements GameStrategy {

    private static final Gesture DEFAULT_GESTURE = Gesture.ROCK;

    private final MoveLogDao moveLogDao;

    private final GameRules gameRules;

    @Inject
    public MarkovChainStrategy(
        final MoveLogDao moveLogDao,
        final GameRules gameRules
    ) {
        this.moveLogDao = moveLogDao;
        this.gameRules = gameRules;
    }

    @Override
    public String id() {
        return "game.strategy.markov.chain";
    }

    @Override
    public Gesture throwGesture(final Long gameId) {
        final List<MoveLogDto> moveLog = moveLogDao.getMoveLog(gameId);
        if (isEmpty(moveLog)) {
            return gameRules.computePlayerLoseGesture(DEFAULT_GESTURE);
        }

        if (moveLog.size() == 1) {
            return gameRules.computePlayerLoseGesture(Gesture.valueOf(moveLog.get(0).getPlayerGesture()));
        }

        final Map<Pair<Gesture, Gesture>, Double> transitionMatrix = buildTransitionMatrix(
            moveLogDao.getMoveLog(gameId)
        );
        final Gesture mostProbableNextPlayerGesture = transitionMatrix
            .entrySet()
            .stream()
            .max(comparing(Map.Entry::getValue))
            .map(i -> i.getKey().getRight())
            .orElse(DEFAULT_GESTURE);

        return gameRules.computePlayerLoseGesture(mostProbableNextPlayerGesture);
    }

    private Map<Pair<Gesture, Gesture>, Double> buildTransitionMatrix(final List<MoveLogDto> moveLog) {
        final Map<Pair<Gesture, Gesture>, Double> result = new HashMap<>();
        Stream.of(Gesture.values()).forEach(prevPlayerGesture ->
            Stream.of(Gesture.values()).forEach(nextPlayerGesture ->
                result.put(Pair.of(prevPlayerGesture, nextPlayerGesture), 0d)
            )
        );

        final List<MoveLogDto> orderedMovesLog = moveLog.stream()
            .sorted(comparing(MoveLogDto::getCreatedAt))
            .collect(toList());

        final int totalMoves = moveLog.size();

        IntStream
            .range(1, moveLog.size())
            .forEach(
            index -> {
                final MoveLogDto prev = orderedMovesLog.get(index - 1);
                final MoveLogDto next = orderedMovesLog.get(index);
                final Pair<Gesture, Gesture> key =
                    Pair.of(Gesture.valueOf(prev.getPlayerGesture()), Gesture.valueOf(next.getPlayerGesture()));
                final Double transitionProbability = result.get(key) + 1d / totalMoves;
                result.put(key, transitionProbability);
            }
        );

        return result;
    }
}
