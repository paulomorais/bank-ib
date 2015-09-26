package com.phpm.bank.util;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.phpm.bank.client.pojo.Account;
import com.phpm.bank.client.pojo.Transaction;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class BankReport {
	
	final static Logger logger = LoggerFactory.getLogger(BankReport.class);
	
	public byte[] statementReport(List<Transaction> data, Account account, Date start, Date end){
		
		JRBeanCollectionDataSource jrDataSource = new JRBeanCollectionDataSource(data);
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("accountNumber", account.getId());
		parameters.put("accountBalance", account.getBalance());
		parameters.put("name", account.getUser().getName());
		parameters.put("start", start);
		parameters.put("end", end);
		JasperPrint jasperPrint;
		 
		byte[] pdf = null;
		String reportFileName = ResourceLoader.getResourcePath("/resources/reports/Statement.jasper");
		 
		try {
			jasperPrint = JasperFillManager.fillReport(reportFileName, parameters, jrDataSource);
		    pdf = JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (JRException jre) {
			jre.printStackTrace();
			logger.error("Erro creating report: {}.", jre.getMessage());
		}
		
		return pdf;
	}

}
