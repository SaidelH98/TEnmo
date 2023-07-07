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

    @Override
    public List<TransferHistory> viewTransfers(String loggedUsername){
        List<TransferHistory> pastTransfers = new ArrayList<>();
        String sql = "SELECT transfer_id, (SELECT username AS senderusername\n" +
                "FROM tenmo_user tu\n" +
                "JOIN account a\n" +
                "ON tu.user_id = a.user_id\n" +
                "WHERE account_id = account_from), (SELECT username AS receiverusername\n" +
                "FROM tenmo_user tu\n" +
                "JOIN account a\n" +
                "ON tu.user_id = a.user_id\n" +
                "WHERE account_id = account_to), amount\n" +
                "FROM transfer\n" +
                "WHERE account_from = (SELECT account_id FROM account WHERE user_id = (SELECT user_id FROM tenmo_user WHERE username = ?));";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, loggedUsername);
            while(result.next()){
                TransferHistory transferHistory = new TransferHistory();
                transferHistory.setTransferAmount(result.getBigDecimal("amount"));
                transferHistory.setTransferId(result.getInt("transfer_id"));
                transferHistory.setSenderUsername(result.getString("senderusername"));
                transferHistory.setReceiverUsername(result.getString("receiverusername"));
                pastTransfers.add(transferHistory);
            }
        }catch (Exception ex){
            throw new DaoException("Unable to connect to server or database", ex);
        }
        return pastTransfers;
    }
    //to be completed and used for the viewTransferDetailed
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
