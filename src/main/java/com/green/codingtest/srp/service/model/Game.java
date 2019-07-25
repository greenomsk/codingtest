package com.green.codingtest.srp.service.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * class Game
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 24.07.2019
 */
@Accessors(chain = true)
@Getter
@Setter
public class Game {
    private Long id;
    private GameState state;
}
