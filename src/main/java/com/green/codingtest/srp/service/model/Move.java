package com.green.codingtest.srp.service.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <h2>class MoveRequestJson</h2>
 *
 * Player move model
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 23.07.2019
 */
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class Move {
    private Long gameId;
    private Gesture playerGesture;
}
