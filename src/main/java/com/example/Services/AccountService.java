package com.example.Services;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.Entities.Account;
import com.example.Entities.Transaction;
import com.example.Entities.Transaction.TransactionType;
import com.example.Repositories.AccountRepository;
import com.example.Repositories.TransactionRepository;


@Service
public class AccountService  {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public int getBalance(Long accountId){
    		Optional<Account> account = accountRepository.findById(accountId);
    		if(account == null)
    	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "accountId not existing");

    			return account.get().getCurrentBalance();

    }

    public Transaction createTransaction(Long accountId, TransactionType transactionType, int transactionAmount, Optional<Long> toAccountId) {
    	Optional<Account> account = accountRepository.findById(accountId);
    	if(account == null)
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "accountId not existing");
    	
    	if(transactionType != TransactionType.TRANSFER && toAccountId != null) 
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input");
    	
    	if(transactionAmount < 0)
	        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid transaction amount input");    	

    	switch (transactionType) {
    	 case TRANSFER: 
    			if(toAccountId == null)
    		        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "toAccount value מot accepted");   
    			Optional<Account> toAccount = accountRepository.findById(toAccountId.get());
    	    	if(toAccount == null)
    		        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "toAaccountId not existing");
        	    account.get().setCurrentBalance(-transactionAmount);
        	    toAccount.get().setCurrentBalance(transactionAmount);
            	transactionRepository.save(new Transaction(toAccountId.get(), transactionAmount,transactionType, LocalDateTime.now()));
            	return transactionRepository.save(new Transaction(accountId, -transactionAmount,transactionType, LocalDateTime.now()));

    	case WITHDRAWAL:
    	    account.get().setCurrentBalance(-transactionAmount);
        	return transactionRepository.save(new Transaction(accountId, transactionAmount,transactionType, LocalDateTime.now()));
        
    	  default:
    		  account.get().setCurrentBalance(transactionAmount);
          	return transactionRepository.save(new Transaction(accountId, transactionAmount,transactionType, LocalDateTime.now()));        
      }
    }

	public List<Transaction> recentMovements(Long accountId, int amount) {
		Optional<Account> account = accountRepository.findById(accountId);
		if(account == null)
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "accountId not existing");
		
		List<Transaction> allTransactions = transactionRepository.getAllByAccountId(accountId);

		List<Transaction> recentTransactions = allTransactions.stream()
				.sorted(Comparator.comparing(Transaction::getTransactionDateTime).reversed())
				.limit(amount)
				.collect(Collectors.toList());

		return recentTransactions;


	}
}
