package com.jipzip.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMessage.RecipientType;

@Component
public class MailManager {
	@Value("${spring.mail.username}")
	private String sender;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void send(String sendTo, String sub, String con) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		mimeMessage.setFrom(sender);
		
		//받는 사람
		mimeMessage.addRecipient(RecipientType.TO, new InternetAddress(sendTo));
		mimeMessage.setSubject(sub);
		mimeMessage.setText(con);
		javaMailSender.send(mimeMessage);
	}
	
}
