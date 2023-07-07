package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.TransferHistory;
import com.techelevator.tenmo.model.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferHistoryDao implements TransferHistoryDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTransferHistoryDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    public String usernameByAccountId(int id){
        String username = null;
        String sql2 = "SELECT username\n" +
                "FROM tenmo_user tu\n" +
                "JOIN account a\n" +
                "ON tu.user_id = a.user_id\n" +
                "WHERE account_id = ?;";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql2,id);
            username = result.getString("username");
        } catch (Exception ex){
            throw new DaoException("Unable to connect to server or database",ex);
        }
        return username;
    }

    @Override
    public List<TransferHistory> viewTransfers(String loggedUsername){
        TransferHistory transferHistory = new TransferHistory();
        List<TransferHistory> pastTransfers = new ArrayList<>();
        String sql = "SELECT transfer_id, account_from, account_to, amount\n" +
                "FROM transfer\n" +
                "WHERE account_from = (SELECT account_id FROM account WHERE user_id = (SELECT user_id FROM tenmo_user WHERE username = ?));";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, loggedUsername);
            while(result.next()){
                transferHistory.setTransferAmount(result.getBigDecimal("amount"));
                transferHistory.setTransferId(result.getInt("transfer_id"));
                transferHistory.setSenderUsername(usernameByAccountId(result.getInt("account_from")));
                transferHistory.setReceiverUsername(usernameByAccountId(result.getInt("account_to")));
                pastTransfers.add(transferHistory);
            }
        }catch (Exception ex){
            throw new DaoException("Unable to connect to server or database", ex);
        }
        return pastTransfers;
    }

  /* private TransferHistory mapRowToTransferHistory(SqlRowSet rs) {
        TransferHistory transferHistory = new TransferHistory();
        transferHistory.setTransferId(rs.getInt("transfer_id"));
        //Lookup username by accountId
        transferHistory.setSenderUsername(rs.getInt("account_from"));
        transferHistory.setReceiverUsername(rs.getString());
        transferHistory.setTransferAmount(rs.getBigDecimal("amount"));
        return transferHistory;
    }*/
}
