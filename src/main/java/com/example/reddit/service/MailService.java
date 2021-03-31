package com.example.reddit.service;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.reddit.exceptions.EmailNotSentException;
import com.example.reddit.modal.NotificationEmail;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@AllArgsConstructor
@Slf4j
public class MailService {
	
	private final JavaMailSender mailSender;
	private MailContentBuilder builder;
	
	@Async
	public void sendEmail(NotificationEmail notificationEmail) throws EmailNotSentException {
		
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
			helper.setFrom("karafiloski84@gmail.com");
			helper.setSubject(notificationEmail.getSubject());
			helper.setTo(notificationEmail.getRecepient());
			helper.setText(builder.build(notificationEmail.getBody()));
		};
		
		try {
			mailSender.send(messagePreparator);
			log.info("Activation email sent!!");
		} catch (MailException e) {
			
			throw new EmailNotSentException("Problem occured while sending the email");
		}
		
	}

}
