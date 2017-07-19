package de.wwu.wfm.group12.emtour;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

public class SendPayment implements JavaDelegate {
	 private final static Logger LOGGER = Logger.getLogger("SEND-PAYMENT");

	  public void execute(DelegateExecution execution) throws Exception {
	    LOGGER.info("Send Payment '" 
	    		+ execution.getVariable("name") + "' customerId: '"
	    		+ execution.getVariable("customerId") + "'...");
	    
	    String input;
		ClientRequest request = new ClientRequest("http://192.168.1.30:8080/engine-rest/message");
		request.accept("application/json");
		
		input = "{\"messageName\":\"payment_confirmation\","
				+ "\"correlationKeys\":{ "
						+ "\"customerId\":{\"value\":\""+execution.getVariable("customerId")+"\", \"type\": \"String\"}"											
					+ "},"
				+ "\"processVariables\":{ "
						+ "\"payment_status\":{\"value\":\""+execution.getVariable("payment_status")+"\", \"type\": \"Boolean\"},"
						+ "\"funspark_banktransfer_code\":{\"value\":\""+execution.getVariable("funspark_banktransfer_code")+"\", \"type\": \"String\"},"
						+ "\"funspark_selected_activities\":{\"value\":\""+execution.getVariable("funspark_selected_activities")+"\", \"type\": \"String\"}"
					+ "}"
				+"}";
		
		System.out.println(input);
		request.body("application/json", input);

		ClientResponse<String> response = request.post(String.class);

		if (response.getStatus() != 204) {
			
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new ByteArrayInputStream(response.getEntity().getBytes())));	
			
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
