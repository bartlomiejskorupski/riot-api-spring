package com.summoner.riotapispring.model;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChampionMasteryResponse {
    private long championPointsUntilNextLevel;
    private boolean chestGranted;
    private long championId;
    private long lastPlayTime;
    private int championLevel;
    private int championPoints;
    private long championPointsSinceLastLevel;
    private String summonerId;
    private int tokensEarned;
    private String championIconPath;

    public static ChampionMasteryResponse fromDTO(ChampionMasteryDTO masteryDTO) {
        return ChampionMasteryResponse.builder()
                .championPointsUntilNextLevel(masteryDTO.getChampionPointsUntilNextLevel())
                .chestGranted(masteryDTO.isChestGranted())
                .lastPlayTime(masteryDTO.getLastPlayTime())
                .championLevel(masteryDTO.getChampionLevel())
                .championPoints(masteryDTO.getChampionPoints())
                .championPointsSinceLastLevel(masteryDTO.getChampionPointsSinceLastLevel())
                .summonerId(masteryDTO.getSummonerId())
                .tokensEarned(masteryDTO.getTokensEarned())
                .championIconPath("https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-icons/" + masteryDTO.getChampionId() + ".png")
                .build();
    }

}
