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
		/*
		// build HTTP request with all variables as parameters
		HttpClient client = HttpClients.createDefault();
		RequestBuilder requestBuilder = RequestBuilder.get()
		.setUri("http://192.168.1.30:8080/engine-rest/request-received")
		.addParameter("customerid", customerId);
		for (String variable : execution.getVariableNames()) {
			requestBuilder.addParameter(variable, String.valueOf(execution.getVariable(variable)));
		}
		// execute request
		HttpUriRequest request = requestBuilder.build();
		HttpResponse response = client.execute(request);
		// log debug information
		System.out.println(request.getURI());
		System.out.println(response.getStatusLine());
		
			
		HttpConnector http = Connectors.getConnector(HttpConnector.ID);
		http.createRequest()
		  .post()
		  .url("http://camunda.org")
		  .execute();
		
		HttpResponse response = http.createRequest()
				  .get()
				  .header("Accept", "application/json")
				  .url("http://192.168.1.30:8080/engine-rest/process-definition/key/funspark-id/start")
				  .execute();
		
		
		HttpRequest request = http.createRequest();
		HttpResponse response = request.getR
		request.setRequestParameter("method", "GET");
		request.setRequestParameter("url", "192.168.1.30:8080/engine-rest/authorization");
		request.setRequestParameter("payload", "hello world!");
		System.out.println(request.getUrl());
		Integer statusCode = response.getStatusCode();
		String contentTypeHeader = response.getHeader("Content-Type");
		String body = response.getResponse();
		response.getResponseParameter("statusCode");
		response.getResponseParameter("headers");
		response.getResponseParameter("response");
		//System.out.println(response.getStatusLine());
		 */
		
		String input;
		ClientRequest request = new ClientRequest("http://192.168.1.30:8080/engine-rest/process-definition/key/funspark-id/start");
		// "http://requestb.in/w1pjs5w1");
		request.accept("application/json");
		
		input = "{\"variables\":{ "
				+ "\"name\":{\"value\":\""+execution.getVariable("name")+"\", \"type\": \"String\"},"
				+ "\"desiredCity\":{\"value\":\""+execution.getVariable("desiredCity")+"\", \"type\": \"String\"}},"
				+ "\"businessKey\":\""+execution.getId()+"\"}";
		
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
