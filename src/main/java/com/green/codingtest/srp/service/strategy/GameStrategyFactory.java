package com.green.codingtest.srp.service.strategy;

/**
 * <h2>class GameStrategyFactory</h2>
 *
 * Factory pattern providing with {@link GameStrategy}
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 25.07.2019
 */
public interface GameStrategyFactory {
    /**
     * Provides with {@link GameStrategy}
     * @return {@link GameStrategy}
     */
    GameStrategy getGameStrategy();
}
