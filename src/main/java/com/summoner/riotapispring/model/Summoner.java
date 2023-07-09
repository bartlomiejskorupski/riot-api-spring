package com.summoner.riotapispring.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "summoner")
public class Summoner {
    @Id
    @Column(name = "puuid", nullable = false)
    private String puuid;
    @Column(name = "region")
    private String region;
    @Column(name = "account_id")
    private String accountId;
    @Column(name = "profile_icon_id")
    private int profileIconId;
    @Column(name = "revision_date")
    private long revisionDate;
    @Column(name = "name", length = 50)
    private String name;
    @Column(name = "id", nullable = false, length = 63)
    private String id;
    @Column(name = "summoner_level")
    private long summonerLevel;
    @Column(name = "profile_icon_path", length = 256)
    private String profileIconPath;
    @OneToMany(mappedBy = "summoner", cascade = CascadeType.ALL)
    private List<ChampionMastery> championMasteries;

    public static Summoner fromDTO(SummonerDTO summonerDTO) {
        return Summoner.builder()
                .accountId(summonerDTO.getAccountId())
                .profileIconId(summonerDTO.getProfileIconId())
                .revisionDate(summonerDTO.getRevisionDate())
                .name(summonerDTO.getName())
                .id(summonerDTO.getId())
                .puuid(summonerDTO.getPuuid())
                .summonerLevel(summonerDTO.getSummonerLevel())
                .profileIconPath("/images/icons/" + summonerDTO.getProfileIconId() + ".png")
                .championMasteries(List.of())
                .build();
    }

    public void updateFromDTO(SummonerDTO dto) {
        this.accountId = dto.getAccountId();
        this.profileIconId = dto.getProfileIconId();
        this.revisionDate = dto.getRevisionDate();
        this.name = dto.getName();
        this.id = dto.getId();
        this.summonerLevel = dto.getSummonerLevel();
        this.profileIconPath = "/images/icons/" + dto.getProfileIconId() + ".png";
    }
}
