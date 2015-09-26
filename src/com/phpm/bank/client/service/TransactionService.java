package com.phpm.bank.client.service;

import java.util.Date;
import java.util.List;

import com.phpm.bank.client.pojo.Transaction;
import com.phpm.bank.util.DateUtils;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;


public class TransactionService {

	public List<Transaction> list(long accountNumber, int startAt, int size, Date startDate, Date endDate){
		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client client = Client.create(clientConfig);
        
        WebResource webResourceGet = client.resource(Config.URI_BASE + "/transactions")
        		.queryParam("accountNumber", ""+accountNumber)
        		.queryParam("startAt", ""+startAt)
        		.queryParam("size", ""+size);
        
        if(startDate != null){
        	webResourceGet = webResourceGet.queryParam("startDate", DateUtils.getFormatedDate(startDate, "yyyy-MM-dd"));
        }
        if(endDate != null){
        	webResourceGet = webResourceGet.queryParam("endDate", DateUtils.getFormatedDate(endDate, "yyyy-MM-dd"));
        }
        
        return webResourceGet.get(new GenericType<List<Transaction>>() {});
	}
	
	public int rowCount(long accountNumber, Date startDate, Date endDate){
        Client client = Client.create();
        WebResource webResourceGet = client.resource(Config.URI_BASE + "/transactions/rowCount")
        		.queryParam("accountNumber", ""+accountNumber);
        
        if(startDate != null){
        	webResourceGet = webResourceGet.queryParam("startDate", DateUtils.getFormatedDate(startDate, "yyyy-MM-dd"));
        }
        if(endDate != null){
        	webResourceGet = webResourceGet.queryParam("endDate", DateUtils.getFormatedDate(endDate, "yyyy-MM-dd"));
        }
        
        String result = webResourceGet.get(String.class);
        try {
        	return Integer.parseInt(result);
        } catch (NumberFormatException nfe){
        	return 0;
        }
	}
}
