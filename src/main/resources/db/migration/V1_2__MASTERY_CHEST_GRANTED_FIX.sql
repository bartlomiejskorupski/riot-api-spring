ALTER TABLE "champion_mastery"
    ALTER COLUMN "chest_granted" TYPE BOOLEAN
    USING "chest_granted"::boolean;