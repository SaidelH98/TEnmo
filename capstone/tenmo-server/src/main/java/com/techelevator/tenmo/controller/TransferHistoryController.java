package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferHistoryDao;
import com.techelevator.tenmo.model.TransferHistory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
public class TransferHistoryController {
    private final TransferHistoryDao transferHistoryDao;
    private final String path = "/history";

    public TransferHistoryController(TransferHistoryDao transferHistoryDao){
        this.transferHistoryDao = transferHistoryDao;
    }

    @RequestMapping(path = path, method = RequestMethod.GET)
    public List<TransferHistory> viewTransferHistory(Principal principal){
           return transferHistoryDao.viewTransfers(principal.getName());
    }

    @RequestMapping(path = path + "/{id}", method = RequestMethod.GET)
    public TransferHistory getTransferHistoryById(@PathVariable int id){
        TransferHistory transferHistory = transferHistoryDao.getTransferHistoryById(id);
        if (transferHistory == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transfer not found");
        }
        return transferHistory;
    }
}
