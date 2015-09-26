package com.phpm.bank.client.service;

import java.util.List;

import com.phpm.bank.client.pojo.User;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;


public class UserService {
	
	public static void main(String[] args) {
		UserService dao = new UserService();
		dao.createMockData(1000, 4, 500);
	}
	
	public List<User> list(int startAt, int size){
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        
        WebResource webResourceGet = client.resource(Config.URI_BASE + "/users")
        		.queryParam("startAt", ""+startAt)
        		.queryParam("size", ""+size);
        
        return webResourceGet.get(new GenericType<List<User>>() {});
	}
	
	public int rowCount(){
        Client client = Client.create();
        WebResource webResourceGet = client.resource(Config.URI_BASE + "/users/rowCount");
        String result = webResourceGet.get(String.class);
        try {
        	return Integer.parseInt(result);
        } catch (NumberFormatException nfe){
        	return 0;
        }
	}
	
	public User getUser(String username){
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        
        WebResource webResourceGet = client.resource(Config.URI_BASE + "/users/" + username);
        ClientResponse response = webResourceGet.get(ClientResponse.class);
        if (response.getStatus() == 200){
        	User user = response.getEntity(User.class);
        	return user;
        }
		return new User();
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
	
	public String createMockData(int users, int accounts, int transactions){
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        
        WebResource webResourceGet = client.resource(Config.URI_BASE + "/mock")
        		.queryParam("users", ""+users)
        		.queryParam("accounts", ""+accounts)
        		.queryParam("transactions", ""+transactions);
        
        return webResourceGet.get(String.class);
	}
}
