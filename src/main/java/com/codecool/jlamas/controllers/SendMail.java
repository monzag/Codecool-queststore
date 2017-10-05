package com.codecool.jlamas.controllers;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
    private final String USERNAME = "codecool.queststore@gmail.com";
	private final String PASSWORD = "QuestStoreCC";
    private Properties properties;
    private Session session;

    public SendMail() {
        this.setProperties();
        this.startSession();

    }

    private void setProperties() {
        this.properties = new Properties();
    	this.properties.put("mail.smtp.auth", "true");
    	this.properties.put("mail.smtp.starttls.enable", "true");
    	this.properties.put("mail.smtp.host", "smtp.gmail.com");
    	this.properties.put("mail.smtp.port", "587");
    }

    private void startSession() {
        this.session = Session.getInstance(properties,
		    new javax.mail.Authenticator() {
			    protected PasswordAuthentication getPasswordAuthentication() {
				    return new PasswordAuthentication(USERNAME, PASSWORD);
			    }
		    });
    }

    public void sendMail(String mail, String textMessage) {
        try {

			Message message = new MimeMessage(this.session);
			message.setFrom(new InternetAddress("codecool.queststore@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(mail));
			message.setSubject("Account password");
			message.setText(textMessage);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			System.out.println(e.getMessage());
		}
    }


}
