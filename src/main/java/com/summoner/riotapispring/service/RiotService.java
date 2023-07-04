package com.summoner.riotapispring.service;

import com.summoner.riotapispring.model.Summoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class RiotService {

    @Value("${api.key}")
    private String api_key;
    private final RestTemplate restTemplate;

    @Autowired
    public RiotService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<Summoner> getSummonerByName(String name) {
        String url = String.format("https://eun1.api.riotgames.com/lol/summoner/v4/summoners/by-name/%s?api_key=%s", name, api_key);
        try {
            return restTemplate.getForEntity(url, Summoner.class);
        } catch (HttpClientErrorException ex) {
            return ResponseEntity.notFound().build();
        }
    }

}
