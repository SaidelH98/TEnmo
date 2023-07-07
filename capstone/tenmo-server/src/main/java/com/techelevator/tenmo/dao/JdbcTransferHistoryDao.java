package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferHistoryDao implements TransferHistoryDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferHistoryDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Transfer> viewTransfers(){
        List<Transfer> pastTransfers = new ArrayList<>();

        return pastTransfers;
    }
}
