package de.wwu.wfm.group12.emtour;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmail {
	
	public static void messageProposal (String name, String lastName, String email, String recommendations){
		
		String subject = "emTour Recomendations to you";
		
		String text = "Dear customer " + name + " " + lastName + ", \n\n" + 
        		 "We are glad to send you the following recommendations: \n\n"+ 
        		 recommendations + "\n\n" +
        		 "Please read them carefully and confirm your choice within the next 24 hours by answering this email. \n\n" +
        		 "Best regards, \n\n" + 
        		 "emTour team";
		
		sendEmail(email, subject, text);
	}
	
	
	public static void messageAskMoreInformation (String name, String lastName, String email, String moreInformation){
		
		String subject = "emTour requires more information";
		
		String text = "Dear customer " + name + " " + lastName + ", \n\n" + 
        		 "In order to give you a better service, we require from you the following information: \n\n"+ 
        		 moreInformation + "\n\n" +
        		 "Thank you very much for your time. \n\n" +
        		 "Best regards, \n\n" + 
        		 "emTour team";
		
		sendEmail(email, subject, text);
	}


	public static void mesaggeSendBill (String name, String lastName, String email, String bill){
		
		String subject = "emTour Bill";
		
		String text = "Dear customer " + name + " " + lastName + ", \n\n" + 
        		 "We are glad to send you the bill of your travel: \n\n"+ 
        		 bill + "\n\n" +
        		 "Please make the payment within the next 24 hours. \n\n" +
        		 "Best regards, \n\n" + 
        		 "emTour team";
		
		sendEmail(email, subject, text);
	}
	
	
	public static void mesaggeSendVoucher (String name, String lastName, String email, String voucher){
		
		String subject = "emTour Voucher";
		
		String text = "Dear customer " + name + " " + lastName + ", \n\n" + 
        		 "We are glad to send you the voucher of your travel: \n\n"+ 
        		 voucher + "\n\n" +
        		 "Enjoy your trip! \n\n" +
        		 "Best regards, \n\n" + 
        		 "emTour team";
		
		sendEmail(email, subject, text);
	}
	
	
	public static void sendEmail(String email, String subject, String text) {

        final String username = "information.emtour@gmail.com";
        final String password = "Emtour2017";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
          new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
          });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(email));
            message.setSubject(subject);
            message.setText(text);

            Transport.send(message);

            System.out.println("Done sending email");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
