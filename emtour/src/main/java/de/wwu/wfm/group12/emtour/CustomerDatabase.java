package de.wwu.wfm.group12.emtour;

import org.camunda.bpm.engine.delegate.DelegateExecution;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.sql.Connection;
import java.sql.Date;

import javax.ejb.Stateless;
import javax.inject.Named;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import static java.lang.Math.toIntExact;

@Stateless
@Named
public class CustomerDatabase {
	
	public void createCustomerDbRecord (DelegateExecution delegateExecution) throws Exception {
    
    // Get all process variables
    Map<String, Object> variables = delegateExecution.getVariables();
    System.out.println("variables here db creation:" + variables.toString());  
       
    String insertQuery = String.format("INSERT INTO customer "
    		+ "(`name`, `lastName`, `birthDate`,`email`,`budget`,"
    		+ "`desiredCity`,`travelStartDate`,`travelEndDate`,`children`,`adult`)"
    		+ "VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s');", 
    		variables.get("name"),variables.get("lastName"),transformDate(variables.get("birthDate").toString()),variables.get("email"),variables.get("budget"),
    		variables.get("desiredCity"),transformDate(variables.get("travelStartDate").toString()),transformDate(variables.get("travelEndDate").toString()),variables.get("children"),variables.get("adult"));
    
    Class.forName("com.mysql.jdbc.Driver");
    
	Connection con = DriverManager.getConnection("jdbc:mysql://pedro911.selfhost.eu:3306/emtour","emtour","Emtour,sql");
	
	PreparedStatement statement = con.prepareStatement(insertQuery);
	
	try { 
		statement.executeUpdate();		
		System.out.println("Customer database insert success");		
		
	} 
	catch (SQLException e) { 
		e.printStackTrace();
	}
	
  }
	
  public String lastCustomerId() throws Exception{
	String customerId="";	
    Class.forName("com.mysql.jdbc.Driver");    
	Connection con = DriverManager.getConnection("jdbc:mysql://pedro911.selfhost.eu:3306/emtour","emtour","Emtour,sql");
	String lastIdquery = String.format("SELECT idcustomer FROM customer ORDER BY idcustomer DESC LIMIT 1");
	PreparedStatement statement = con.prepareStatement(lastIdquery); 
	ResultSet result = statement.executeQuery();	
	try {
		while (result.next()) {
			customerId = result.getString("idcustomer");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}  
	System.out.println("Last customer Id inserted: "+customerId);
	return customerId;
  }

  public void createContract (DelegateExecution delegateExecution) throws Exception {
	    
	    // Get all process variables
	    Map<String, Object> variables = delegateExecution.getVariables();
	    
	    Long amount = Long.valueOf((String) variables.get("amount"));
	    Long period = Long.valueOf((String) variables.get("period"));
	    	    
	    String query = String.format("INSERT INTO customercreditrequest (`RequestID`,`DebitorID`,`Date`,"
	    		+ "`Currency`,`Amount`,`Status`,`Months`,`IncomeAmount`)"
	    		+ "VALUES ('%s','%s',%s,'%s',%d,'%s',%d ,'%s');", variables.get("requestID"), 
	    		variables.get("pass"), "CURDATE()" , variables.get("currency"), amount, "In progress",
	    		period*12,variables.get("income")); 
	     	
	    Class.forName("com.mysql.jdbc.Driver");
	    
		Connection con = DriverManager.getConnection("jdbc:mysql://pedro911.selfhost.eu:3306/emtour","emtour","Emtour,sql");
		
		PreparedStatement statement = con.prepareStatement(query);
		
		try { 
			statement.executeUpdate(); 
		} 
		catch (SQLException e) { 
			e.printStackTrace();
		}
		
		Long monthlyPay = amount/(period*12);
		delegateExecution.setVariable("monthlyPay", monthlyPay);
		
	  }
  
  public void completeContract (DelegateExecution delegateExecution) throws Exception {
	    
	    // Get all process variables
	    Map<String, Object> variables = delegateExecution.getVariables();
	    
	    Long amount = Long.valueOf((String) variables.get("amount"));
	    Long period = Long.valueOf((String) variables.get("period"));
	    
	    Calendar c = Calendar.getInstance(); 
	    c.add(Calendar.YEAR, toIntExact(period));
	    Date sqlDate= new java.sql.Date(c.getTimeInMillis());
	    DateFormat df = new SimpleDateFormat("YYYY-MM-dd");
	    String dueDate = df.format(sqlDate);
	    
	    String update_query = String.format("UPDATE customercreditrequest SET Status = 'Completed' WHERE "
	    		+ "RequestID = '%s';", variables.get("requestID")); 
		
	    String insert_query = String.format("INSERT INTO customercredithistory"
				+ "(`RequestID`,`DebitorID`,`RequestedDate`,`Currency`,`Amount`,`AmountLeft`,"
				+ "`DueDate`) VALUES ('%s','%s',%s,'%s', %d, %d, '%s');",
				variables.get("requestID"), variables.get("pass"), "CURDATE()", variables.get("currency"), 
				amount, amount, dueDate);  
		
		Class.forName("com.mysql.jdbc.Driver");
	    
		Connection con = DriverManager.getConnection("jdbc:mysql://pedro911.selfhost.eu:3306/emtour","emtour","Emtour,sql");
		
		PreparedStatement statement = null;
				
		try { 
			statement = con.prepareStatement(update_query);
			statement.executeUpdate();		
		} 
		catch (SQLException e) { 
			e.printStackTrace();
		}
		
		try { 
			statement = con.prepareStatement(insert_query);
			statement.executeUpdate();		
		} 
		catch (SQLException e) { 
			e.printStackTrace();
		}
		
			
  }
  
  public void cancelContract (DelegateExecution delegateExecution) throws Exception {
	    
	    // Get all process variables
	    Map<String, Object> variables = delegateExecution.getVariables();
	    
	    String query = String.format("UPDATE customercreditrequest SET Status = 'Incomplete' WHERE "
	    		+ "RequestID = '%s';", variables.get("requestID")); 		
	     	
	    Class.forName("com.mysql.jdbc.Driver");
	    
		Connection con = DriverManager.getConnection("jdbc:mysql://pedro911.selfhost.eu:3306/emtour","emtour","Emtour,sql");
		
		PreparedStatement statement = con.prepareStatement(query);
			
		try { 
			statement.executeUpdate();		
		} 
		catch (SQLException e) { 
			e.printStackTrace();
		}		
			
  }
  
	public java.sql.Date transformDate(String dateStr){		
	    DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
	    java.util.Date date = null;
		try {
			date = (java.util.Date)formatter.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	    
	    java.sql.Date dateDB = new java.sql.Date(date.getTime());		
		return dateDB;
	}
} 




