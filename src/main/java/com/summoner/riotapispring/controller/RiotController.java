package com.summoner.riotapispring.controller;

import com.summoner.riotapispring.model.ChampionMasteryDTO;
import com.summoner.riotapispring.model.ChampionMastery;
import com.summoner.riotapispring.model.SummonerDTO;
import com.summoner.riotapispring.model.Summoner;
import com.summoner.riotapispring.model.leagueentry.LeagueEntry;
import com.summoner.riotapispring.model.leagueentry.LeagueEntryDTO;
import com.summoner.riotapispring.service.ChampionMasteryService;
import com.summoner.riotapispring.service.LeagueEntryService;
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
    private final LeagueEntryService leagueEntryService;

    @Autowired
    RiotController(
            RiotService riotService,
            SummonerService summonerService,
            ChampionMasteryService championMasteryService,
            LeagueEntryService leagueEntryService
    ) {
        this.riotService = riotService;
        this.summonerService = summonerService;
        this.championMasteryService = championMasteryService;
        this.leagueEntryService = leagueEntryService;
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

        // Update mastery
        List<ChampionMasteryDTO> masteryDTOList = riotService.getChampionMastery(region, summoner.getId());
        ArrayList<ChampionMastery> masteryList = new ArrayList<>(masteryDTOList.stream().map(ChampionMastery::fromDTO).toList());
        masteryList.forEach(mastery -> mastery.setSummoner(summoner));
        championMasteryService.deleteMasteriesBySummonerPuuid(puuid);
        summoner.setChampionMasteries(masteryList);

        // Update league entries
        List<LeagueEntryDTO> leagueEntryDTOList = riotService.getLeagueEtries(region, summoner.getId());
        List<LeagueEntry> leagueEntries = leagueEntryDTOList.stream().map(LeagueEntry::fromDTO).toList();
        leagueEntries.forEach(le -> {
            le.setSummonerPuuid(summoner.getPuuid());
            leagueEntryService.save(le);
        });

        summonerService.updateSummoner(summoner);
        return ResponseEntity.ok(summoner);
    }

    @GetMapping("/api/{region}/champion-mastery/{puuid}")
    public ResponseEntity<List<ChampionMastery>> getChampionMastery(
        @PathVariable String region,
        @PathVariable String puuid
    ) {
        if(riotService.isRegionInvalid(region)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Summoner> summonerOpt = summonerService.getSummonerByPuuid(puuid);
        if(summonerOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Summoner summoner = summonerOpt.get();
        List<ChampionMastery> masteryList = summoner.getChampionMasteries();
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
