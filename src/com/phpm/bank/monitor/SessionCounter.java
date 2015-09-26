package com.phpm.bank.monitor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.phpm.bank.client.pojo.User;

@WebListener
public class SessionCounter implements HttpSessionListener {

	public static Map<String,User> sessions = new HashMap<String, User>();
	
	@Override
    public void sessionCreated(HttpSessionEvent event) {
        sessions.put(event.getSession().getId(), null);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
    	sessions.remove(event.getSession().getId());
    }
    
}
