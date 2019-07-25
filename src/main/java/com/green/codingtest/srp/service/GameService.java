package com.green.codingtest.srp.service;

import com.green.codingtest.srp.service.model.Game;
import com.green.codingtest.srp.service.model.MoveResult;
import com.green.codingtest.srp.service.model.Move;
import com.green.codingtest.srp.service.model.TotalScore;

/**
 * class GameService
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 23.07.2019
 */
public interface GameService {
    Game startNewGame();
    MoveResult makeMove(Move playerMove);
    TotalScore getTotalScore(Long gameId);
    TotalScore finishGame(Long gameId);
}
