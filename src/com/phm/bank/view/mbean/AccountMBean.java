package com.phm.bank.view.mbean;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.model.LazyDataModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phm.bank.client.pojo.Account;
import com.phm.bank.client.pojo.Transaction;
import com.phm.bank.client.pojo.User;
import com.phm.bank.client.service.AccountService;
import com.phm.bank.client.service.TransactionService;
import com.phm.bank.lazyloading.LazyAccountDataModel;
import com.phm.bank.lazyloading.LazyTransactionDataModel;
import com.phm.bank.util.BankReport;

@ManagedBean(name = "AccountMBean")
@SessionScoped
public class AccountMBean {
	
	final static Logger logger = LoggerFactory.getLogger(AccountMBean.class);
	
	private LazyDataModel<Account> lazyAccount;
	private LazyDataModel<Transaction> lazyTransactions;
	
	private List<Account> accounts = null;
	private Account account = new Account();
	private Account receiverAccount;
	private Date startDate;
	private Date endDate;
	
	private Transaction transaction;
	private int transferStage = 1;
	
	private AccountService accountService = new AccountService();
	
	public AccountMBean() {
		Calendar calendar = Calendar.getInstance();
		endDate = calendar.getTime();
		calendar.add(Calendar.DATE, -30);
		startDate = calendar.getTime();
		
		lazyAccount = new LazyAccountDataModel(getLoggedUser().getUsername());
	}
	

	public String startup() {
		logger.debug("loading MBean.");
		lazyAccount = new LazyAccountDataModel(getLoggedUser().getUsername());
		receiverAccount = null;
		transaction = null;
		transferStage = 1;
		return "index";
	}
	
	public String viewAccount() {
		receiverAccount = null;
		transaction = null;
		transferStage = 1;
		logger.debug("View Account." + account);
		if (account != null){
			lazyTransactions = new LazyTransactionDataModel(account.getId(), startDate, endDate);
		} else {
			return null;
		}
		return "account.xhtml";
	}
	
	public void validateTransfer(){
		long accountNumberSearch = receiverAccount.getId();
		receiverAccount = accountService.getAccount(receiverAccount.getId());
		if (receiverAccount.getId() == 0){
			receiverAccount.setId(accountNumberSearch);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, 
					"Wrong data inserted", "Account number not found!"));
		} else {
			transferStage = 2;
		}
	}
	
	public String cancelTransfer() {
		receiverAccount = null;
		transaction = null;
		transferStage = 1;
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
				"Operation canceled!", null));
		
		return "account";
	}
	
	public String executeTransfer() {
		transaction = accountService.executeTransfer(transaction, receiverAccount.getId());
		transferStage = 1;
		account = accountService.getAccount(account.getId());
		
		if (transaction.getId() != 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
					"Success!", "Transfer executed!"));
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Error!", "Service unavailable!"));
			return null;
		}
		
		receiverAccount = null;
		transaction = null;
		
		return "account";
	}
	
	public String transactions() {
		receiverAccount = null;
		transaction = null;
		return "account.xhtml";
	}
	
	public String transfer() {
		receiverAccount = new Account();
		transaction = new Transaction();
		transaction.setAccountNumber(account.getId());
		transferStage = 1;
		return "transfer";
	}
	
	public void updateFilters() {
		lazyTransactions = new LazyTransactionDataModel(account.getId(), startDate, endDate);
	}
	
	public String exportStatement() {
		BankReport bankReport = new BankReport();
		
		TransactionService service = new TransactionService();
		
		List<Transaction> transactions = service.list(account.getId(), 0, 1000, startDate, endDate);
		byte[] pdf = bankReport.statementReport(transactions, account, startDate, endDate);
		
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) context.getExternalContext().getResponse();
		 
		response.reset();
		try {
		     OutputStream os = response.getOutputStream();
		     response.setContentType("application/pdf"); // fill in contentType
		     response.setContentLength(pdf.length);
		     response.setHeader("Content-disposition", "attachment; filename=\""+ "PHM-Bank-Statement.pdf\"");
		     os.write(pdf); // fill in bytes
		     os.flush();
		     os.close();
		context.responseComplete();
		} catch (IOException e) {
		}
		 
		return null;
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


	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}


	public Account getReceiverAccount() {
		return receiverAccount;
	}


	public void setReceiverAccount(Account receiverAccount) {
		this.receiverAccount = receiverAccount;
	}


	public Transaction getTransaction() {
		return transaction;
	}


	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}


	public int getTransferStage() {
		return transferStage;
	}


	public void setTransferStage(int transferStage) {
		this.transferStage = transferStage;
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