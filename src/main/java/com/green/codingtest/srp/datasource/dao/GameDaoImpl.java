package com.green.codingtest.srp.datasource.dao;

import com.green.codingtest.srp.datasource.dto.GameDto;
import com.green.codingtest.srp.datasource.rel.GameRel;
import lombok.NonNull;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.Optional;

import static org.jooq.impl.DSL.currentTimestamp;
import static org.jooq.impl.DSL.val;

/**
 * <h2>class GameDaoImpl</h2>
 *
 * Concrete {@link GameDao} implementation based on Jooq framework.
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 24.07.2019
 */
@Repository
public class GameDaoImpl implements GameDao {

    private static final String GAME_SEQ = "game_seq";

    @NonNull
    private final DSLContext dslContext;

    @Inject
    public GameDaoImpl(final DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public Optional<GameDto> findById(final Long id) {
        return dslContext
            .selectFrom(GameRel.INSTANCE)
            .where(GameRel.ID.eq(id))
            .fetchOptional()
            .map(this::recordToGameDto);
    }

    @Override
    public Long insert(final GameDto dto) {
        final Long gameId = dslContext.nextval(GAME_SEQ).longValue();

        dslContext
            .insertInto(GameRel.INSTANCE)
            .columns(
                GameRel.ID,
                GameRel.STATE,
                GameRel.CREATED_AT,
                GameRel.UPDATED_AT)
            .values(
                val(gameId),
                val(dto.getState()),
                currentTimestamp(),
                currentTimestamp())
            .execute();

        return gameId;
    }

    @Override
    public void updateState(final Long id, final String state) {
        dslContext
            .update(GameRel.INSTANCE)
            .set(GameRel.STATE, val(state))
            .set(GameRel.UPDATED_AT, currentTimestamp())
            .where(GameRel.ID.eq(id))
            .execute();
    }

    @Override
    public void touch(final Long id) {
        dslContext
            .update(GameRel.INSTANCE)
            .set(GameRel.UPDATED_AT, currentTimestamp())
            .where(GameRel.ID.eq(id))
            .execute();
    }

    private GameDto recordToGameDto(final Record record) {
        return new GameDto()
            .setId(record.get(GameRel.ID))
            .setState(record.get(GameRel.STATE))
            .setCreatedAt(record.get(GameRel.CREATED_AT))
            .setUpdatedAt(record.get(GameRel.UPDATED_AT));
    }
}
