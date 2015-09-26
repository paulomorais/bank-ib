package com.phpm.bank.lazyloading;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.phpm.bank.client.pojo.User;
import com.phpm.bank.client.service.UserService;

public class LazyUserDataModel extends LazyDataModel<User> {

	private static final long serialVersionUID = 4754552100679116186L;

	@Override
    public List<User> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
		
		UserService userService = new UserService();
		
		List<User> list = userService.list(first, pageSize);

		this.setRowCount( userService.rowCount() );
		return list;
	}
}
