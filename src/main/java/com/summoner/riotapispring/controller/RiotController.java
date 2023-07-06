package com.summoner.riotapispring.controller;

import com.summoner.riotapispring.model.ChampionMasteryDTO;
import com.summoner.riotapispring.model.ChampionMasteryResponse;
import com.summoner.riotapispring.model.SummonerDTO;
import com.summoner.riotapispring.model.SummonerResponse;
import com.summoner.riotapispring.service.RiotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RiotController {

    @Autowired
    private RiotService riotService;

    private final List<String> regions = List.of("br1", "eun1", "euw1", "jp1", "kr", "la1", "la2", "na1", "oc1", "tr1", "ru", "ph2", "sg2", "th2", "tw2", "vn2");

    @GetMapping("/api/{region}/summoner/{name}")
    public ResponseEntity<SummonerResponse> getSummonerByName(
        @PathVariable String region,
        @PathVariable String name
    ) {
        if(!regions.contains(region)) {
            return ResponseEntity.badRequest().build();
        }
        SummonerDTO summoner = riotService.getSummonerByRegionAndName(region, name);
        if(summoner == null) {
            return ResponseEntity.notFound().build();
        }
        SummonerResponse summonerResponse = SummonerResponse.fromDTO(summoner);
        List<ChampionMasteryDTO> masteryList = riotService.getChampionMasteryTop(region, summoner.getId(), 3);
        if (masteryList == null) {
            masteryList = List.of();
        }
        List<ChampionMasteryResponse> masteryResponseList = masteryList.stream()
                .map(ChampionMasteryResponse::fromDTO)
                .toList();
        summonerResponse.setChampionMasteries(masteryResponseList);
        return ResponseEntity.ok(summonerResponse);
    }

    @GetMapping("/api/{region}/champion-mastery/{encryptedSummonerId}/top")
    public ResponseEntity<List<ChampionMasteryDTO>> getChampionMasteryTop3(
        @PathVariable String region,
        @PathVariable String encryptedSummonerId
    ) {
        if(!regions.contains(region)) {
            return ResponseEntity.badRequest().build();
        }
        List<ChampionMasteryDTO> masteryList = riotService.getChampionMasteryTop(region, encryptedSummonerId, 3);
        if (masteryList == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(masteryList);
    }

}
