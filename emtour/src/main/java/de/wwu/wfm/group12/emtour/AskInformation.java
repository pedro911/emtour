package de.wwu.wfm.group12.emtour;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class AskInformation implements JavaDelegate {
	 private final static Logger LOGGER = Logger.getLogger("RECOMMENDATIONS-REQUESTS");

	  public void execute(DelegateExecution execution) throws Exception {
	    LOGGER.info("Ask for more information '" 
	    		+ execution.getVariable("name") + "' customerId: '"
	    		+ execution.getVariable("customerId") + "'...");
	  	  
	    // implement email sending, add customer_info_updated variable
	    // ajimenez
	    // send email to ask for more information
	    String name = (execution.getVariable("name")).toString();
	    String lastName = (execution.getVariable("lastName")).toString();
	    String email = (execution.getVariable("email")).toString();
	    String more_info_request = (execution.getVariable("more_info_request")).toString();

	    SendEmail.messageAskMoreInformation (name, lastName, email, more_info_request);
	    System.out.println("SendEmail.messageAskMoreInformation done");
	    
	  }
}
