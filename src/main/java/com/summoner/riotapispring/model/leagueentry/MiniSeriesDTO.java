package com.summoner.riotapispring.model.leagueentry;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MiniSeriesDTO {
    private Integer losses;
    private String progress;
    private Integer target;
    private Integer wins;
}
