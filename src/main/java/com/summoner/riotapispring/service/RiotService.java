package com.summoner.riotapispring.service;

import com.summoner.riotapispring.model.ChampionMasteryDTO;
import com.summoner.riotapispring.model.SummonerDTO;
import com.summoner.riotapispring.model.leagueentry.LeagueEntryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RiotService {

    @Value("${api.key}")
    private String api_key;

    @Value("${api.url.format}")
    private String urlFormat;

    private final RestTemplate restTemplate;

    private final List<String> regions = List.of("br1", "eun1", "euw1", "jp1", "kr", "la1", "la2", "na1", "oc1", "tr1", "ru", "ph2", "sg2", "th2", "tw2", "vn2");

    @Autowired
    public RiotService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Optional<SummonerDTO> getSummonerByRegionAndName(String region, String name) {
        try {
            String endpoint = "/lol/summoner/v4/summoners/by-name/" + name;
            ResponseEntity<SummonerDTO> summonerResponse = getRiotEndpoint(region, endpoint, SummonerDTO.class);
            return Optional.ofNullable(summonerResponse.getBody());
        } catch (Exception ex) {
            System.out.println(ex);
            return Optional.empty();
        }
    }

    public Optional<SummonerDTO> getSummonerByRegionAndPuuid(String region, String puuid) {
        try {
            String endpoint = "/lol/summoner/v4/summoners/by-puuid/" + puuid;
            ResponseEntity<SummonerDTO> summonerResponse = getRiotEndpoint(region, endpoint, SummonerDTO.class);
            return Optional.ofNullable(summonerResponse.getBody());
        } catch (Exception ex) {
            System.out.println(ex);
            return Optional.empty();
        }
    }

    public List<ChampionMasteryDTO> getChampionMasteryTop(String region, String encryptedSummonerId, int count) {
        try {
            String endpoint = "/lol/champion-mastery/v4/champion-masteries/by-summoner/" + encryptedSummonerId + "/top";
            ResponseEntity<ChampionMasteryDTO[]> masteryResponse = getRiotEndpoint(region, endpoint, ChampionMasteryDTO[].class);
            return List.of(Objects.requireNonNull(masteryResponse.getBody()));
        } catch (Exception ex) {
            System.out.println(ex);
            return List.of();
        }
    }

    public List<ChampionMasteryDTO> getChampionMastery(String region, String encryptedSummonerId) {
        try {
            String endpoint = "/lol/champion-mastery/v4/champion-masteries/by-summoner/" + encryptedSummonerId;
            ResponseEntity<ChampionMasteryDTO[]> masteryResponse = getRiotEndpoint(region, endpoint, ChampionMasteryDTO[].class);
            return List.of(Objects.requireNonNull(masteryResponse.getBody()));
        } catch (Exception ex) {
            System.out.println(ex);
            return List.of();
        }
    }

    public List<LeagueEntryDTO> getLeagueEtries(String region, String encryptedSummonerId) {
        try {
            String endpoint = "/lol/league/v4/entries/by-summoner/" + encryptedSummonerId;
            ResponseEntity<LeagueEntryDTO[]> response = getRiotEndpoint(region, endpoint, LeagueEntryDTO[].class);
            return List.of(Objects.requireNonNull(response.getBody()));
        } catch (Exception ex) {
            System.out.println(ex);
            return List.of();
        }
    }

    private <T> ResponseEntity<T> getRiotEndpoint(String region, String endpoint, Class<T> clazz) throws InterruptedException {
        String baseUrl = String.format(urlFormat, region);
        String queryParams = "?api_key=" + api_key;
        String url = baseUrl + endpoint + queryParams;
        while(true) {
            try {
                return restTemplate.getForEntity(url, clazz);
            } catch(HttpClientErrorException.TooManyRequests ex) {
                System.out.println(ex);
                System.out.println("Too many requests! Waiting 0.5s");
                Thread.sleep(500);
            }
        }
    }

    public boolean isRegionInvalid(String region) {
        return !regions.contains(region);
    }

}
