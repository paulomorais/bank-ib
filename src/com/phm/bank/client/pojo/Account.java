package com.phm.bank.client.pojo;

import java.io.Serializable;

public class Account implements Serializable {
    
	private static final long serialVersionUID = 2035472434374650057L;

	private long id;
	private double balance;
	private User user;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public boolean equals(Object aThat) {
		if ( this == aThat ) return true;
		  if ( !(aThat instanceof Account) ) return false;
		  Account that = (Account) aThat;
		  return this.id == that.id;
	}
	
	@Override
	public int hashCode() {
		return new Long(this.id).hashCode();
	}
}
