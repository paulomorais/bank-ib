package com.phm.bank.lazyloading;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.phm.bank.client.pojo.Account;
import com.phm.bank.client.service.AccountService;

public class LazyAccountDataModel extends LazyDataModel<Account> {

	private static final long serialVersionUID = 4754552100679116186L;
	private String username;
	
	public LazyAccountDataModel(String username) {
		this.username = username;
	}
	
	@Override
    public List<Account> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
		
		AccountService accountService = new AccountService();
		List<Account> list = new ArrayList<Account>();
		long accountNumberFilter = 0;
		String usernameFilter = username;

		if (filters != null) {
			if (filters.containsKey("accountNumberFilter")){
				accountNumberFilter = Long.parseLong((String) filters.get("accountNumberFilter"));
			}
			if (filters.containsKey("usernameFilter")){
				usernameFilter = (String) filters.get("usernameFilter");
			}
		}
		
		if (accountNumberFilter > 0 ) {
			Account accountSearch = accountService.getAccount( accountNumberFilter );
			if (accountSearch.getId() > 0 ){
				list.add(accountSearch);
			}
			this.setRowCount(list.size());
		} else {
			list = accountService.list(usernameFilter, first, pageSize);
			this.setRowCount( accountService.rowCount(usernameFilter) );			
		}
		System.err.println("first = " + first);
		System.err.println("list.size() = " + list.size());
		return list;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
