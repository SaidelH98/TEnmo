package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferHistory;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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
        List<TransferHistory> transferHistories = new ArrayList<>();
        try {
            ResponseEntity<TransferHistory[]> response = restTemplate.exchange(API_BASE_URL, HttpMethod.GET, makeAuthEntity(), TransferHistory[].class);
            transferHistories = Arrays.asList(response.getBody());
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

    private HttpEntity<TransferHistory> makeTransferHistoryEntity(TransferHistory transferHistory){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(transferHistory, headers);
    }
}
