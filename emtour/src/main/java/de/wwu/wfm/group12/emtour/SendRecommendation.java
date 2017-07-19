package de.wwu.wfm.group12.emtour;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;



public class SendRecommendation implements JavaDelegate {
	private final static Logger LOGGER = Logger.getLogger("SEND-RECOMMENDATIONS");

	public void execute(DelegateExecution execution) throws Exception {
		LOGGER.info("Send Recommendations '" + execution.getVariable("name") + "' desired City: '"
				+ execution.getVariable("desiredCity") + "'...");

		// create db record with customer info and return customerID
		CustomerDatabase cdb = new CustomerDatabase();
		cdb.createCustomerDbRecord(execution);
		String customerId = cdb.lastCustomerId();
		execution.setVariable("customerId", customerId);
		
		// RESTeasy Jboss API to send recommendations to Funspark
				
		String input;
		ClientRequest request = new ClientRequest("http://192.168.1.30:8080/engine-rest/process-definition/key/funspark-id/start");
		request.accept("application/json");
		
		input = "{\"variables\":{ "
					+ "\"customerId\":{\"value\":\""+customerId+"\", \"type\": \"String\"},"
					+ "\"name\":{\"value\":\""+execution.getVariable("name")+"\", \"type\": \"String\"},"				
					+ "\"desiredCity\":{\"value\":\""+execution.getVariable("desiredCity")+"\", \"type\": \"String\"}"
					+"}"
				+ "}";
		
		System.out.println(input);
		request.body("application/json", input);

		ClientResponse<String> response = request.post(String.class);

		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new ByteArrayInputStream(response.getEntity().getBytes())));	
		
		String output;
		System.out.println("Output from Server. Status: ");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}
		
	}

}
