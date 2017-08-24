CREATE TABLE Player (
    PRIMARY KEY (player_id),
    player_id       INTEGER GENERATED BY DEFAULT AS IDENTITY,
    name            VARCHAR(100) CONSTRAINT player_name_UQ UNIQUE,
    x               INTEGER NOT NULL DEFAULT 0,
    y               INTEGER NOT NULL DEFAULT 0,
    timer           LONG NOT NULL,
    score           INTEGER NOT NULL DEFAULT 0,
    finished        BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE Artifact (
    PRIMARY KEY (artifact_id),
    artifact_id     INTEGER GENERATED BY DEFAULT AS IDENTITY,
    type            VARCHAR(100) NOT NULL,
    collected       BOOLEAN DEFAULT FALSE
);

CREATE TABLE Tile (
    PRIMARY KEY (tile_dummy_id),
        /* some sort of terrain type or value may need to be added.*/
    tile_dummy_id   INTEGER GENERATED BY DEFAULT AS IDENTITY,
    player_id       INTEGER NOT NULL CONSTRAINT Tile_Player_FK REFERENCES player,
    artifact        INTEGER NOT NULL, CONSTRAINT Tile_Artifact_FK REFERENCES artifact, 
    x               INTEGER NOT NULL,
    y               INTEGER NOT NULL,
    passable        BOOLEAN NOT NULL,
    elevation       FLOAT(53) NOT NULL DEFAULT                   0.5
);

CREATE TABLE GradientVector (
/* no primary key */
    player_id       INTEGER CONSTRAINT IDENTITY, CONSTRAINT Player_GradientVector_FK REFERENCES player, 
    x               INTEGER NOT NULL,
    y               INTEGER NOT NULL,
    direction       INTEGER NOT NULL direction_uq UNIQUE
);

CREATE TABLE Key (
    PRIMARY KEY (key_id),
    key_id          INTEGER GENERATED BY DEFAULT AS IDENTITY,
    api_key         VARCHAR(50) NOT NULL api_key_uq UNIQUE,
    public_key      VARCHAR(500) NOT NULL 
);

INSERT INTO Player
    (
        name,   
        x,
        y,
        time,
        finished
    );
VALUES
    (
        'Example Player Name',
        '1',
        '1',
        '0940',
        'false'
    ),
    (
        'Other Player Name',
        '2',
        '2',
        '1040',
        'false'
    ),
    (
        'Still Another Name',
        '3',
        '3',
        '0940',
        'false'
      );
    
INSERT INTO Artifact
    (
        type,
        x,
        y,
        collected,
        player_id 
    );
VALUES
    (
        'artifact 1',
        '1',
        '1',
        false,
        player_id
    ),
    (
        'artifact 2',
        '50',
        '50',
        'false',
        player_id    
    ),
    (
        'artifact 1',
        '100',
        '100',
        false,
        player_id    
    );
    
INSERT INTO Tile
    (
        x,
        y,
        passable,
        obscures             
    )
VALUES
    (
        '1',
        '1',
        'true',
        'true'
    ),
    (
        '50',
        '50',
        'true',
        'true'
    ),
    (
        '100',
        '100',
        'true',
        'true'
    );
    
INSERT INTO GradientVector
(
    x,
    y,
    direction
),
VALUES
    (
        1,
        1,
        1,0
    )
    
INSERT INTO Key
(
    api_key,
    public_key
),
VALUES
    (
        HJKL6780HJKL,
        JKL;JKL;78907890JKL;KJ,;890789
    )
    
SELECT
    p.player_id,
    a.artifact_id,
    t.tile_dummy_id
    
FROM
    Player AS p,
    Location AS a,
    Tile AS t
    
/*SELECT
    

FROM 
    Tile AS t,
    JOIN Player AS p,
        ON p.player_id = t.player_id

WHERE 
    p.player_id = n,
    AND
    ABS(t.x - p.x) <= r
    AND ABS(t.y - p.y) <= r
    
FROM
    Tile AS t
    JOIN Player AS p
        ON p.player_id = t.player_id
    JOIN Artifact AS a
        ON a.player_id = p.player_id
        AND a.x = t.x
        AND a.y = t.y
        AND NOT a.collected
    
DELETE

FROM 
    t.
*/
    
    
    
    
    
/*WHERE
    p.name in ()
    AND t.name = '';
    
SELECT
    p.name,
    p.x,
    p.y,
    p.time,
    p.finished;
FROM
    Player AS p
    JOIN Artifact AS a
        ON p.x = a.x
    JOIN Artifact AS a
        ON a.x = act.tag_id
WHERE
    p.name = 'Player1';
    
SELECT
    t.name,
    count(*)
FROM
    GROUP BY  
    t.name;
*/
