package com.summoner.riotapispring.service;

import com.summoner.riotapispring.model.leagueentry.LeagueEntry;
import com.summoner.riotapispring.repository.LeagueEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeagueEntryService {
    private final LeagueEntryRepository repository;

    @Autowired
    LeagueEntryService(LeagueEntryRepository repository) {
        this.repository = repository;
    }

    public List<LeagueEntry> getBySummonerPuuid(String puuid) {
        return repository.findAllBySummonerPuuid(puuid);
    }

    public LeagueEntry save(LeagueEntry leagueEntry) {
        return repository.save(leagueEntry);
    }

}
