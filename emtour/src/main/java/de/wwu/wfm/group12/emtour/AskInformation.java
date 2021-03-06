package de.wwu.wfm.group12.emtour;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class AskInformation implements JavaDelegate {
	 private final static Logger LOGGER = Logger.getLogger("RECOMMENDATIONS-REQUESTS");

	  public void execute(DelegateExecution execution) throws Exception {
	    LOGGER.info("Ask for more information '" 
	    		+ execution.getVariable("name") + "' customerId: '"
	    		+ execution.getVariable("customerId") + "'...");
	  		    
	    // ajimenez
	    // send email to ask for more information
	    String name = (execution.getVariable("name")).toString();
	    String lastName = (execution.getVariable("lastName")).toString();
	    String email = (execution.getVariable("email")).toString();
	    String more_info_request = "Are you healthy?";

	    SendEmail.messageAskMoreInformation (name, lastName, email, more_info_request);
	    System.out.println("SendEmail.messageAskMoreInformation done");
	    
	  }
}
