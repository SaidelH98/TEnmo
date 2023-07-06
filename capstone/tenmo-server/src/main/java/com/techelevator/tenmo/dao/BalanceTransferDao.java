package com.techelevator.tenmo.dao;

import java.math.BigDecimal;

public interface BalanceTransferDao {
     BigDecimal getUserBalance(int id);

     void transfer(BigDecimal transferAmount, String username, String senderUsername);
}
