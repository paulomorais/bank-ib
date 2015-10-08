package com.phm.bank.view.mbean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.phm.bank.client.pojo.User;

@ManagedBean(eager = true)
@ApplicationScoped
public class MonitorInfoBean {
	
	private List<User> loggedUsers = new ArrayList<User>();

	public List<User> getLoggedUsers() {
		return loggedUsers;
	}

	public void setLoggedUsers(List<User> loggedUsers) {
		this.loggedUsers = loggedUsers;
	}

	public void addUser(User loggedUser) {
		System.err.println("MonitorInfoBean.addUser " + loggedUser);
		loggedUsers.add(loggedUser);
		System.err.println("loggedUsers.size() = " + loggedUsers.size());
	}

	public void removeUser(User loggedUser) {
		System.err.println("MonitorInfoBean.removeUser " + loggedUser);
		loggedUsers.remove(loggedUser);
		System.err.println("loggedUsers.size() = " + loggedUsers.size());
		
	}
}