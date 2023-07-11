package com.summoner.riotapispring.component;

import com.summoner.riotapispring.model.ChampionData;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class ChampionDataLoader {
    private final RestTemplate restTemplate;
    private final Map<Integer, ChampionData> championDataMap;

    @Autowired
    ChampionDataLoader(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.championDataMap = new HashMap<>();
    }

    @PostConstruct
    public void loadData() {
        try {
            String CHAMPION_DATA_URL = "https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-summary.json";
            ResponseEntity<ChampionData[]> championDataResponse = restTemplate.getForEntity(CHAMPION_DATA_URL, ChampionData[].class);
            List<ChampionData> championDataList = List.of(Objects.requireNonNull(championDataResponse.getBody()));
            for(ChampionData championData : championDataList) {
                this.championDataMap.put(championData.getId(), championData);
            }
        } catch (Exception ex) {
            System.out.println("Error loading champion data");
        }
    }

    public Map<Integer, ChampionData> getChampionData() {
        return championDataMap;
    }

}
