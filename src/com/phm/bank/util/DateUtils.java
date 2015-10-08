package com.phm.bank.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public synchronized static String getFormatedDate(Date data){
    	return getFormatedDate(data, "dd/MM/yy");
    }
    
    public synchronized static String getFormatedDate(Date data, String padrao){
    	SimpleDateFormat sdf = new SimpleDateFormat(padrao);
    	return sdf.format(data);
    }
}
