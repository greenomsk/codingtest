package com.green.codingtest.srp.service;

import com.green.codingtest.srp.datasource.dao.GameDao;
import com.green.codingtest.srp.datasource.dao.MoveLogDao;
import com.green.codingtest.srp.datasource.dto.GameDto;
import com.green.codingtest.srp.datasource.dto.MoveLogDto;
import com.green.codingtest.srp.service.exception.GameNotFoundException;
import com.green.codingtest.srp.service.exception.ValidationException;
import com.green.codingtest.srp.service.model.Game;
import com.green.codingtest.srp.service.model.GameState;
import com.green.codingtest.srp.service.model.Gesture;
import com.green.codingtest.srp.service.model.Move;
import com.green.codingtest.srp.service.model.MoveResult;
import com.green.codingtest.srp.service.model.Result;
import com.green.codingtest.srp.service.model.TotalScore;
import com.green.codingtest.srp.service.strategy.GameStrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.mutable.MutableInt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * <h2>class GameServiceImpl</h2>
 *
 * {@link GameService} concrete implementation. Contains and manages all necessary information to provide game flow.
 * I.e. game rule, game strategy, DAO objects to store game flow information.
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 23.07.2019
 */
@Slf4j
@Service
public class GameServiceImpl implements GameService {

    private final MoveLogDao moveLogDao;

    private final GameDao gameDao;

    private final GameStrategyFactory strategyFactory;

    private final GameRules gameRules;

    /**
     * class constructor
     *
     * @param moveLogDao {@link MoveLogDao} DAO object intended to access to all moves related to game.
     * @param gameDao {@link GameDao} DAO object intended to access game information.
     * @param strategyFactory {@link GameStrategyFactory} intended to access currently choosen
     * {@link com.green.codingtest.srp.service.strategy.GameStrategy}
     * @param gameRules {@link GameRules} game rules.
     */
    @Inject
    public GameServiceImpl(
        final MoveLogDao moveLogDao,
        final GameDao gameDao,
        final GameStrategyFactory strategyFactory,
        final GameRules gameRules
    ) {
        this.moveLogDao = moveLogDao;
        this.gameDao = gameDao;
        this.strategyFactory = strategyFactory;
        this.gameRules = gameRules;
    }

    @Override
    @Transactional
    public Game startNewGame() {
        log.info("Start new game");

        final Long gameId = gameDao.insert(
            new GameDto()
                .setState(GameState.STARTED.name())
        );
        final Game result = getGame(gameId);

        log.info("New game {} started", result.getId());
        return result;
    }

    @Override
    @Transactional
    public TotalScore finishGame(final Long gameId) {
        log.info("Finish game {}", gameId);

        final Game game = getGame(gameId);
        checkGame(game);

        gameDao.updateState(gameId, GameState.FINISHED.name());

        log.info("Game {} finished", gameId);
        return getTotalScore(gameId);
    }

    @Override
    @Transactional
    public MoveResult makeMove(final Move playerMove) {

        log.info("Player move {} processing", playerMove);

        final Long gameId = playerMove.getGameId();

        final Game game = getGame(gameId);
        checkGame(game);

        final Gesture playerGesture = playerMove.getPlayerGesture();
        final Gesture opponentGesture = strategyFactory.getGameStrategy().throwGesture(gameId);
        final Result result = gameRules.computeResult(playerGesture, opponentGesture);

        moveLogDao.insert(
            new MoveLogDto()
                .setGameId(game.getId())
                .setPlayerGesture(playerGesture.name())
                .setOpponentGesture(opponentGesture.name())
                .setResult(result.name())
        );

        gameDao.touch(game.getId());

        final MoveResult moveResult = new MoveResult()
            .setPlayerGesture(playerGesture)
            .setOpponentGesture(opponentGesture)
            .setResult(result);

        log.info("Player move {} processing finished with result {}", playerMove, moveResult);

        return moveResult;
    }

    @Override
    @Transactional
    public TotalScore getTotalScore(final Long gameId) {
        log.info("Total score request processing for game {}", gameId);
        final Game game = getGame(gameId);
        final TotalScore result = getGameTotalScore(game);
        log.info("Total score request successfully processed for game {}", gameId);
        return result;
    }

    private TotalScore getGameTotalScore(final Game game) {
        final TotalScore result = new TotalScore();
        final List<MoveLogDto> moveLog = moveLogDao.getMoveLog(game.getId());

        final MutableInt playerWins = new MutableInt(0);
        final MutableInt playerLooses = new MutableInt(0);

        moveLog.stream()
            .map(MoveLogDto::getResult)
            .map(Result::valueOf)
            .forEach(
                i -> {
                    if (i == Result.WIN) {
                        playerWins.increment();
                    } else if (i == Result.LOSE) {
                        playerLooses.increment();
                    }
                }
            );

        return result
            .setPlayerPoints(playerWins.getValue())
            .setOpponentPoints(playerLooses.getValue());
    }

    private Game getGame(final Long gameId) {
        return gameDao
            .findById(gameId)
            .map(this::dtoToGame)
            .orElseThrow(() -> new GameNotFoundException(gameId));
    }

    private void checkGame(final Game game) {
        if (game.getState() == GameState.FINISHED) {
            throw new ValidationException("Game with id=" + game.getId() + " has been finished already");
        }
    }

    private Game dtoToGame(final GameDto dto) {
        return new Game()
            .setId(dto.getId())
            .setState(GameState.valueOf(dto.getState()));
    }
}
