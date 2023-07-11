package com.summoner.riotapispring.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ChampionData {
    private Integer id;
    private String name;
    private String alias;
    private String squarePortraitPath;
    private String[] roles;
}
