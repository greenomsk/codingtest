package com.green.codingtest.srp.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * class MoveResultJson
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 23.07.2019
 */
@ApiModel("JSON value representing result of one move")
@Accessors(chain = true)
@Getter
@Setter
public class MoveResultJson {
    @ApiModelProperty("Player gesture. Could be 'ROCK', 'SCISSORS' or 'PAPER'")
    @JsonProperty("player-gesture")
    private String playerGesture;
    @ApiModelProperty("Opponent gesture. Could be 'ROCK', 'SCISSORS' or 'PAPER'")
    @JsonProperty("opponent-gesture")
    private String opponentGesture;
    @ApiModelProperty("Move result. Could be 'WIN', 'LOSE' or 'DRAW'")
    @JsonProperty("result")
    private String result;
}
