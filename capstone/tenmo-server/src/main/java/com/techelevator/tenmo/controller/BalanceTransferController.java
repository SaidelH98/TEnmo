package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.BalanceTransferDao;
import com.techelevator.tenmo.dao.JdbcBalanceTransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.security.jwt.TokenProvider;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
public class BalanceTransferController {

    private final UserDao userDao;
    private final BalanceTransferDao balanceTransferDao;

    public BalanceTransferController(UserDao userDao, BalanceTransferDao balanceTransferDao) {
        this.userDao = userDao;
        this.balanceTransferDao = balanceTransferDao;
    }

    @RequestMapping(path = "/balance", method = RequestMethod.GET)
    public BigDecimal getBalance(Principal principal){
        String userName = principal.getName();
        User currentUser =  userDao.getUserByUsername(userName);
        return balanceTransferDao.getUserBalance(currentUser.getId());
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<String> getUsernames(Principal principal) {
        return userDao.getUsernames(principal.getName());
    }

    @RequestMapping(path = "/transfer", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void transferMoney(Principal principal, @RequestBody Transfer transfer){
        String senderUsername = principal.getName();
        balanceTransferDao.transfer(transfer.getAmount(), transfer.getUsername(), senderUsername);
    }




}
