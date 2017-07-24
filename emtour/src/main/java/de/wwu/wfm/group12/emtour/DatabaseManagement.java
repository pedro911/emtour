package de.wwu.wfm.group12.emtour;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;

@Stateless
@Named
public class DatabaseManagement {
	
	public void createCustomerDbRecord (DelegateExecution delegateExecution) throws Exception {
    
    // Get all process variables
    Map<String, Object> variables = delegateExecution.getVariables();
    //System.out.println("variables here db creation:" + variables.toString());  
       
    String insertQuery = String.format("INSERT INTO customer "
    		+ "(`name`, `lastName`, `birthDate`,`email`,`budget`,"
    		+ "`desiredCity`,`travelStartDate`,`travelEndDate`,`children`,`adult`)"
    		+ "VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s');", 
    		variables.get("name"),variables.get("lastName"),transformDate(variables.get("birthDate").toString()),variables.get("email"),variables.get("budget"),
    		variables.get("desiredCity"),transformDate(variables.get("travelStartDate").toString()),transformDate(variables.get("travelEndDate").toString()),variables.get("children"),variables.get("adult"));
    
    Class.forName("com.mysql.jdbc.Driver");
    
	//Connection con = DriverManager.getConnection("jdbc:mysql://pedro911.selfhost.eu:3306/emtour","emtour","Emtour,sql");
	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emtour","emtour","Emtour,sql");
	
	PreparedStatement statement = con.prepareStatement(insertQuery);
	
	try { 
		statement.executeUpdate();		
		System.out.println("Customer database insert success");		
		
	} 
	catch (SQLException e) { 
		e.printStackTrace();
	}
	
  }
	
	public void createEmTourRecommendationDbRecord (int customerId, Double priceDouble, String description, String recommendedDestination) throws Exception {	    
	      
	    String insertQuery = String.format("INSERT INTO emtour_recommendation "
	    		+ "(`customer_idcustomer`,`price`, `description`, `desiredCity`)"
	    		+ "VALUES ('%s','%s','%s','%s');", 
	    		customerId,priceDouble,description,recommendedDestination);
	    
	    Class.forName("com.mysql.jdbc.Driver");
	    
		//Connection con = DriverManager.getConnection("jdbc:mysql://pedro911.selfhost.eu:3306/emtour","emtour","Emtour,sql");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emtour","emtour","Emtour,sql");
		
		PreparedStatement statement = con.prepareStatement(insertQuery);
		
		try { 
			statement.executeUpdate();	
			System.out.println("emTour Recommendataion database insert success");			
		} 
		catch (SQLException e) { 
			e.printStackTrace();
		}
		
	  }
	
	public void createFunsParkRecommendationDbRecord (String price, String description, String customerId) throws Exception {	    
	    String idemtour_recommendation="";
	    idemtour_recommendation = idEmTourRecommendations_by_customerId(customerId);
	    String insertQuery = String.format("INSERT INTO funspark_recommendation "
	    		+ "(`emtour_recommendation_idemtour_recommendation`,`price`, `description`)"
	    		+ "VALUES ('%s','%s','%s');", 
	    		idemtour_recommendation,price,description);
	    
	    Class.forName("com.mysql.jdbc.Driver");
	    
		//Connection con = DriverManager.getConnection("jdbc:mysql://pedro911.selfhost.eu:3306/emtour","emtour","Emtour,sql");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emtour","emtour","Emtour,sql");
		
		PreparedStatement statement = con.prepareStatement(insertQuery);
		
		try { 
			statement.executeUpdate();	
			System.out.println("Funspark Recommendataion database insert success");			
		} 
		catch (SQLException e) { 
			e.printStackTrace();
		}
		
	  }
	
	public String idEmTourRecommendations_by_customerId(String customerId) throws Exception{
		String idemtour_recommendation="";
	    Class.forName("com.mysql.jdbc.Driver");    
		//Connection con = DriverManager.getConnection("jdbc:mysql://pedro911.selfhost.eu:3306/emtour","emtour","Emtour,sql");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emtour","emtour","Emtour,sql");
		String query = String.format("SELECT idemtour_recommendation FROM emtour_recommendation WHERE customer_idcustomer ="+customerId);
		PreparedStatement statement = con.prepareStatement(query); 
		ResultSet result = statement.executeQuery();	
		try {
			while (result.next()) {
				idemtour_recommendation = result.getString("idemtour_recommendation");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		System.out.println("idEmTourRecommendations_by_customerId "+idemtour_recommendation);		
		return idemtour_recommendation;
	}
	
	public void createBill(String customerId, String funsparkRecommendationId, boolean paymentStatus) throws Exception {
	    
		String idemtour_recommendation="";
	    idemtour_recommendation = idEmTourRecommendations_by_customerId(customerId);
	    String insertQuery = String.format("INSERT INTO bill "
	    		+ "(`customer_idcustomer`,`funspark_recommendation_idfunspark_recommendation`, `emtour_recommendation_idemtour_recommendation`,`paymentStatus`)"
	    		+ "VALUES ('%s','%s','%s','%s');", 
	    		customerId,funsparkRecommendationId,idemtour_recommendation,0);
	    
	    Class.forName("com.mysql.jdbc.Driver");
	    
		//Connection con = DriverManager.getConnection("jdbc:mysql://pedro911.selfhost.eu:3306/emtour","emtour","Emtour,sql");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emtour","emtour","Emtour,sql");
		
		PreparedStatement statement = con.prepareStatement(insertQuery);
		
		try { 
			statement.executeUpdate();	
			System.out.println("Create Bill database insert success");			
		} 
		catch (SQLException e) { 
			e.printStackTrace();
		}	
	}
	
	public void updatePaymentStatus(String customerId, boolean paymentStatus) throws Exception{
		
		int insert=0;
		if (paymentStatus=true) insert=1;
	    
	    String query = String.format("UPDATE bill SET paymentStatus="+insert+" WHERE customer_idcustomer="+customerId);
	    
	    Class.forName("com.mysql.jdbc.Driver");
	    
		//Connection con = DriverManager.getConnection("jdbc:mysql://pedro911.selfhost.eu:3306/emtour","emtour","Emtour,sql");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emtour","emtour","Emtour,sql");
		
		PreparedStatement statement = con.prepareStatement(query);
		
		try { 
			statement.executeUpdate();	
			System.out.println("update Bill success");			
		} 
		catch (SQLException e) { 
			e.printStackTrace();
		}	

	}

	
  public String lastCustomerId() throws Exception{
	String customerId="";	
    Class.forName("com.mysql.jdbc.Driver");    
	//Connection con = DriverManager.getConnection("jdbc:mysql://pedro911.selfhost.eu:3306/emtour","emtour","Emtour,sql");
	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emtour","emtour","Emtour,sql");
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
  
  public String laslFunsparkRecommendationId() throws Exception{
	String funsparkRecommendationId="";	
    Class.forName("com.mysql.jdbc.Driver");    
	//Connection con = DriverManager.getConnection("jdbc:mysql://pedro911.selfhost.eu:3306/emtour","emtour","Emtour,sql");
	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/emtour","emtour","Emtour,sql");
	String lastIdquery = String.format("SELECT idfunspark_recommendation FROM funspark_recommendation ORDER BY idfunspark_recommendation DESC LIMIT 1");
	PreparedStatement statement = con.prepareStatement(lastIdquery); 
	ResultSet result = statement.executeQuery();	
	try {
		while (result.next()) {
			funsparkRecommendationId = result.getString("idfunspark_recommendation");
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}  
	System.out.println("Last idfunspark_recommendation inserted: "+funsparkRecommendationId);
	return funsparkRecommendationId;
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

