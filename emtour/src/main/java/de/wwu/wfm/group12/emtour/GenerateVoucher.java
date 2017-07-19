package de.wwu.wfm.group12.emtour;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
//import org.camunda.bpm.engine.delegate.DelegateExecution;
//import org.camunda.bpm.engine.delegate.JavaDelegate;

public class GenerateVoucher {

	public static void main( String[] name, String lastName )
    {
    	int bill;
    	double price;
    	double total_value = 1; //get from bill process class later 	
    	
    	String time = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
       
    	
    	StringBuilder sb = new StringBuilder();
    	sb.append("");
    	sb.append(total_value);
    	String total = sb.toString();
		
    	
		FileOutputStream fop = null;
		File file;
		String content = "Payment Voucher" + "\n"  + "Sum of Amount: " + total + "\n" + "Date of Payment: " + time + "\n"+ "Payed by: " + name + lastName + "\n"+ "Signed by: emTour" + "\n";

		try {
			int increase = 1;

			file = new File("c:\\" + "voucher" + increase + ".txt");
			fop = new FileOutputStream(file);

			if (!file.exists()) {
				increase++;
				file.createNewFile();
			}

			// get the content in bytes
			byte[] contentInBytes = content.getBytes();

			fop.write(contentInBytes);
			fop.flush();
			fop.close();


		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
	
    }

}
