package de.wwu.wfm.group12.emtour;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendPayment implements JavaDelegate {
	 private final static Logger LOGGER = Logger.getLogger("RECOMMENDATIONS-REQUESTS");

	  public void execute(DelegateExecution execution) throws Exception {
	    LOGGER.info("Send Payment '" 
	    		+ execution.getVariable("name") + "' age: '"
	    		+ execution.getVariable("age") + "'...");
	  }
}
