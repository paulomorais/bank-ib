package com.phm.bank.view.mbean;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phm.bank.client.pojo.Account;
import com.phm.bank.client.pojo.User;
import com.phm.bank.client.service.UserService;

@ManagedBean
@SessionScoped
public class LoginBean {
	
	final static Logger logger = LoggerFactory.getLogger(LoginBean.class);
	
	@ManagedProperty(value="#{monitorInfoBean}")
	MonitorInfoBean monitorInfoBean;
	
	public void setMonitorInfoBean(MonitorInfoBean monitorInfoBean) {
		this.monitorInfoBean = monitorInfoBean;
	}
	public MonitorInfoBean getMonitorInfoBean() {
		return monitorInfoBean;
	}


	User user = null;
	Account accountSelected = null;
	private String username = null;
	private String password = null;
	
	private int nUsers = 10;
	private int nAccounts = 4;
	private int nTransactions = 40;
	
	public LoginBean() {
		FacesContext context = FacesContext.getCurrentInstance();
		Object userObject = context.getExternalContext().getSessionMap().get("user");
		if (userObject != null && userObject instanceof User) {
			user = (User) userObject;
		}
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String newPassword() {
		return "login.xhtml";
	}
	
	public Account getAccountSelected() {
		return accountSelected;
	}

	public void setAccountSelected(Account accountSelected) {
		this.accountSelected = accountSelected;
	}

	public String login() {
		user = null;
		String startPage = null;

		UserService userService = new UserService();
		user = userService.login(username, password);

		if (user.getId() > 0) {
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getSessionMap().put("user", user);
			
			System.err.println("before create <LoggedUser> instance for " + user.getName());
			if (monitorInfoBean != null){
				System.err.println("monitorInfoBean != null ");
				monitorInfoBean.addUser(user);
			}
			startPage = "index.xhtml";
		} else {
			logger.debug("Access denied! Invalid username or password!");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Access denied! Invalid username or password!", null));
		}

		return startPage;
	}

	public String logout() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
		
		if (monitorInfoBean != null){
			System.err.println("monitorInfoBean != null ");
			monitorInfoBean.removeUser(user);
		}
		context.getExternalContext().getSessionMap().remove("user");
		session.invalidate();
		
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
				"Logged out", null));
		
		return "login.xhtml";
	}
	
	public String monitor() {
		
		return "monitor.xhtml";
	}
	
	public void createMock() {
		UserService userService = new UserService();
		String result = userService.createMockData(nUsers, nAccounts, nTransactions);
		if ("OK".equals(result)){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
					"Mock data created!", null));
		}
	}

	public int getnUsers() {
		return nUsers;
	}

	public void setnUsers(int nUsers) {
		this.nUsers = nUsers;
	}

	public int getnAccounts() {
		return nAccounts;
	}

	public void setnAccounts(int nAccounts) {
		this.nAccounts = nAccounts;
	}

	public int getnTransactions() {
		return nTransactions;
	}

	public void setnTransactions(int nTransactions) {
		this.nTransactions = nTransactions;
	}
}