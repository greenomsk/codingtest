package com.green.codingtest.srp.datasource.rel;

import org.jooq.Record;
import org.jooq.TableField;
import org.jooq.impl.TableImpl;

import java.sql.Timestamp;

import static org.jooq.impl.DSL.name;
import static org.jooq.impl.SQLDataType.BIGINT;
import static org.jooq.impl.SQLDataType.TIMESTAMP;
import static org.jooq.impl.SQLDataType.VARCHAR;

/**
 * class MoveLogRel
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 24.07.2019
 */
public class MoveLogRel extends TableImpl<Record> {

    /**
     * Static instance
     */
    public static final MoveLogRel INSTANCE = new MoveLogRel();

    /**
     * Column moves_log_id
     */
    public static final TableField<Record, Long> ID =
        createField("move_log_id", BIGINT, INSTANCE);

    /**
     * Column game_id
     */
    public static final TableField<Record, Long> GAME_ID =
        createField("game_id_fk", BIGINT, INSTANCE);

    /**
     * Column player_gesture
     */
    public static final TableField<Record, String> PLAYER_GESTURE =
        createField("player_gesture", VARCHAR, INSTANCE);

    /**
     * Column opponent_gesture
     */
    public static final TableField<Record, String> OPPONENT_GESTURE =
        createField("opponent_gesture", VARCHAR, INSTANCE);

    /**
     * Column result
     */
    public static final TableField<Record, String> RESULT =
        createField("result", VARCHAR, INSTANCE);

    /**
     * Column created_at
     */
    public static final TableField<Record, Timestamp> CREATED_AT =
        createField("created_at", TIMESTAMP, INSTANCE);

    /**
     * Default constructor
     */
    private MoveLogRel() {
        super(name("move_log"));
    }
}
