package com.green.codingtest.srp.datasource.dao;

import com.green.codingtest.srp.datasource.dto.MoveLogDto;
import com.green.codingtest.srp.datasource.rel.MoveLogRel;
import lombok.NonNull;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static org.jooq.impl.DSL.currentTimestamp;
import static org.jooq.impl.DSL.val;

/**
 * class MoveLogDaoImpl
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 23.07.2019
 */
@Repository
public class MoveLogDaoImpl implements MoveLogDao {

    private static final String MOVE_LOG_SEQ = "move_log_seq";

    @NonNull
    private final DSLContext dslContext;

    @Inject
    public MoveLogDaoImpl(final DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    @Override
    public List<MoveLogDto> getMoveLog(final Long gameId) {
        requireNonNull(gameId);
        return dslContext
            .selectFrom(MoveLogRel.INSTANCE)
            .where(MoveLogRel.GAME_ID.eq(gameId))
            .fetchStream()
            .map(MoveLogDaoImpl::record2MoveLogDto)
            .collect(toList());
    }

    @Override
    public Long insert(final MoveLogDto dto) {
        requireNonNull(dto);
        final Long id = dslContext.nextval(MOVE_LOG_SEQ).longValue();
        dslContext
            .insertInto(MoveLogRel.INSTANCE)
            .columns(
                MoveLogRel.ID,
                MoveLogRel.GAME_ID,
                MoveLogRel.PLAYER_GESTURE,
                MoveLogRel.OPPONENT_GESTURE,
                MoveLogRel.RESULT,
                MoveLogRel.CREATED_AT)
            .values(
                val(id),
                val(dto.getGameId()),
                val(dto.getPlayerGesture()),
                val(dto.getOpponentGesture()),
                val(dto.getResult()),
                currentTimestamp())
            .execute();
        return id;
    }

    private static MoveLogDto record2MoveLogDto(final Record record) {
        return new MoveLogDto()
            .setId(record.get(MoveLogRel.ID))
            .setGameId(record.get(MoveLogRel.GAME_ID))
            .setPlayerGesture(record.get(MoveLogRel.PLAYER_GESTURE))
            .setOpponentGesture(record.get(MoveLogRel.OPPONENT_GESTURE))
            .setResult(record.get(MoveLogRel.RESULT))
            .setCreatedAt(record.get(MoveLogRel.CREATED_AT));
    }
}
