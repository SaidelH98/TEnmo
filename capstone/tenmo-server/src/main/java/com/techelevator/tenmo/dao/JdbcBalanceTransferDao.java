package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.exception.DaoException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcBalanceTransferDao implements BalanceTransferDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcBalanceTransferDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal getUserBalance(int id){
        BigDecimal balance = null;
        String sql = "SELECT balance\n" +
                "FROM account\n" +
                "WHERE user_id = ?;";
        try{
            SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql,id);
            if(rowSet.next()){
                balance = rowSet.getBigDecimal("balance");
            }
        }catch (CannotGetJdbcConnectionException ex){
            throw new DaoException("Unable to connect to server or database", ex);
        } catch (DataIntegrityViolationException ex){
            throw new DaoException("Data integrity violation", ex);
        }

        return balance;
    }

    @Override
    public void transfer(BigDecimal transferAmount, String receiverUsername, String senderUsername){
        String sql = "BEGIN TRANSACTION;\n" +
                "\n" +
                "UPDATE account\n" +
                "SET balance = balance - ?\n" +
                "WHERE user_id = \n" +
                "\t(SELECT user_id\n" +
                "\t FROM tenmo_user\n" +
                "\t WHERE username = ?);\n" +
                "\n" +
                "UPDATE account\n" +
                "SET balance = balance + ?\n" +
                "WHERE user_id = \n" +
                "\t(SELECT user_id\n" +
                "\t FROM tenmo_user\n" +
                "\t WHERE username = ?);\n" +
                "\n" +
                "COMMIT;";
        try{
            jdbcTemplate.update(sql, transferAmount, senderUsername, transferAmount, receiverUsername);
        } catch (Exception ex){
            throw new DaoException("Unable to connect to server or database", ex);
        }
    }
}
