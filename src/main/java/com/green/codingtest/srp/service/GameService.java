package com.green.codingtest.srp.service;

import com.green.codingtest.srp.service.model.Game;
import com.green.codingtest.srp.service.model.Move;
import com.green.codingtest.srp.service.model.MoveResult;
import com.green.codingtest.srp.service.model.TotalScore;

/**
 * <h2>class GameService</h2>Main service tier class manages all necessary components to implement game flow.
 *
 * <br/><br/><h3>Game flow:</h3>
 * <ul>
 *     <li>First of all game should be started by calling {@link GameService#startNewGame()} method.</li>
 *     <li>On next step some moves can be made by calling {@link GameService#makeMove(Move)}}</li>
 *     <li>During game process {@link GameService#getTotalScore(Long)} can be called to get current game {@link TotalScore}</li>
 *     <li>To finish game {@link GameService#finishGame(Long)} should be called.
 *     After game finishing an attempt to make new move will lead to exception throwing</li>
 * </ul>
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 23.07.2019
 */
public interface GameService {
    /**
     * Starts a new game.
     * @return {@link Game} containing all necessary information to maintain game process
     */
    Game startNewGame();

    /**
     * Makes one move.
     * Be noted that making move in not existing or finished game will lead to runtime exception throwing.
     * @param playerMove {@link Move} class instance containing information about player gesture and game.
     * @return {@link MoveResult} computed result of the move.
     */
    MoveResult makeMove(Move playerMove);

    /**
     * Returns total score of game identified by gameId parameter.
     * @param gameId game identifier
     * @return game {@link TotalScore}
     */
    TotalScore getTotalScore(Long gameId);

    /**
     * Finished game identified by gameId parameter.
     * Be noted that finishing of already finished or not existing game will lead to runtime exception throwing
     * @param gameId game identifier.
     * @return game {@link TotalScore} of finished game.
     */
    TotalScore finishGame(Long gameId);
}
