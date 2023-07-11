package com.summoner.riotapispring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="champion_mastery")
public class ChampionMastery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "summoner_puuid", nullable = false)
    @JsonIgnore
    private Summoner summoner;
    @Column(name = "champion_points_until_next_level")
    private long championPointsUntilNextLevel;
    @Column(name = "chest_granted")
    private boolean chestGranted;
    @Column(name = "champion_id")
    private long championId;
    @Column(name = "last_play_time")
    private long lastPlayTime;
    @Column(name = "champion_level")
    private int championLevel;
    @Column(name = "champion_points")
    private int championPoints;
    @Column(name = "champion_points_since_last_level")
    private long championPointsSinceLastLevel;
    @Column(name = "summoner_id")
    private String summonerId;
    @Column(name = "tokens_earned")
    private int tokensEarned;
    @Column(name = "champion_icon_path")
    private String championIconPath;

    @Transient
    private ChampionData championData;

    public static ChampionMastery fromDTO(ChampionMasteryDTO masteryDTO) {
        return ChampionMastery.builder()
                .championPointsUntilNextLevel(masteryDTO.getChampionPointsUntilNextLevel())
                .chestGranted(masteryDTO.isChestGranted())
                .lastPlayTime(masteryDTO.getLastPlayTime())
                .championId(masteryDTO.getChampionId())
                .championLevel(masteryDTO.getChampionLevel())
                .championPoints(masteryDTO.getChampionPoints())
                .championPointsSinceLastLevel(masteryDTO.getChampionPointsSinceLastLevel())
                .summonerId(masteryDTO.getSummonerId())
                .tokensEarned(masteryDTO.getTokensEarned())
                .championIconPath("https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-icons/" + masteryDTO.getChampionId() + ".png")
                .build();
    }

}
