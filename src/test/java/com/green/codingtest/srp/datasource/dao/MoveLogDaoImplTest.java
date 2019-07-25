package com.green.codingtest.srp.datasource.dao;

import com.green.codingtest.srp.DatasourceTestConfig;
import com.green.codingtest.srp.datasource.dto.MoveLogDto;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * class MoveLogDaoImplTest
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 24.07.2019
 */

@ContextConfiguration(
    classes = DatasourceTestConfig.class,
    loader = AnnotationConfigContextLoader.class
)
@Commit
public class MoveLogDaoImplTest extends AbstractTransactionalTestNGSpringContextTests {

    @Inject
    private MoveLogDao testee;

    @BeforeClass
    public void setUp() {
        deleteFromTables("move_log", "game");
        insertDefaultGame();
    }

    @AfterClass
    public void cleanUp() {
        deleteFromTables("move_log", "game");
    }

    @Test
    public void testInsert() {
        final MoveLogDto dto = new MoveLogDto()
            .setGameId(1L)
            .setPlayerGesture("player gesture")
            .setOpponentGesture("opponent gesture")
            .setResult("result");

        Long id = testee.insert(dto);

        assertNotNull(id);
        assertEquals(getField(id, "game_id_fk", Long.class).longValue(), 1L);
        assertEquals(getField(id, "player_gesture", String.class), "player gesture");
        assertEquals(getField(id, "opponent_gesture", String.class), "opponent gesture");
        assertEquals(getField(id, "result", String.class), "result");
        assertNotNull(getField(id, "created_at", String.class));
    }

    @Test(dependsOnMethods = "testInsert")
    public void testGetMoveLog() {
        final List<MoveLogDto> log = testee.getMoveLog(1L);
        assertEquals(log.size(), 1);
        final MoveLogDto dto = log.get(0);
        assertNotNull(dto.getId());
        assertNotNull(dto.getGameId());
        assertEquals(dto.getPlayerGesture(), "player gesture");
        assertEquals(dto.getOpponentGesture(), "opponent gesture");
        assertEquals(dto.getResult(), "result");
        assertNotNull(dto.getCreatedAt());
    }

    private void insertDefaultGame() {
        jdbcTemplate.update("insert into game(game_id, state) values(1, 'STARTED')");
    }

    private <T> T getField(Long id, String fieldName, Class<T> clazz) {
        return jdbcTemplate.queryForObject(
            "select " + fieldName + " from move_log where move_log_id = ?", new Object[]{id}, clazz
        );
    }
}
