package de.wwu.wfm.group12.emtour;

import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class ProcessBill implements JavaDelegate {
	
	 private final static Logger LOGGER = Logger.getLogger("PROCESS-BILL");

	  public void execute(DelegateExecution execution) throws Exception {
		  
		  
		   LOGGER.info("Offers: '" 
		    		+ execution.getVariable("offers") + "' City: '"
		    		+ execution.getVariable("desiredCity") + "'");
	    	    
		   //get variables from 
		   String FunsparkActivityPrices = (execution.getVariable("ActivityPrices")).toString();
		   String[] FunsparkActivityPricesArray = FunsparkActivityPrices.split(Pattern.quote(";"));
		   double activitySetPrice = 0;
		   for (int i = 0; i < FunsparkActivityPricesArray.length; i++){
			   activitySetPrice = activitySetPrice + Integer.parseInt(FunsparkActivityPricesArray[i]);
		   }
		   System.out.println("activitySetPrice" + activitySetPrice); 
		   // count how many activities are split by ; and sum it
		   
		   double total_value;
		   double totalFunspark = 0;
		   //int id = (Integer)execution.getVariable("id"); //customerId
		   //int children = ((Integer) execution.getVariable("children")).intValue();
		   int children = Integer.parseInt((execution.getVariable("children")).toString());
		   //int adult = (Integer) execution.getVariable("adult");
		   int adult = Integer.parseInt((execution.getVariable("adult")).toString());
		   //int number_of_days = (Integer) execution.getVariable("number_of_days");
		   //int budget = (Integer) execution.getVariable("budget");
		   //int price = (Integer) execution.getVariable("price");
		   double price = Integer.parseInt((execution.getVariable("price")).toString());
		   //int price=100;
		   boolean hasFunSparkActivity = ((Boolean)execution.getVariable("hasFunSparkActivity")).booleanValue();
		   		   
		   // Generate bill depending on client's budget & activity set price provided from FunSpark
		   total_value = price * adult + (price/2) * children;  
		   if (hasFunSparkActivity == true){
			   totalFunspark = activitySetPrice * adult + (activitySetPrice/2) * children;
			   total_value = total_value + totalFunspark; 
		   }
		   execution.setVariable("total_value", String.valueOf(total_value));
		   execution.setVariable("activitySetPrice", String.valueOf(activitySetPrice));
		   execution.setVariable("totalFunspark", String.valueOf(totalFunspark));
		   
		   System.out.println("Total Bill is: " + total_value + " Persons: " + String.valueOf(adult +children) + " City: " + execution.getVariable("desiredCity"));
		   
	   
	  }
}
