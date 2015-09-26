package com.phpm.bank.client.pojo;

import java.io.Serializable;
import java.util.Date;

import com.phpm.bank.client.pojo.util.TransactionType;

public class Transaction implements Serializable {
    
	private static final long serialVersionUID = 2035472434374650057L;

	private long id;
	private Date date;
	private double value;
	private TransactionType type;
	private long accountNumber;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}

	@Override
	public boolean equals(Object aThat) {
		if ( this == aThat ) return true;
		  if ( !(aThat instanceof Transaction) ) return false;
		  Transaction that = (Transaction) aThat;
		  return this.id == that.id;
	}
	
	@Override
	public int hashCode() {
		return new Long(this.id).hashCode();
	}
}
