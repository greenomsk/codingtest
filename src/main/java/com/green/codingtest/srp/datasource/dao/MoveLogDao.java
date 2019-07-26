package com.green.codingtest.srp.datasource.dao;

import com.green.codingtest.srp.datasource.dto.MoveLogDto;

import java.util.List;

/**
 * <h2>class MoveLogDao</h2>
 *
 * move_log table data access object. Provides with variety of methods enough to access move_log table data.

 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 23.07.2019
 */
public interface MoveLogDao {

    /**
     * Returns move log related to game identified by gameId
     * @param gameId game identifier
     * @return list of {@link MoveLogDto} related to gameId
     */
    List<MoveLogDto> getMoveLog(Long gameId);

    /**
     * Inserts new log record
     * @param dto {@link MoveLogDto}
     * @return identifier of newly inserted record
     */
    Long insert(MoveLogDto dto);
}
