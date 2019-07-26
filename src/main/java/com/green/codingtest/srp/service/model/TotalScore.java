package com.green.codingtest.srp.service.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <h2>class TotalScore</h2>
 *
 * Total game score representation model
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 23.07.2019
 */
@Accessors(chain = true)
@Getter
@Setter
public class TotalScore {
    int playerPoints;
    int opponentPoints;
}
