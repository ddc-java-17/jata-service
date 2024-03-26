CREATE OR REPLACE FORCE VIEW shot_status AS SELECT
        sh.SHOT_ID,
       CASE WHEN sl.SHIP_ID IS NULL THEN false ELSE true END AS hit
FROM shot AS sh
         JOIN user_game AS ug
              ON ug.USER_GAME_ID = sh.TO_USER_GAME_ID
         LEFT JOIN ship_location AS sl
                   ON ug.USER_GAME_ID = sl.USER_GAME_ID
                       AND sl.X = sh.X
                       AND sl.Y = sh.Y;
