package com.green.codingtest.srp.controller.rest;

import com.green.codingtest.srp.controller.model.ErrorResultJson;
import com.green.codingtest.srp.controller.model.MoveRequestJson;
import com.green.codingtest.srp.controller.model.MoveResultJson;
import com.green.codingtest.srp.controller.model.TotalScoreJson;
import com.green.codingtest.srp.service.GameService;
import com.green.codingtest.srp.service.ValidationService;
import com.green.codingtest.srp.service.model.Game;
import com.green.codingtest.srp.service.model.Move;
import com.green.codingtest.srp.service.model.MoveResult;
import com.green.codingtest.srp.service.model.TotalScore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

/**
 * class GameController
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 23.07.2019
 */
@RestController
@RequestMapping(
    value = "/1.0/scissors-rock-paper",
    produces = APPLICATION_JSON_UTF8_VALUE
)
@Api(tags = "scissors-rock-paper", description = "Scissors-Rock-Paper code test game REST API")
public class GameController {

    @NonNull
    private final GameService gameService;

    @NonNull
    private final ValidationService validationService;

    @NonNull
    private final ModelMapper modelMapper;

    @Inject
    public GameController(
        final GameService gameService,
        final ValidationService validationService,
        final ModelMapper modelMapper
    ) {
        this.gameService = gameService;
        this.validationService = validationService;
        this.modelMapper = modelMapper;
    }

    @ApiOperation(value = "Start new game", response = Long.class)
    @ApiResponses({
        @ApiResponse(code = 200, message = "New game has been successfully created", response = Long.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ErrorResultJson.class)
    })
    @PutMapping("/start-new-game")
    @ResponseBody
    public ResponseEntity<Long> startNewGame() {

        final Game game = gameService.startNewGame();

        return ResponseEntity.ok(game.getId());
    }

    @ApiOperation(value = "Finish game by its ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "New game has been successfully finished", response = TotalScoreJson.class),
        @ApiResponse(code = 400, message = "Bad request. E.g. if game not in 'STARTED' state", response = ErrorResultJson.class),
        @ApiResponse(code = 404, message = "Game not found", response = ErrorResultJson.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ErrorResultJson.class)
    })
    @PostMapping("/{game-id}/finish-game")
    @ResponseBody
    public ResponseEntity<TotalScoreJson> finishGame(
        @ApiParam(value = "Game identifier got after start-new-game call", required = true)
        @PathVariable("game-id") final Long gameId
    ) {
        final TotalScore totalScore = gameService.finishGame(gameId);

        final TotalScoreJson totalScoreJson = modelMapper.map(totalScore, TotalScoreJson.class);

        return ResponseEntity.ok(totalScoreJson);
    }

    @ApiOperation(value = "Make one move")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Move has been successfully made", response = MoveResultJson.class),
        @ApiResponse(code = 400, message = "Bad request. E.g. if game not in 'STARTED' state", response = ErrorResultJson.class),
        @ApiResponse(code = 404, message = "Game not found", response = ErrorResultJson.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ErrorResultJson.class)
    })
    @PutMapping("/{game-id}/make-move")
    @ResponseBody
    public ResponseEntity<MoveResultJson> makeMove(
        @ApiParam(value = "Game identifier got after start-new-game call", required = true)
        @PathVariable("game-id") final Long gameId,
        @RequestBody final MoveRequestJson moveRequestJson
    ) {

        requireNonNull(moveRequestJson);

        validationService.validate(moveRequestJson::getPlayerGesture);

        final Move move = modelMapper.map(moveRequestJson, Move.class);
        move.setGameId(gameId);

        final MoveResult gameResult = gameService.makeMove(move);
        final MoveResultJson moveResultJson = modelMapper.map(gameResult, MoveResultJson.class);

        return ResponseEntity.ok(moveResultJson);
    }

    @ApiOperation(value = "Make one move")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Total score", response = TotalScoreJson.class),
        @ApiResponse(code = 400, message = "Bad request. E.g. if game not in 'STARTED' state", response = ErrorResultJson.class),
        @ApiResponse(code = 404, message = "Game not found", response = ErrorResultJson.class),
        @ApiResponse(code = 500, message = "Internal server error", response = ErrorResultJson.class)
    })
    @GetMapping("/{game-id}/total-score")
    @ResponseBody
    public ResponseEntity<TotalScoreJson> getTotalScore(
        @ApiParam(value = "Game identifier got after start-new-game call", required = true)
        @PathVariable("game-id") final Long gameId
    ) {
        final TotalScore totalScore = gameService.getTotalScore(gameId);
        final TotalScoreJson totalScoreJson = modelMapper.map(totalScore, TotalScoreJson.class);

        return ResponseEntity.ok(totalScoreJson);
    }
}
