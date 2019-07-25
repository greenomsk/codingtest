package com.green.codingtest.srp.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * class MoveRequestJson
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 23.07.2019
 */
@ApiModel("JSON model representing player move")
@Accessors(chain = true)
@Getter
@Setter
public class MoveRequestJson {
    @ApiModelProperty(value = "Player gesture. Could be 'ROCK', 'SCISSORS' or 'PAPER'", required = true)
    @JsonProperty(value = "player-gesture", required = true)
    private String playerGesture;
}
