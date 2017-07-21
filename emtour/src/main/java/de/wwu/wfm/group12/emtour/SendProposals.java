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
		// send email proposal
		String name = (execution.getVariable("name")).toString();
		String lastName = (execution.getVariable("lastName")).toString();
		String email = (execution.getVariable("email")).toString();
		String desiredCity = (execution.getVariable("desiredCity")).toString();
		String funspark_activities = (execution.getVariable("funspark_activities")).toString();
		String recommendations = desiredCity + "\n" + "Activities: " + funspark_activities;

		SendEmail.messageProposal(name, lastName, email, recommendations);
		System.out.println("SendEmail.messageProposal done");

	}

}
