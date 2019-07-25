CREATE SEQUENCE game_seq;

CREATE TABLE game (
    game_id BIGINT DEFAULT game_seq.nextval PRIMARY KEY,
    state VARCHAR(32),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT current_timestamp(),
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT current_timestamp()
);

COMMENT ON TABLE game IS 'Games';
COMMENT ON COLUMN game.game_id IS 'Unique identifier';
COMMENT ON COLUMN game.state IS 'Game state';
COMMENT ON COLUMN game.created_at IS 'Game created timestamp';
COMMENT ON COLUMN game.updated_at IS 'Game updated timestamp';

CREATE SEQUENCE move_log_seq;

CREATE TABLE move_log (
    move_log_id BIGINT DEFAULT move_log_seq.nextval PRIMARY KEY,
    game_id_fk BIGINT NOT NULL CONSTRAINT game_fk REFERENCES game(game_id),
    player_gesture VARCHAR(32),
    opponent_gesture VARCHAR(32),
    result VARCHAR(32),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT current_timestamp()
);

CREATE INDEX game_id_fk_ndx ON move_log(game_id_fk);

COMMENT ON TABLE move_log IS 'Game moves log';
COMMENT ON COLUMN move_log.move_log_id IS 'Unique identifier';
COMMENT ON COLUMN move_log.player_gesture IS 'Player gesture';
COMMENT ON COLUMN move_log.opponent_gesture IS 'Opponent gesture';
COMMENT ON COLUMN move_log.result IS 'Move result';
COMMENT ON COLUMN move_log.created_at IS 'Log record creation timestamp';
