package com.phpm.bank.client.service;

import java.util.List;

import com.phpm.bank.client.pojo.Account;
import com.phpm.bank.client.pojo.Transaction;
import com.phpm.bank.client.pojo.User;
import com.phpm.bank.client.pojo.util.TransactionType;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;


public class AccountService {

	public List<Account> list(String username, int startAt, int size){
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        
        WebResource webResourceGet = client.resource(Config.URI_BASE + "/accounts")
        		.queryParam("startAt", ""+startAt)
        		.queryParam("size", ""+size);
        if(username != null){
        	webResourceGet = webResourceGet.queryParam("username", username);
        }
        
        return webResourceGet.get(new GenericType<List<Account>>() {});
	}
	
	public int rowCount(String username){
        Client client = Client.create();
        WebResource webResourceGet = client.resource(Config.URI_BASE + "/accounts/rowCount");
        if(username != null){
        	webResourceGet = webResourceGet.queryParam("username", username);
        }
        String result = webResourceGet.get(String.class);
        try {
        	return Integer.parseInt(result);
        } catch (NumberFormatException nfe){
        	return 0;
        }
	}
	
	public Account getAccount(long accountNumber){
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        
        WebResource webResourceGet = client.resource(Config.URI_BASE + "/accounts/" + accountNumber);
        ClientResponse response = webResourceGet.get(ClientResponse.class);
        
        if (response.getStatus() == 200){
        	Account account = response.getEntity(Account.class);
        	return account;
        }
		return new Account();
	}
	
	public Transaction executeTransfer(Transaction transaction, long receiverAccount){
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        
        transaction.setType(TransactionType.TRANSFER);
        
        WebResource webResourceGet = client.resource(Config.URI_BASE + "/transfer/" + receiverAccount);
        
        ClientResponse response = webResourceGet.type("application/json").post(ClientResponse.class, transaction);
        
        if (response.getStatus() == 200){
        	transaction = response.getEntity(Transaction.class);
        	return transaction;
        }
		return new Transaction();
	}
	
	public User login(String username, String password){
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        
        WebResource webResourceGet = client.resource(Config.URI_BASE + "/users/" 
    		+ username + "?password=" + password);
        ClientResponse response = webResourceGet.get(ClientResponse.class);
        if (response.getStatus() == 200){
        	User user = response.getEntity(User.class);
        	return user;
        }
		return new User();
	}
}
