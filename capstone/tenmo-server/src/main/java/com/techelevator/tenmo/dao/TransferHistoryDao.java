package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferHistory;

import java.security.Principal;
import java.util.List;

public interface TransferHistoryDao {
    List<TransferHistory> viewTransfers(String loggedUsername);
}
