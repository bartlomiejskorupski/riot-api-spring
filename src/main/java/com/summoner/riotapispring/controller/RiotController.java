package com.summoner.riotapispring.controller;

import com.summoner.riotapispring.model.ChampionMasteryDTO;
import com.summoner.riotapispring.model.ChampionMastery;
import com.summoner.riotapispring.model.SummonerDTO;
import com.summoner.riotapispring.model.Summoner;
import com.summoner.riotapispring.service.ChampionMasteryService;
import com.summoner.riotapispring.service.RiotService;
import com.summoner.riotapispring.service.SummonerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class RiotController {

    private final RiotService riotService;
    private final SummonerService summonerService;
    private final ChampionMasteryService championMasteryService;
    private final List<String> regions = List.of("br1", "eun1", "euw1", "jp1", "kr", "la1", "la2", "na1", "oc1", "tr1", "ru", "ph2", "sg2", "th2", "tw2", "vn2");

    @Autowired
    RiotController(RiotService riotService, SummonerService summonerService, ChampionMasteryService championMasteryService) {
        this.riotService = riotService;
        this.summonerService = summonerService;
        this.championMasteryService = championMasteryService;
    }

    @GetMapping("/api/{region}/summoner/{name}")
    public ResponseEntity<Summoner> getSummonerByName(
        @PathVariable String region,
        @PathVariable String name
    ) {
        if(!regions.contains(region)) {
            return ResponseEntity.badRequest().build();
        }

        SummonerDTO summonerDTO = riotService.getSummonerByRegionAndName(region, name);
        if(summonerDTO == null) {
            return ResponseEntity.notFound().build();
        }

        Optional<Summoner> summonerOpt = summonerService.getSummonerByPuuid(summonerDTO.getPuuid());
        Summoner summoner = summonerOpt.orElse(Summoner.fromDTO(summonerDTO));
        summoner.updateFromDTO(summonerDTO);
        summoner.setRegion(region);
        summonerService.updateSummoner(summoner);
//        List<ChampionMasteryDTO> masteryList = riotService.getChampionMasteryTop(region, summoner.getId(), 3);
//        if (masteryList == null) {
//            masteryList = List.of();
//        }
//        List<ChampionMastery> masteryResponseList = masteryList.stream()
//                .map(ChampionMastery::fromDTO)
//                .toList();
//        summoner.setChampionMasteries(masteryResponseList);
        return ResponseEntity.ok(summoner);
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

    @GetMapping("/api/{region}/summoner-name/starts-with")
    public ResponseEntity<List<String>> getSummonerNamesStartingWith(
            @PathVariable String region,
            @Param("startsWith") String startsWith,
            @Param("top") Optional<Integer> top
    ) {
        if(!regions.contains(region)) {
            return ResponseEntity.badRequest().build();
        }
        int takeTop = top.orElse(20);
        List<String> names = summonerService.getAllNamesStartingWith(startsWith, region, takeTop);
        return ResponseEntity.ok(names);
    }

}
