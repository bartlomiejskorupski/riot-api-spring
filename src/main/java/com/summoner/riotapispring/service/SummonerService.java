package com.summoner.riotapispring.service;

import com.summoner.riotapispring.model.Summoner;
import com.summoner.riotapispring.repository.SummonerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SummonerService {

    private final SummonerRepository repository;

    @Autowired
    SummonerService(SummonerRepository repository) {
        this.repository = repository;
    }

    public Optional<Summoner> getSummonerByPuuid(String puuid) {
        return repository.findById(puuid);
    }

    public void updateSummoner(Summoner summoner) {
        repository.save(summoner);
    }
}
