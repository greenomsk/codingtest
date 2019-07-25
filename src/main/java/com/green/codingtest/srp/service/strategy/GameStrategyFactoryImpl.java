package com.green.codingtest.srp.service.strategy;

import org.jooq.tools.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;

/**
 * class GameStrategyFactoryImpl
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 25.07.2019
 */
@Component
public class GameStrategyFactoryImpl implements GameStrategyFactory {

    private final GameStrategy strategy;

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
