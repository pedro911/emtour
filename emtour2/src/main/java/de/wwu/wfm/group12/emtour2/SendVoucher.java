package de.wwu.wfm.group12.emtour2;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendVoucher implements JavaDelegate {
	 private final static Logger LOGGER = Logger.getLogger("RECOMMENDATIONS-REQUESTS");

	  public void execute(DelegateExecution execution) throws Exception {
	    LOGGER.info("Send Voucher '" 
	    		+ execution.getVariable("name") + "' age: '"
	    		+ execution.getVariable("age")  
	    		+ "END ...");
	  }
}
