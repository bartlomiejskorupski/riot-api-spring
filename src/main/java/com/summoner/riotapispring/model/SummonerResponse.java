package com.summoner.riotapispring.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SummonerResponse {
    private String accountId;
    private int profileIconId;
    private long revisionDate;
    private String name;
    private String id;
    private String puuid;
    private long summonerLevel;
    private String profileIconPath;
    private List<ChampionMasteryResponse> championMasteries;

    public static SummonerResponse fromDTO(SummonerDTO summonerDTO) {
        return SummonerResponse.builder()
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
}
