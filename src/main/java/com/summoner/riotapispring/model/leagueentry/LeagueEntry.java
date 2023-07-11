package com.summoner.riotapispring.model.leagueentry;

import jakarta.persistence.*;
import lombok.*;

import java.text.DecimalFormat;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "league_entry")
@Builder
public class LeagueEntry {
    @Id
    @Column(name = "league_id")
    private String leagueId;
    @Column(name = "summoner_puuid")
    private String summonerPuuid;
    @Column(name = "queue_type")
    private String queueType;
    @Column(name = "tier")
    private String tier;
    @Column(name = "rank")
    private String rank;
    @Column(name = "league_points")
    private Integer leaguePoints;
    @Column(name = "wins")
    private Integer wins;
    @Column(name = "losses")
    private Integer losses;
    @Column(name = "hot_streak")
    private Boolean hotStreak;
    @Column(name = "veteran")
    private Boolean veteran;
    @Column(name = "fresh_blood")
    private Boolean freshBlood;
    @Column(name = "inactive")
    private Boolean inactive;
    @Column(name = "series_wins")
    private Integer seriesWins;
    @Column(name = "series_losses")
    private Integer seriesLosses;
    @Column(name = "series_progress")
    private String seriesProgress;
    @Column(name = "series_target")
    private Integer seriesTarget;

    @Transient
    public String getEmblemUrl() {
        String url = "https://raw.communitydragon.org/latest/plugins/rcp-fe-lol-static-assets/global/default/images/ranked-emblem/";
        return url + "emblem-" + tier.toLowerCase() + ".png";
    }

    @Transient
    public String getWinRatio() {
        float wr = 100.0f*wins/(wins+losses);
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        return df.format(wr) + "%";
    }

    public static LeagueEntry fromDTO(LeagueEntryDTO dto) {
        LeagueEntryBuilder leb = LeagueEntry.builder()
                .leagueId(dto.getLeagueId())
                .queueType(dto.getQueueType())
                .tier(dto.getTier())
                .rank(dto.getRank())
                .leaguePoints(dto.getLeaguePoints())
                .wins(dto.getWins())
                .losses(dto.getLosses())
                .hotStreak(dto.getHotStreak())
                .veteran(dto.getVeteran())
                .freshBlood(dto.getVeteran())
                .inactive(dto.getInactive());
        if(dto.getMiniSeries() == null) {
            return leb.build();
        }
        return leb.seriesWins(dto.getMiniSeries().getWins())
                .seriesLosses(dto.getMiniSeries().getLosses())
                .seriesProgress(dto.getMiniSeries().getProgress())
                .seriesTarget(dto.getMiniSeries().getTarget())
                .build();
    }
}
