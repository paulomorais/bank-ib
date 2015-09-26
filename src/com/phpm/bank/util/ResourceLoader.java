package com.phpm.bank.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phpm.bank.client.pojo.util.UserType;

public class ResourceLoader {
	
	final static Logger logger = LoggerFactory.getLogger(ResourceLoader.class);
	
	public static HashMap<String, Set<UserType>> permissions = new HashMap<String, Set<UserType>>();
	
	static {
		Set<UserType> admin = new HashSet<UserType>();
		admin.add(UserType.ADMIN);

		Set<UserType> all = new HashSet<UserType>();
		all.add(UserType.ADMIN);
		all.add(UserType.CLIENT);
		
		permissions.put("monitor.xhtml", admin);
		permissions.put("monitoredAccount.xhtml", admin);
		permissions.put("transfer.xhtml", all);
		permissions.put("login.xhtml", all);
		permissions.put("index.xhtml", all);
		permissions.put("account.xhtml", all);
	}
	
	public synchronized static String getResourcePath(String resource) {
		try {
			return ResourceLoader.class.getClassLoader().getResource(resource).getFile();
		} catch (Exception e) {
			logger.error("Required file '{}' not found!", resource);
			return null;
		}
	}
}