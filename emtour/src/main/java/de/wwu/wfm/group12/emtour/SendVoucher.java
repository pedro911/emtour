package de.wwu.wfm.group12.emtour;

import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Named;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendVoucher implements JavaDelegate {
	 private final static Logger LOGGER = Logger.getLogger("SEND-VOUCHER");

	  public void execute(DelegateExecution execution) throws Exception {
	  
		/* what Funspark sends:
		 * { "messageName": "Message_Voucher",
    			"processVariables": {
        			"Activities": { "type": "String", "value": "3hr vintage bike tour;Vatikan Museum;Grouptour Street Food;Visit the Colosseum;Shuttlebus Sightseeing"},
        			"VoucherValue": {"type": "Integer","value": 170}
        		}
        	}		   
		 */
		  
	  //implement send voucher mail
	    
	  LOGGER.info("Voucher Sent '" 
		    		+ execution.getVariable("name") + "' customerId: '"
		    		+ execution.getVariable("customerId")  
		    		+ "  END =)");	
	  
	  		// ajimenez
			// send email voucher
			String name = (execution.getVariable("name")).toString();
			String lastName = (execution.getVariable("lastName")).toString();
			String email = (execution.getVariable("email")).toString();
			String desiredCity = (execution.getVariable("desiredCity")).toString();
			String funspark_activities = (execution.getVariable("Activities")).toString();
			int children = ((Integer) execution.getVariable("children")).intValue();
			int adult = (Integer) execution.getVariable("adult");
		    String total_value = (execution.getVariable("total_value")).toString();
		    String activitySetPrice = (execution.getVariable("activitySetPrice")).toString();
		    String voucher = "City: " + desiredCity;
			
			boolean hasFunSparkActivity = ((Boolean)execution.getVariable("hasFunSparkActivity")).booleanValue();
			if (hasFunSparkActivity == true){
				voucher = voucher + "\n" + "Activities: " + funspark_activities + " - €" + activitySetPrice;
			}
			
			voucher = voucher + " \n\n" + "Number of travelers: " + String.valueOf(adult +children) + " \n\n"+
		    		"Total Bill is: €" + total_value;

			//SendEmail.messageSendVoucher(name, lastName, email, voucher);
			System.out.println("SendEmail.messageSendVoucher done");
	  }
}
