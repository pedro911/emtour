package de.wwu.wfm.group12.emtour;

import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendProposals implements JavaDelegate {
	private final static Logger LOGGER = Logger.getLogger("SEND-PROPOSALS");

	public void execute(DelegateExecution execution) throws Exception {
		LOGGER.info("Send Proposals to '" + execution.getVariable("name") + "' email: '"
				+ execution.getVariable("email") + "'...");
		/* what Funspark will send
		 * {"messageName":"SendActivities",
				"correlationKeys":{ "customerId":{"value":"50", "type": "String"}
				},    
    			"processVariables": {
        			"ActivityDescs": {"type": "String","value": "Boat Cruise on the Canal;Welcome to Manchester: Private Guided Tour;Hard Rock Café (Without Waiting Time)"},
        			"ActivityIDs": {"type": "String","value": "149;150;148"},
					"ActivityPrices": {"type": "String", "value": "55;66;88"}
    			}
    		}
		 * */
		// ajimenez
		// send email proposal
		String name = (execution.getVariable("name")).toString();
		String lastName = (execution.getVariable("lastName")).toString();
		String email = (execution.getVariable("email")).toString();
		String desiredCity = (execution.getVariable("desiredCity")).toString();
		String funspark_activities = (execution.getVariable("ActivityDescs")).toString();
		String recommendations = desiredCity + "\n" + "Activities: \n";
		String FunsparkActivityPrices = (execution.getVariable("ActivityPrices")).toString();
		
		String[] funspark_activitiesArray = funspark_activities.split(Pattern.quote(";"));
		String[] FunsparkActivityPricesArray = FunsparkActivityPrices.split(Pattern.quote(";"));
		for (int i = 0; i < funspark_activitiesArray.length; i++){
			recommendations = recommendations + "- " + funspark_activitiesArray[i] + " €" + FunsparkActivityPricesArray[i] + " ";
		}
		
		// Alejandro, please add the FunsparkActivityPrices into the email sending, the price will match with the ActivityDescs position  
		SendEmail.messageProposal(name, lastName, email, recommendations);
		System.out.println("SendEmail.messageProposal done.... "+name+ lastName+ email+ recommendations);

	}

}
