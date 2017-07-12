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

		// send email proof
		String name = (execution.getVariable("name")).toString();
		String lastName = (execution.getVariable("lastName")).toString();
		String email = (execution.getVariable("email")).toString();

		SendEmail.messageProposal(name, lastName, email, newDesiredCity);

	}

}
