package de.wwu.wfm.group12.emtour;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendBill implements JavaDelegate {
	 private final static Logger LOGGER = Logger.getLogger("RECOMMENDATIONS-REQUESTS");

	  public void execute(DelegateExecution execution) throws Exception {
	    LOGGER.info("Send Bill '" 
	    		+ execution.getVariable("name") + "' age: '"
	    		+ execution.getVariable("age") + "'...");
	    
	    // ajimenez
	    // send email bill
	    String name = (execution.getVariable("name")).toString();
	    String lastName = (execution.getVariable("lastName")).toString();
	    String email = (execution.getVariable("email")).toString();
	    int children = ((Integer) execution.getVariable("children")).intValue();
		int adult = (Integer) execution.getVariable("adult");
		String desiredCity = (execution.getVariable("desiredCity")).toString();
		String funspark_activities = (execution.getVariable("ActivityDescs")).toString();
		String activitySetPrice = (execution.getVariable("activitySetPrice")).toString();
	    String total_value = (execution.getVariable("total_value")).toString();
	    
	    String bill = "City: " + desiredCity;
		
		boolean hasFunSparkActivity = ((Boolean)execution.getVariable("hasFunSparkActivity")).booleanValue();
		if (hasFunSparkActivity == true){
			bill = bill + "\n" + "Activities: " + funspark_activities + " - €" + activitySetPrice;
		}
		
		bill = bill + " \n\n" + "Number of travelers: " + String.valueOf(adult +children) + " \n\n"+
	    		"Total Bill is: €" + total_value;

	    //SendEmail.mesaggeSendBill (name, lastName, email, bill);
	    System.out.println("SendEmail.mesaggeSendBill done...."+name+ lastName+ email+ bill);
	    
	  }
}
