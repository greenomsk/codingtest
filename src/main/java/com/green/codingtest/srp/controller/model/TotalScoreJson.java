package com.green.codingtest.srp.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * class TotalScoreJson
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 23.07.2019
 */
@ApiModel("Total game score")
@Accessors(chain = true)
@Getter
@Setter
public class TotalScoreJson {
    @ApiModelProperty("Player total points")
    @JsonProperty("player-points")
    int playerPoints;
    @ApiModelProperty("Opponent total points")
    @JsonProperty("opponent-points")
    int opponentPoints;
}
