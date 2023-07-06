package com.summoner.riotapispring.service;

import com.summoner.riotapispring.model.ChampionMasteryDTO;
import com.summoner.riotapispring.model.SummonerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;
import java.util.Objects;

@Service
public class RiotService {

    @Value("${api.key}")
    private String api_key;

    @Value("${api.url.format}")
    private String urlFormat;

    private final RestTemplate restTemplate;

    @Autowired
    public RiotService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public SummonerDTO getSummonerByRegionAndName(String region, String name) {
        try {
            String endpoint = "/lol/summoner/v4/summoners/by-name/" + name;
            ResponseEntity<SummonerDTO> summonerResponse = getRiotEndpoint(region, endpoint, SummonerDTO.class);
            return summonerResponse.getBody();
        } catch (HttpClientErrorException ex) {
            System.out.println(ex);
            return null;
        }
    }

    public List<ChampionMasteryDTO> getChampionMasteryTop(String region, String encryptedSummonerId, int count) {
        try {
            String endpoint = "/lol/champion-mastery/v4/champion-masteries/by-summoner/" + encryptedSummonerId + "/top";
            ResponseEntity<ChampionMasteryDTO[]> masteryResponse = getRiotEndpoint(region, endpoint, ChampionMasteryDTO[].class);
            return List.of(Objects.requireNonNull(masteryResponse.getBody()));
        } catch (Exception ex) {
            System.out.println(ex);
            return null;
        }
    }

    private <T> ResponseEntity<T> getRiotEndpoint(String region, String endpoint, Class<T> clazz) throws RestClientException {
        String baseUrl = String.format(urlFormat, region);
        String queryParams = "?api_key=" + api_key;
        String url = baseUrl + endpoint + queryParams;
        return restTemplate.getForEntity(url, clazz);
    }

}
