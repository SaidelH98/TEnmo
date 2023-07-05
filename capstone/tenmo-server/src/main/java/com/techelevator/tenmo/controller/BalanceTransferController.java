package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.BalanceTransferDao;
import com.techelevator.tenmo.dao.JdbcBalanceTransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.security.jwt.TokenProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;

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




}
