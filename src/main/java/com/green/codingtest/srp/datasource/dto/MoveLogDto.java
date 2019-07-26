package com.green.codingtest.srp.datasource.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * <h2>class MoveLogDto</h2>
 *
 * Move log data transfer object
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 23.07.2019
 */
@Accessors(chain = true)
@Getter
@Setter
public class MoveLogDto {
    private Long id;
    private Long gameId;
    private String playerGesture;
    private String opponentGesture;
    private String result;
    private Timestamp createdAt;
}
