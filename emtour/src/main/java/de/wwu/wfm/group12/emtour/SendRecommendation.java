package de.wwu.wfm.group12.emtour;

import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendRecommendation implements JavaDelegate {
	private final static Logger LOGGER = Logger.getLogger("RECOMMENDATIONS-REQUESTS");

	public void execute(DelegateExecution execution) throws Exception {
		LOGGER.info("Send Recommendations '" + execution.getVariable("name") + "' country: '"
				+ execution.getVariable("country") + "'...");

		// create db record with customer info
		CustomerDatabase cd = new CustomerDatabase();
		cd.createCustomerDbRecord(execution);
		
		// implement REST API to send recommendations to Funspark

	}

}
