package com.summoner.riotapispring.controller;

import com.summoner.riotapispring.model.Summoner;
import com.summoner.riotapispring.service.RiotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RiotController {

    @Autowired
    private RiotService riotService;

    @GetMapping("/api/summoner/{name}")
    public ResponseEntity<Summoner> getSummonerByName(
        @PathVariable String name
    ) {
        return riotService.getSummonerByName(name);
    }

}
