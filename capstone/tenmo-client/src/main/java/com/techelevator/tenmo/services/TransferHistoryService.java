package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.TransferHistory;
import com.techelevator.util.BasicLogger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class TransferHistoryService {
    private static final String API_BASE_URL = "http://localhost:8080/history";
    private RestTemplate restTemplate = new RestTemplate();
    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public List<TransferHistory> viewTransfers(){
        List<TransferHistory> transferHistories = null;
        try {
            return Arrays.asList(restTemplate.exchange(API_BASE_URL, HttpMethod.GET, makeAuthEntity(), TransferHistory[].class).getBody());
        } catch (RestClientResponseException | ResourceAccessException ex) {
            BasicLogger.log(ex.getMessage());
        }
        return transferHistories;
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
