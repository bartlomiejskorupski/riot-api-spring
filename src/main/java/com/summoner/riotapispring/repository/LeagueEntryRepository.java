package com.summoner.riotapispring.repository;

import com.summoner.riotapispring.model.leagueentry.LeagueEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LeagueEntryRepository extends JpaRepository<LeagueEntry, Integer> {

    @Query("SELECT le FROM LeagueEntry le WHERE le.summonerPuuid = :puuid")
    List<LeagueEntry> findAllBySummonerPuuid(@Param("puuid") String puuid);

}
