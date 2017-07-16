package de.wwu.wfm.group12.emtour;

import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.connect.Connectors;
import org.camunda.connect.httpclient.HttpConnector;
import org.camunda.connect.httpclient.HttpRequest;
import org.camunda.connect.httpclient.HttpResponse;

import connectjar.org.apache.http.client.HttpClient;
import connectjar.org.apache.http.client.methods.HttpUriRequest;
import connectjar.org.apache.http.client.methods.RequestBuilder;
import connectjar.org.apache.http.impl.client.HttpClients;



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
		
	/*	// build HTTP request with all variables as parameters
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
		  .get()
		  .url("http://camunda.org")
		  .execute();
		
		HttpResponse response = http.createRequest()
				  .get()
				  .header("Accept", "application/json")
				  .url("http://camunda.org")
				  .execute();
		HttpRequest request = http.createRequest();
		request.setRequestParameter("method", "GET");
		request.setRequestParameter("url", "http://camunda.org");
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
	}

}
