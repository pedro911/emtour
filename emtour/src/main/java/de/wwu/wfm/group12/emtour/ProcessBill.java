package de.wwu.wfm.group12.emtour;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class ProcessBill implements JavaDelegate {
	
	 private final static Logger LOGGER = Logger.getLogger("PROCESS-BILL");

	  public void execute(DelegateExecution execution) throws Exception {
	   /* 	    
	   int activitySetPrice = 100;
	   int bill;
	   
	   int id = (Integer)execution.getVariable("id");
	   int children = ((Integer) execution.getVariable("children")).intValue();
	   int adult = (Integer) execution.getVariable("adult");
	   int number_of_days = (Integer) execution.getVariable("number_of_days");
	   int budget = (Integer) execution.getVariable("budget");
	   
	// Generate bill depending on client's budget & activity set price provided from FunSpark
	   bill = budget * adult + (budget/2) * children + activitySetPrice;  
	   
	   System.out.println("Total Bill is: " + bill + " Persons: " + String.valueOf(adult +children) + " City: " + execution.getVariable("desiredCity"));
	   */
	  
	   LOGGER.info("Offers: '" 
	    		+ execution.getVariable("offers") + "' City: '"
	    		+ execution.getVariable("desiredCity") + "'");
	   
	  }
}
