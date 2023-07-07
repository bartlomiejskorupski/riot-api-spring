package com.summoner.riotapispring.service;

import com.summoner.riotapispring.repository.ChampionMasteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChampionMasteryService {
    private final ChampionMasteryRepository repository;

    @Autowired
    ChampionMasteryService(ChampionMasteryRepository repository) {
        this.repository = repository;
    }



}

