package com.summoner.riotapispring.service;

import com.summoner.riotapispring.model.ChampionMastery;
import com.summoner.riotapispring.repository.ChampionMasteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChampionMasteryService {
    private final ChampionMasteryRepository repository;

    @Autowired
    ChampionMasteryService(ChampionMasteryRepository repository) {
        this.repository = repository;
    }

    public List<ChampionMastery> getMasteriesBySummonerPuuid(String puuid) {
        return repository.findAllBySummonerPuuid(puuid);
    }

    public void deleteMasteriesBySummonerPuuid(String puuid) {
        List<ChampionMastery> masteries = getMasteriesBySummonerPuuid(puuid);
        repository.deleteAll(masteries);
    }

}

