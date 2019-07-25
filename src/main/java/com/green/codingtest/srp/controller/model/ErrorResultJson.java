package com.green.codingtest.srp.controller.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * class ErrorResultJson
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 23.07.2019
 */
@ApiModel("JSON model representing error result")
@Accessors(chain = true)
@Getter
@Setter
public class ErrorResultJson {
    @ApiModelProperty("General error code. I.e. HTTP error status code.")
    @JsonProperty("error-code")
    private String code;
    @ApiModelProperty("Error subcode. I.e. system specific error code.")
    @JsonProperty("error-sub-code")
    private String subCode;
    @ApiModelProperty("Error details message.")
    @JsonProperty("error-message")
    private String message;
}
