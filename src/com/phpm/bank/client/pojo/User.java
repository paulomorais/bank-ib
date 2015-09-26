package com.phpm.bank.client.pojo;

import java.io.Serializable;

import com.phpm.bank.client.pojo.util.UserType;

public class User implements Serializable {
    
	private static final long serialVersionUID = 2035472434374650057L;

	private long id;
	private String username;
	private String password;
	private UserType type;
	private String name;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object aThat) {
		if ( this == aThat ) return true;
		  if ( !(aThat instanceof User) ) return false;
		  User that = (User) aThat;
		  return this.id == that.id;
	}
	
	@Override
	public int hashCode() {
		return new Long(this.id).hashCode();
	}
}
