package au.com.unico.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.unico.service.ParameterPersistenceBean;

public class WebServiceUtils {
	
	private static final Logger logger = LoggerFactory
			.getLogger(WebServiceUtils.class);

	public static DataSource getMySQLDataSource(){
		Context initContext;
		DataSource ds = null;
		try {
			initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/myoracle");
			return ds;
		} catch (NamingException e) {
			logger.error("Exception in lookup ", e);
		}
		
		return ds;
	}
	
	/*
     * Find GCD of two number using Euclid's method
     * @return GDC of two numbers 
     */
    public static int findGCD(int number1, int number2) {
        if(number2 == 0){
            return number1;
        }
        return findGCD(number2, number1%number2);
    }

}
