package com.summoner.riotapispring.repository;

import com.summoner.riotapispring.model.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SummonerRepository extends JpaRepository<Summoner, String> {

    @Query("SELECT s.name FROM Summoner s WHERE LOWER(REPLACE(s.name, ' ', '')) LIKE CONCAT(LOWER(REPLACE(:startsWith, ' ', '')), '%')")
    List<String> getAllNamesStartingWith(@Param("startsWith") String startsWith);

}
