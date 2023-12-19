package com.example.Entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue
    private Long accountId;

    private int currentBalance;

    public Account(int InputCurrentBalance) {
    	 currentBalance = InputCurrentBalance;
    }
    
	public int getCurrentBalance() {
		return this.currentBalance;
	}


	public void setCurrentBalance(int Amount) {
		this.currentBalance = currentBalance + Amount;
	}


}
