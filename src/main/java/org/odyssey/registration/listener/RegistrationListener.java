package org.odyssey.registration.listener;

import java.util.UUID;
import org.odyssey.persistence.model.User;
import org.odyssey.registration.OnRegistrationCompleteEvent;
import org.odyssey.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

	private IUserService service;

	private MessageSource messageSource;

	private JavaMailSender mailSender;

	private Environment env;

	@Autowired
	public RegistrationListener(IUserService service, MessageSource messageSource, JavaMailSender mailSender, Environment env) {
		this.service = service;
		this.messageSource = messageSource;
		this.mailSender = mailSender;
		this.env = env;
	}

	@Override
	public void onApplicationEvent(final OnRegistrationCompleteEvent event) {
		this.confirmRegistration(event);
	}

	private void confirmRegistration(final OnRegistrationCompleteEvent event) {
		final User user = event.getUser();
		final String token = UUID.randomUUID().toString();
		service.createVerificationTokenForUser(user, token);

		final SimpleMailMessage email = constructEmailMessage(event, user, token);
		mailSender.send(email);
	}

	//

	private final SimpleMailMessage constructEmailMessage(final OnRegistrationCompleteEvent event, final User user, final String token) {
		final String recipientAddress = user.getEmail();
		final String subject = "Registration Confirmation";
		final String confirmationUrl = event.getAppUrl() + "/registrationConfirm.html?token=" + token;
		final String message = messageSource.getMessage("message.regSucc", null, event.getLocale());
		final SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipientAddress);
		email.setSubject(subject);
		email.setText(message + " \r\n" + confirmationUrl);
		email.setFrom(env.getProperty("support.email"));
		return email;
	}

}
