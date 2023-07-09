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
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class RiotController {

    private final RiotService riotService;
    private final SummonerService summonerService;
    private final ChampionMasteryService championMasteryService;

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
        if(riotService.isRegionInvalid(region)) {
            return ResponseEntity.badRequest().build();
        }

        Optional<SummonerDTO> summonerDTOOpt = riotService.getSummonerByRegionAndName(region, name);
        if(summonerDTOOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        SummonerDTO summonerDTO = summonerDTOOpt.get();
        Optional<Summoner> summonerOpt = summonerService.getSummonerByPuuid(summonerDTO.getPuuid());
        Summoner summoner = summonerOpt.orElse(Summoner.fromDTO(summonerDTO));
        summoner.updateFromDTO(summonerDTO);
        summoner.setRegion(region);
        summonerService.updateSummoner(summoner);
        return ResponseEntity.ok(summoner);
    }

    @PutMapping("/api/{region}/summoner/{puuid}")
    public ResponseEntity<Summoner> updateSummoner(
        @PathVariable String region,
        @PathVariable String puuid
    ) {
        if(riotService.isRegionInvalid(region)) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Summoner> summonerOpt = summonerService.getSummonerByPuuid(puuid);
        Optional<SummonerDTO> summonerDTOOpt = riotService.getSummonerByRegionAndPuuid(region, puuid);
        if(summonerOpt.isEmpty() || summonerDTOOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        SummonerDTO summonerDTO = summonerDTOOpt.get();
        Summoner summoner = summonerOpt.get();
        summoner.updateFromDTO(summonerDTO);

        List<ChampionMasteryDTO> masteryDTOList = riotService.getChampionMastery(region, summoner.getId());
        ArrayList<ChampionMastery> masteryList = new ArrayList<>(masteryDTOList.stream().map(ChampionMastery::fromDTO).toList());
        masteryList.forEach(mastery -> mastery.setSummoner(summoner));
        championMasteryService.deleteMasteriesBySummonerPuuid(puuid);
        summoner.setChampionMasteries(masteryList);

        summonerService.updateSummoner(summoner);
        return ResponseEntity.ok(summoner);
    }

    @GetMapping("/api/{region}/champion-mastery/{encryptedSummonerId}/top")
    public ResponseEntity<List<ChampionMasteryDTO>> getChampionMasteryTop3(
        @PathVariable String region,
        @PathVariable String encryptedSummonerId
    ) {
        if(riotService.isRegionInvalid(region)) {
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
        if(riotService.isRegionInvalid(region)) {
            return ResponseEntity.badRequest().build();
        }
        int takeTop = top.orElse(20);
        List<String> names = summonerService.getAllNamesStartingWith(startsWith, region, takeTop);
        return ResponseEntity.ok(names);
    }

}
