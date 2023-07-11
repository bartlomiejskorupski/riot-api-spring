CREATE TABLE IF NOT EXISTS "league_entry" (
    "league_id" VARCHAR(100) PRIMARY KEY,
    "summoner_puuid" VARCHAR(78) NOT NULL,
    "queue_type" VARCHAR(20),
    "tier" VARCHAR(20),
    "rank" VARCHAR(20),
    "league_points" INT,
    "wins" INT,
    "losses" INT,
    "hot_streak" BOOLEAN,
    "veteran" BOOLEAN,
    "fresh_blood" BOOLEAN,
    "inactive" BOOLEAN,
    "series_wins" INT,
    "series_losses" INT,
    "series_progress" VARCHAR(200),
    "series_target" INT,
    FOREIGN KEY ("summoner_puuid") REFERENCES "summoner"("puuid")
);