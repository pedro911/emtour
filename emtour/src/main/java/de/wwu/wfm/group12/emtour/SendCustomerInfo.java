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

public class SendCustomerInfo implements JavaDelegate{
	 private final static Logger LOGGER = Logger.getLogger("SEND-CUSTOMER-INFO");

	  public void execute(DelegateExecution execution) throws Exception {
	    LOGGER.info("Send Customer Information '" 
	    		+ execution.getVariable("name") + "' customerId: '"
	    		+ execution.getVariable("customerId") + "'...");
		
	    
	    String input;
		//ClientRequest request = new ClientRequest("http://192.168.1.30:8080/engine-rest/message");
	    ClientRequest request = new ClientRequest("http://178.6.170.56:8080/engine-rest/message");
	    request.accept("application/json");
		
		input = "{\"messageName\":\"Message_AddInf\","
				+ "\"correlationKeys\":{ "
						+ "\"customerId\":{\"value\":\""+execution.getVariable("customerId")+"\", \"type\": \"String\"}"											
					+ "},"
				+ "\"processVariables\":{ "
						+ "\"Healthy\":{\"value\":\""+execution.getVariable("Healthy")+"\", \"type\": \"Boolean\"}"												
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
