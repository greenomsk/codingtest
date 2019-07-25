package com.green.codingtest.srp.datasource.dao;

import com.green.codingtest.srp.datasource.dto.MoveLogDto;

import java.util.List;

/**
 * class MoveLogDao
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 23.07.2019
 */
public interface MoveLogDao {

    List<MoveLogDto> getMoveLog(Long gameId);

    Long insert(MoveLogDto dto);
}
