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
 * <h2>class GameRel</h2>
 *
 * Describes game table fields to operate with Jooq framework
 *
 * @author <a href="mailto:greenomsk@gmail.com">Andrey Grinenko</a>
 * @since 24.07.2019
 */
public class GameRel extends TableImpl<Record> {
    /**
     * Static instance
     */
    public static final GameRel INSTANCE = new GameRel();

    /**
     * Column moves_log_id
     */
    public static final TableField<Record, Long> ID =
        createField("game_id", BIGINT, INSTANCE);

    /**
     * Column state
     */
    public static final TableField<Record, String> STATE =
        createField("state", VARCHAR, INSTANCE);

    /**
     * Column created_at
     */
    public static final TableField<Record, Timestamp> CREATED_AT =
        createField("created_at", TIMESTAMP, INSTANCE);

    /**
     * Column updated_at
     */
    public static final TableField<Record, Timestamp> UPDATED_AT =
        createField("updated_at", TIMESTAMP, INSTANCE);

    /**
     * Default constructor
     */
    private GameRel() {
        super(name("game"));
    }

}
