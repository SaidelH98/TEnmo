package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.util.List;

public interface TransferHistoryDao {
    List<Transfer> viewTransfers();
}
