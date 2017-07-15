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
		CustomerDatabase cdb = new CustomerDatabase();
		cdb.createCustomerDbRecord(execution);
		String customerId = cdb.lastCustomerId();
		
		// implement REST API to send recommendations to Funspark
		/*
		{
		    "businessKey": "Instance_001",
		    "messageName": "Message_AddInf",
		    "processInstanceId": "7cc0d35d-680b-11e7-a49f-846920524153",
		    "processVariables": {"Healthy": {
		        "type": "Boolean",
		        "value": "false"
		    }}
		}*/

	}

}
