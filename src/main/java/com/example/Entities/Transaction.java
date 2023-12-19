package com.example.Entities;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "transaction")
public class Transaction {
	
	public enum TransactionType {
		DEPOSIT,
		WITHDRAWAL,
		CREDIT,
		TRANSFER,
		CHECK,
		}
	
    @Id
    @GeneratedValue
    private Long transactionId;

    private Long accountId;

    private int transactionAmount;
    
    private TransactionType transactionType;

    private LocalDateTime transactionDateTime;
    
    public Transaction(Long inputAccountId, int transactionAmount2, TransactionType inputTransactionType, LocalDateTime localDateTime) {
    	accountId = inputAccountId;
    	transactionAmount = transactionAmount2;
    	transactionType = inputTransactionType;
    	setTransactionDateTime(localDateTime);
    }

	public LocalDateTime getTransactionDateTime() {
		return transactionDateTime;
	}

	public void setTransactionDateTime(LocalDateTime transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

}