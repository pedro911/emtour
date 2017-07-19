package de.wwu.wfm.group12.emtour;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendVoucher implements JavaDelegate {
	 private final static Logger LOGGER = Logger.getLogger("SEND-VOUCHER");

	  public void execute(DelegateExecution execution) throws Exception {
	  
	  //implement send voucher mail
	    
	  LOGGER.info("Voucher Sent '" 
		    		+ execution.getVariable("name") + "' customerId: '"
		    		+ execution.getVariable("customerId")  
		    		+ "END ...");	    
	  }
}
