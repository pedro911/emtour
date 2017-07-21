package de.wwu.wfm.group12.emtour;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;
import java.util.regex.Pattern;

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
		
		// ajimenez
		// input variables
		String country = (execution.getVariable("country")).toString();
		String budget = (execution.getVariable("budget")).toString();
		String children = (execution.getVariable("children")).toString();
		System.out.println("Our recommendation is (input): |" + country + "|" + budget + "|" + children + "|");

		// get variable of recommendations from DMN
		String desiredCity = (execution.getVariable("desiredCity")).toString();
		System.out.println("Our recommendation is (variables): " + desiredCity);

		// split recommendations
		String[] desired = desiredCity.split(Pattern.quote("|"));
		String recommendedDestination = desired[0];
		String description = desired[1];
		String price = desired[2];
		System.out.println("Our recommendation is (split): " + recommendedDestination + " - " + description + " - " + price);

		// put recommendations in a nice way
		String outputRecommendation = recommendedDestination + " - " + description + " - Subtotal: €" + price;
		execution.setVariable("desiredCity", outputRecommendation);
		String newDesiredCity = (execution.getVariable("desiredCity")).toString();
		System.out.println("Our recommendation is (outputRecommendation): " + newDesiredCity);
		
		// save splitted variables from DMN result
		execution.setVariable("recommendedDestination", recommendedDestination);
		execution.setVariable("description", description);
		execution.setVariable("price", price);
		
		// replace with semicolons to send to Funspark
		String semicolonsRecommendation = recommendedDestination + ";" + description + ";" + price;
		System.out.println("Our recommendation sent to Funspark: " + semicolonsRecommendation);
				
		
		
		// RESTeasy Jboss API to send recommendations to Funspark
		/*		
		String input;
		ClientRequest request = new ClientRequest("http://localhost:8080/engine-rest/process-definition/key/funspark-id/start");
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
		*/
		
	}

}
