package com.green.codingtest.srp.service.strategy;

import com.green.codingtest.srp.datasource.dao.MoveLogDao;
import com.green.codingtest.srp.datasource.dto.MoveLogDto;
import com.green.codingtest.srp.service.GameRules;
import com.green.codingtest.srp.service.model.Gesture;
import org.hamcrest.Matchers;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.green.codingtest.srp.service.model.Gesture.PAPER;
import static com.green.codingtest.srp.service.model.Gesture.ROCK;
import static com.green.codingtest.srp.service.model.Gesture.SCISSORS;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

/**
 * class MarkovChainStrategyTest
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 25.07.2019
 */
public class MarkovChainStrategyTest {

    private GameRules rulesMock;
    private MoveLogDao moveLogDaoMock;
    private GameStrategy testee;

    @BeforeClass
    public void setUp() {

        rulesMock = Mockito.mock(GameRules.class);
        moveLogDaoMock = Mockito.mock(MoveLogDao.class);

        when(rulesMock.computePlayerLoseGesture(PAPER)).thenReturn(SCISSORS);
        when(rulesMock.computePlayerLoseGesture(SCISSORS)).thenReturn(ROCK);
        when(rulesMock.computePlayerLoseGesture(ROCK)).thenReturn(PAPER);

        testee = new MarkovChainStrategy(
            moveLogDaoMock,
            rulesMock
        );
    }

    @DataProvider
    Object[][] testThrowGestureDataProvider() {
        return new Object[][] {
            {emptyList(), PAPER},
            {singletonList(dto(PAPER, 0)), SCISSORS},
            {asList(dto(ROCK, 1), dto(PAPER, 2), dto(ROCK, 0)), PAPER}
        };
    }

    @Test(dataProvider = "testThrowGestureDataProvider")
    public void testThrowGesture(
        final List<MoveLogDto> moveLog,
        final Gesture expectedGesture
    ) {
        when(moveLogDaoMock.getMoveLog(eq(0L))).thenReturn(moveLog);
        final Gesture computedGesture = testee.throwGesture(0L);
        assertEquals(computedGesture, expectedGesture);
    }

    private static MoveLogDto dto(final Gesture playerGesture, final int order) {
        return new MoveLogDto()
            .setPlayerGesture(playerGesture.name())
            .setCreatedAt(new Timestamp(System.currentTimeMillis() + order));
    }
}
