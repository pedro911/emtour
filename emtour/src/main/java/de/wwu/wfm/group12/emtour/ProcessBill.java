package de.wwu.wfm.group12.emtour;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class ProcessBill implements JavaDelegate {
	 private final static Logger LOGGER = Logger.getLogger("RECOMMENDATIONS-REQUESTS");

	  public void execute(DelegateExecution execution) throws Exception {
	    	    
	   int activitySetPrice = 100;
	   int bill;
	   
	   int id = (Integer)execution.getVariable("id");
	   int children = ((Integer) execution.getVariable("children")).intValue();
	   int adult = (Integer) execution.getVariable("adult");
	   int number_of_days = (Integer) execution.getVariable("number_of_days");
	   int budget = (Integer) execution.getVariable("budget");
	   
	// Generate bill depending on client's budget & activity set price provided from FunSpark
	   bill = budget * adult + (budget/2) * children + activitySetPrice;  
	   
	   System.out.println("Total Bill is: " + bill + " Persons: " + String.valueOf(adult +children) + " Country: " + execution.getVariable("country"));
	   
	  /* LOGGER.info("Total Bill is:'" 
	    		+ bill + "' Country: '"
	    		+ execution.getVariable("country") + "'");*/
	   
	  }
}
}
