CREATE TABLE IF NOT EXISTS "summoner" (
    "puuid" VARCHAR(78) NOT NULL PRIMARY KEY,
    "account_id" VARCHAR(56),
    "profile_icon_id" INT,
    "revision_date" TIMESTAMP,
    "name" VARCHAR(50),
    "id" VARCHAR(63) NOT NULL,
    "summoner_level" INT,
    "profile_icon_path" VARCHAR(256)
);

CREATE TABLE IF NOT EXISTS "champion_mastery" (
    "id" SERIAL PRIMARY KEY,
    "summoner_puuid" VARCHAR(78) NOT NULL,
    "champion_points_until_next_level" INT,
    "chest_granted" INT,
    "champion_id" INT,
    "last_play_time" TIMESTAMP,
    "champion_level" INT,
    "champion_points" INT,
    "champion_points_since_last_level" INT,
    "summoner_id" VARCHAR(63),
    "tokens_earned" INT,
    "champion_icon_path" VARCHAR(256),
    FOREIGN KEY ("summoner_puuid") REFERENCES "summoner"("puuid")
);