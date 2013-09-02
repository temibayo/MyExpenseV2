package controller;

import java.util.Properties;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.mail.Message;

import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.RandomStringUtils;

import dataAccessor.UserProfileAccessor;

@ManagedBean
public class ForgotPasswordBean {
	
	private String userEmail;
	private final int PASSWORD_LENGTH = 8;
	
	public String resetPassword(){
	     FacesContext context = FacesContext.getCurrentInstance();
	     
         //check if the email exists in the database
         UserProfileAccessor upAccessor = new UserProfileAccessor();
         
         if(upAccessor.isExistingEmail(userEmail)){
                        
                 //generate a new password and update the database with the new password
                 String newPassword = RandomStringUtils.randomAlphabetic(PASSWORD_LENGTH);
                 upAccessor.updateUserPassword(userEmail, newPassword);
        
                 //send an email with the new password to the user
                 final String senderUsername = "expensetrackerqa@gmail.com";
                 final String senderPassword = "adepoju83";
  
                 Properties props = new Properties();
                 props.put("mail.smtp.auth", "true");
                 props.put("mail.smtp.starttls.enable", "true");
                 props.put("mail.smtp.host", "smtp.gmail.com");
                 props.put("mail.smtp.port", "587");
  
                 Session session = Session.getInstance(props,
                   new javax.mail.Authenticator() {
                         protected PasswordAuthentication getPasswordAuthentication() {
                                 return new PasswordAuthentication(senderUsername, senderPassword);
                         }
                   });
  
                 try {
  
                         Message message = new MimeMessage(session);
                         message.setFrom(new InternetAddress(senderUsername));
                         message.setRecipients(Message.RecipientType.TO,
                                 InternetAddress.parse(userEmail));
                         message.setSubject("Expense Tracker Password Reset");
                         message.setText("Hello,"
                                 + "\n\n Your password has been reset on Expense Tracker"
                                 + "\n\n Your new password is "+newPassword);
  
                         Transport.send(message);
                         context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"","Your password has been reset and sent to the email address you provided"));
                         return(null);
                 } catch (MessagingException e) {
                         throw new RuntimeException(e);
                 }
                
       	 }
       	 else{
        	 	context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"","This account does not exist in our system. Please try again"));
        	 	return(null);
       	 }
        
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

}
