package com.green.codingtest.srp.service.strategy;

import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

/**
 * <h2>class GameStrategyFactoryImpl</h2>
 *
 * Concrete {@link GameStrategyFactory} implementation.
 * Provides with {@link GameStrategy} by using configured game strategy id.
 *
 * @see GameStrategy#id()
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 25.07.2019
 */
@Component
public class GameStrategyFactoryImpl implements GameStrategyFactory {

    private final GameStrategy strategy;

    /**
     * Class constructor.
     *
     * @param strategyId strategy identifier to choose strategy from strategies list.
     * @param strategies list of strategies defined in project.
     */
    @Inject
    public GameStrategyFactoryImpl(
        @Value("${game.strategy}") String strategyId,
        final List<GameStrategy> strategies
    ) {
        strategy = strategies
            .stream()
            .filter(i -> StringUtils.equals(i.id(), strategyId))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Strategy '" + strategyId + "' not found"));
    }

    @Override
    public GameStrategy getGameStrategy() {
        return strategy;
    }
}
