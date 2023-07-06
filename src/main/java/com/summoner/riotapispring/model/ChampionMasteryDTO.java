package com.summoner.riotapispring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ChampionMasteryDTO {
    private long championPointsUntilNextLevel;
    private boolean chestGranted;
    private long championId;
    private long lastPlayTime;
    private int championLevel;
    private int championPoints;
    private long championPointsSinceLastLevel;
    private String summonerId;
    private int tokensEarned;
}
