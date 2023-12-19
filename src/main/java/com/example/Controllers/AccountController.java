package com.example.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;
import com.example.Entities.Transaction;
import com.example.Entities.Transaction.TransactionType;
import com.example.Services.AccountService;

@RestController
@RequestMapping("/api/account/{accountId}")
public class AccountController {
    @Autowired
    private AccountService accountService;    

    
    @GetMapping(path = "/getBalance")
    @ResponseStatus(HttpStatus.OK)
    public int getBalance(@PathVariable Long accountId) {
    	return accountService.getBalance(accountId);
    }

    @PostMapping("/transaction")
    @ResponseStatus(HttpStatus.OK)
    public Transaction createTransaction(@PathVariable Long accountId,@RequestParam Optional<Long> toAccountId, @RequestBody TransactionType transactionType, @RequestBody int transactionAmount) {
      return accountService.createTransaction(accountId, transactionType, transactionAmount,toAccountId);
    }
    
    @GetMapping(path = "/recent_movements/{amount}")
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> recentMovements(@PathVariable Long accountId, @PathVariable int amount) {
    	return accountService.recentMovements(accountId, amount);
    }
    
    
}