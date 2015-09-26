package com.phpm.bank.view.mbean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phpm.bank.client.pojo.Account;
import com.phpm.bank.client.pojo.Transaction;
import com.phpm.bank.client.pojo.User;
import com.phpm.bank.lazyloading.LazyAccountDataModel;
import com.phpm.bank.lazyloading.LazyTransactionDataModel;
import com.phpm.bank.monitor.SessionCounter;

@ManagedBean(name = "MonitorMBean")
@SessionScoped
public class MonitorMBean {
	
	final static Logger logger = LoggerFactory.getLogger(MonitorMBean.class);
	
	private LazyDataModel<Account> lazyAccount;
	private LazyDataModel<Transaction> lazyTransactions;
	
	private Account account = new Account();
	private List<User> users = new ArrayList<User>();
	
	public MonitorMBean() {
		lazyAccount = new LazyAccountDataModel(null);
		updateLoggedUserList();
	}

	public String startup() {
//		lazyAccount = new LazyAccountDataModel(null);
		lazyTransactions = null;
		updateLoggedUserList();
		return "monitor.xhtml";
	}
	
	public void updateLoggedUserList(){
		users = new ArrayList<User>();
		for (Iterator<User> iterator = SessionCounter.sessions.values().iterator(); iterator.hasNext();) {
			User user = (User) iterator.next();
			if (user != null){
				users.add(user);
			}else{
				System.out.println(">>>>>>>>>>>>>>>>>>>>>> NULLLLLLL");
			}
		}
	}
	
	public String viewAccount() {
		logger.debug("View Account." + account);
		if (account != null){
			lazyTransactions = new LazyTransactionDataModel(account.getId(), null, null);
		} else {
			return null;
		}
		return "monitoredAccount.xhtml";
	}
	
	public String transactions() {
		return "account.xhtml";
	}
	
	public String transfer() {
		return "transfer";
	}
	
	public User getLoggedUser() {
		User user = (User) FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap().get("user");
		return user;
	}
	
	public boolean getUserAdmin() {
		User user = getLoggedUser();
		return user != null && "ADMIN".equals(user.getType());
	}

	public LazyDataModel<Account> getLazyAccount() {
		return lazyAccount;
	}

	public void setLazyAccount(LazyDataModel<Account> lazyAccount) {
		this.lazyAccount = lazyAccount;
	}

	public LazyDataModel<Transaction> getLazyTransactions() {
		return lazyTransactions;
	}


	public void setLazyTransactions(LazyDataModel<Transaction> lazyTransactions) {
		this.lazyTransactions = lazyTransactions;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
}