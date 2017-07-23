package de.wwu.wfm.group12.emtour;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

public class SendVoucher implements JavaDelegate {
	private final static Logger LOGGER = Logger.getLogger("SEND-VOUCHER");

	public void execute(DelegateExecution execution) throws Exception {

		// implement send voucher mail

		// ajimenez
		// send email voucher
		String name = (execution.getVariable("name")).toString();
		String lastName = (execution.getVariable("lastName")).toString();
		String email = (execution.getVariable("email")).toString();
		String desiredCity = (execution.getVariable("desiredCity")).toString();
		String funspark_activities = (execution.getVariable("ActivityDescs")).toString();
		int children = ((Integer) execution.getVariable("children")).intValue();
		int adult = (Integer) execution.getVariable("adult");
		String total_value = (execution.getVariable("total_value")).toString();
		String activitySetPrice = (execution.getVariable("activitySetPrice")).toString();
		String voucher = "City: " + desiredCity;

		boolean hasFunSparkActivity = ((Boolean) execution.getVariable("hasFunSparkActivity")).booleanValue();
		if (hasFunSparkActivity == true) {
			voucher = voucher + "\n" + "Activities: " + funspark_activities + " - €" + activitySetPrice;
		}

		voucher = voucher + " \n\n" + "Number of travelers: " + String.valueOf(adult + children) + " \n\n"
				+ "Total Bill is: €" + total_value;

		// update Bill, set paymentStatus = true
		String customerId = execution.getVariable("customerId").toString();
		boolean paymentStatus = ((Boolean) execution.getVariable("payment_status")).booleanValue();
		DatabaseManagement dbm = new DatabaseManagement();
		dbm.updatePaymentStatus(customerId, paymentStatus);

		SendEmail.messageSendVoucher(name, lastName, email, voucher);
		System.out.println("SendEmail.messageSendVoucher done");

		LOGGER.info("Voucher Sent '" + execution.getVariable("name") + "' customerId: '"
				+ execution.getVariable("customerId") + "  END =)");
	}
}
