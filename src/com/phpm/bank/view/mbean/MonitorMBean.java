package com.phpm.bank.view.mbean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
	private Date startDate;
	private Date endDate;
	
	public MonitorMBean() {
		Calendar calendar = Calendar.getInstance();
		endDate = calendar.getTime();
		calendar.add(Calendar.DATE, -30);
		startDate = calendar.getTime();
		lazyAccount = new LazyAccountDataModel(null);
		updateLoggedUserList();
	}

	public String startup() {
		lazyAccount = new LazyAccountDataModel(null);
		updateLoggedUserList();
		return "monitor.xhtml";
	}
	
	public void updateFilters() {
		lazyTransactions = new LazyTransactionDataModel(account.getId(), startDate, endDate);
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
		if (account != null){
			logger.debug("Monitoring Account number: " + account.getId());
			lazyTransactions = new LazyTransactionDataModel(account.getId(), startDate, endDate);
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
}