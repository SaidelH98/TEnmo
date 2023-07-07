package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferHistoryDao;
import com.techelevator.tenmo.model.TransferHistory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
}
