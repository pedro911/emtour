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
		DatabaseManagement dbm = new DatabaseManagement();
		dbm.createCustomerDbRecord(execution);		
		String customerId = dbm.lastCustomerId();		
		execution.setVariable("customerId", customerId);
		
		// ajimenez
		// input variables
		String country = (execution.getVariable("country")).toString();
		String budget = (execution.getVariable("budget")).toString();
		String children = (execution.getVariable("children")).toString();
		String adults = (execution.getVariable("adult")).toString();
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
		
		// prepare values to send
		// replace with semicolons to send to Funspark
		String semicolonsRecommendation = recommendedDestination + ";" + description + ";" + price;
		System.out.println("Our recommendation sent to Funspark: " + semicolonsRecommendation);
		budget = budget.replaceAll(",", ".");
		price = price.replaceAll(",", ".");
		Double budgetFunspark = Double.parseDouble(budget);
		Double priceDouble = Double.parseDouble(price);
		budgetFunspark = budgetFunspark - priceDouble;
		int adultsInt = Integer.parseInt(adults);
		int childrenInt = Integer.parseInt(children);
		
		//save recommendations db
		dbm.createEmTourRecommendationDbRecord(Integer.parseInt(customerId),priceDouble,description,recommendedDestination);
				
		// RESTeasy Jboss API to send recommendations to Funspark
		
		String input;
		ClientRequest request = new ClientRequest("http://178.6.170.56:8080/engine-rest/message");
		//ClientRequest request = new ClientRequest("http://192.168.1.30:8080/engine-rest/message");
		
		request.accept("application/json");
				
		input = "{\"messageName\":\"Message_Start\","
					+"\"processVariables\":{ "
						+ "\"customerId\":{\"value\":\""+customerId+"\", \"type\": \"String\"},"
						+ "\"City\":{\"value\":\""+recommendedDestination+"\", \"type\": \"String\"},"
						+ "\"Start_Date\":{\"value\":\""+execution.getVariable("travelStartDate")+"\", \"type\": \"String\"},"	
						+ "\"End_Date\":{\"value\":\""+execution.getVariable("travelEndDate")+"\", \"type\": \"String\"},"
						+ "\"Budget\":{\"value\":\""+budgetFunspark+"\", \"type\": \"Double\"},"
						+ "\"Adults\":{\"value\":\""+adultsInt+"\", \"type\": \"Integer\"},"
						+ "\"Children\":{\"value\":\""+childrenInt+"\", \"type\": \"Integer\"}"
						+"}"
				+ "}";
		
		System.out.println(input);
		request.body("application/json", input);

		ClientResponse<String> response = request.post(String.class);

		if (response.getStatus() != 204) {			
			BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes())));			
			String output;
			System.out.println("Output from Server. Status: ");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
		}
		else System.out.println("Output from Server. Status: "+response.getStatus());
		
	}

}
