package com.green.codingtest.srp.datasource.dao;

import com.green.codingtest.srp.datasource.dto.GameDto;

import java.util.Optional;

/**
 * class GameDao
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 24.07.2019
 */
public interface GameDao {
    Optional<GameDto> findById(Long id);
    Long insert(GameDto dto);
    void updateState(Long id, String state);
    void touch(Long id);
}
