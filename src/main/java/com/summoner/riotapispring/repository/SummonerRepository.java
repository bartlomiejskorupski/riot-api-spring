package com.summoner.riotapispring.repository;

import com.summoner.riotapispring.model.Summoner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SummonerRepository extends JpaRepository<Summoner, String> {
}
