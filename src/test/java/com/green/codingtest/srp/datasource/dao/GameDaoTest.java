package com.green.codingtest.srp.datasource.dao;

import com.green.codingtest.srp.DatasourceTestConfig;
import com.green.codingtest.srp.datasource.dto.GameDto;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.Optional;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * class GameDaoTest
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 24.07.2019
 */
@ContextConfiguration(
    classes = DatasourceTestConfig.class,
    loader = AnnotationConfigContextLoader.class
)
@Commit
public class GameDaoTest extends AbstractTransactionalTestNGSpringContextTests {

    @Inject
    private GameDao testee;

    private Long id;

    @BeforeClass
    public void setUp() {
        deleteFromTables("game");
    }

    @AfterClass
    public void cleanUp() {
        deleteFromTables("game");
    }

    @Test
    public void testInsert() {
        id = testee.insert(
            new GameDto()
                .setState("STARTED")
        );
        assertNotNull(id);
    }

    @Test(dependsOnMethods = "testInsert")
    public void testFindById() {
        final Optional<GameDto> gameDto = testee.findById(id);
        assertTrue(gameDto.isPresent());
        assertEquals(gameDto.get().getState(), "STARTED");
        assertNotNull(gameDto.get().getCreatedAt());
        assertNotNull(gameDto.get().getUpdatedAt());
    }

    @Test(dependsOnMethods = "testFindById")
    public void testUpdateState() {
        testee.updateState(id, "FINISHED");
        assertEquals(getField(id, "state", String.class), "FINISHED");
    }

    @Test(dependsOnMethods = "testUpdateState")
    public void testTouch() {
        final Timestamp prev = getField(id, "updated_at", Timestamp.class);
        testee.touch(id);
        assertNotEquals(getField(id, "updated_at", Timestamp.class), prev);
    }

    private <T> T getField(Long id, String fieldName, Class<T> clazz) {
        return jdbcTemplate.queryForObject(
            "select " + fieldName + " from game where game_id = ?", new Object[]{id}, clazz
        );
    }
}
