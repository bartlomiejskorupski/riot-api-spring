package com.summoner.riotapispring.service;

import com.summoner.riotapispring.model.SummonerDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URI;

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
        String baseUrl = String.format(urlFormat, region);
        String endpoint = "/lol/summoner/v4/summoners/by-name/" + name;
        String queryParams = "?api_key=" + api_key;
        String url = baseUrl + endpoint + queryParams;
        try {
            ResponseEntity<SummonerDTO> summonerResponse = restTemplate.getForEntity(url, SummonerDTO.class);
            return summonerResponse.getBody();
        } catch (HttpClientErrorException ex) {
            System.out.println(ex);
            return null;
        }
    }

}
