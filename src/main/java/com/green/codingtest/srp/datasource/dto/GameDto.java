package com.green.codingtest.srp.datasource.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

/**
 * class GameDto
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 24.07.2019
 */
@Accessors(chain = true)
@Getter
@Setter
public class GameDto {
    private Long id;
    private String state;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
