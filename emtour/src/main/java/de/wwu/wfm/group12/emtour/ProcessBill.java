package de.wwu.wfm.group12.emtour;

import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

@SuppressWarnings("unused")
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
		   
		   double total_value;
		   double totalFunspark = 0;
		   int children = Integer.parseInt((execution.getVariable("children")).toString());
		   int adult = Integer.parseInt((execution.getVariable("adult")).toString());
		   double price = Double.parseDouble((execution.getVariable("price")).toString());
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
		   
		   //save bill on database
		   String customerId = execution.getVariable("customerId").toString();		
		   String funsparkRecommendationId = execution.getVariable("funsparkRecommendationId").toString();
		   DatabaseManagement dbm = new DatabaseManagement();
		   boolean paymentStatus = false;		   
		   dbm.createBill(customerId,funsparkRecommendationId,paymentStatus);	
		   
		   System.out.println("Total Bill is: " + total_value + " Persons: " + String.valueOf(adult +children) + " City: " + execution.getVariable("desiredCity"));
		   
	   
	  }
}
