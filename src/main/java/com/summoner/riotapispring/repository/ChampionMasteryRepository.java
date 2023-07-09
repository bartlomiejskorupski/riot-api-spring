package com.summoner.riotapispring.repository;

import com.summoner.riotapispring.model.ChampionMastery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChampionMasteryRepository extends JpaRepository<ChampionMastery, Integer> {

    public List<ChampionMastery> findAllBySummonerPuuid(String puuid);

}
