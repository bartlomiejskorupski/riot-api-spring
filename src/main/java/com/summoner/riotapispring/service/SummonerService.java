package com.summoner.riotapispring.service;

import com.summoner.riotapispring.model.Summoner;
import com.summoner.riotapispring.repository.SummonerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<String> getAllNamesStartingWith(String startsWith, String region, int top) {
        return repository.getAllNamesStartingWith(startsWith, region, top);
    }

}
