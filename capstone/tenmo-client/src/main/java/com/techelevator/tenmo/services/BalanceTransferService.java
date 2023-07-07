package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.util.BasicLogger;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BalanceTransferService {
    private static final String API_BASE_URL = "http://localhost:8080";
    private RestTemplate restTemplate = new RestTemplate();
    private String authToken = null;

    public void setAuthToken(String authToken){
        this.authToken = authToken;
    }

    public BigDecimal getBalance(){
        BigDecimal currentBalance = null;
        try{
            String url = API_BASE_URL + "/balance";
            ResponseEntity<BigDecimal> response = restTemplate.exchange(url, HttpMethod.GET, makeAuthEntity(), BigDecimal.class);
            currentBalance = response.getBody();
        }catch (RestClientResponseException | ResourceAccessException ex) {
            BasicLogger.log(ex.getMessage());
        }
        return currentBalance;
    }

    public List<String> getUsernames(){
        List<String> usernames = new ArrayList<>();
        try {
            String url = API_BASE_URL + "/users";
            ResponseEntity<String[]> response = restTemplate.exchange(url, HttpMethod.GET, makeAuthEntity(), String[].class);
            usernames = Arrays.asList(response.getBody());

        } catch (RestClientResponseException | ResourceAccessException ex){
            BasicLogger.log(ex.getMessage());
        }
        return usernames;
    }

    public void transfer(String userToSend, BigDecimal transferAmount){
        try {
            Transfer transfer = new Transfer();
            transfer.setAmount(transferAmount);
            transfer.setUsername(userToSend);
            restTemplate.exchange(API_BASE_URL + "/transfer", HttpMethod.PUT, makeTransferEntity(transfer), Void.class);
        } catch (RestClientResponseException | ResourceAccessException ex) {
            BasicLogger.log(ex.getMessage());
        }
    }

    private HttpEntity<User> makeUserEntity(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(user, headers);
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(transfer, headers);
    }

}
