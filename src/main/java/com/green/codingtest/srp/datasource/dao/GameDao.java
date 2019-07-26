package com.green.codingtest.srp.datasource.dao;

import com.green.codingtest.srp.datasource.dto.GameDto;

import java.util.Optional;

/**
 * <h2>class GameDao</h2>
 *
 * Game table data access object. Provides with variety of methods enough to access game table data.
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 24.07.2019
 */
public interface GameDao {
    /**
     * Looks for {@link GameDto} by its  identifier
     * @param id game identifier
     * @return {@link Optional} containing found game
     */
    Optional<GameDto> findById(Long id);

    /**
     * Inserts new {@link GameDto} into game table
     * @param dto {@link GameDto}
     * @return identifier of newly inserted game
     */
    Long insert(GameDto dto);

    /**
     * Updates game state.
     * @param id game identifier.
     * @param state game state string representation
     */
    void updateState(Long id, String state);

    /**
     * Refreshes game update timestamp
     * @param id game identifier
     */
    void touch(Long id);
}
