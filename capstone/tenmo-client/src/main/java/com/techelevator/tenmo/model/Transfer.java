package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Transfer {

    private BigDecimal amount;
    private String username;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
