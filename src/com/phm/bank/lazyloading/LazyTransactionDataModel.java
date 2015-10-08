package com.phm.bank.lazyloading;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.phm.bank.client.pojo.Transaction;
import com.phm.bank.client.service.TransactionService;

public class LazyTransactionDataModel extends LazyDataModel<Transaction> {

	private static final long serialVersionUID = 4754552100679116186L;
	
	private long accountNumber = 0;
	private Date startDate;
	private Date endDate;
	
	public LazyTransactionDataModel(long accountNumber, Date startDate, Date endDate) {
		this.accountNumber = accountNumber;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	@Override
    public List<Transaction> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
		
		TransactionService transactionService = new TransactionService();
		List<Transaction> list = transactionService.list(accountNumber, first, pageSize, startDate, endDate);

		this.setRowCount( transactionService.rowCount(accountNumber, startDate, endDate) );
		
		return list;
	}

	public long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(long accountNumber) {
		this.accountNumber = accountNumber;
	}
	
}
