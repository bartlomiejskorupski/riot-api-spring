package com.summoner.riotapispring.controller;

import com.summoner.riotapispring.model.SummonerDTO;
import com.summoner.riotapispring.service.RiotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RiotController {

    @Autowired
    private RiotService riotService;

    private final List<String> regions = List.of("br1", "eun1", "euw1", "jp1", "kr", "la1", "la2", "na1", "oc1", "tr1", "ru", "ph2", "sg2", "th2", "tw2", "vn2");

    @GetMapping("/api/summoner/{region}/{name}")
    public ResponseEntity<SummonerDTO> getSummonerByName(
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
        return ResponseEntity.ok(summoner);
    }

}
