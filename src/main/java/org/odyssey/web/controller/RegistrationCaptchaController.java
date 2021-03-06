package org.odyssey.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.odyssey.captcha.ICaptchaService;
import org.odyssey.persistence.model.User;
import org.odyssey.registration.OnRegistrationCompleteEvent;
import org.odyssey.service.IUserService;
import org.odyssey.web.dto.UserDto;
import org.odyssey.web.util.GenericResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RegistrationCaptchaController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	private IUserService userService;

	private ICaptchaService captchaService;

	private ApplicationEventPublisher eventPublisher;

	@Autowired
	public RegistrationCaptchaController(IUserService userService, ICaptchaService captchaService,
		ApplicationEventPublisher eventPublisher) {
		this.userService = userService;
		this.captchaService = captchaService;
		this.eventPublisher = eventPublisher;
	}

	@RequestMapping(value = "/user/registrationCaptcha", method = RequestMethod.POST)
	@ResponseBody
	public GenericResponse captchaRegisterUserAccount(@Valid final UserDto accountDto, final HttpServletRequest request) {

		final String response = request.getParameter("g-recaptcha-response");
		captchaService.processResponse(response);

		LOGGER.debug("Registering user account with information: {}", accountDto);

		final User registered = userService.registerNewUserAccount(accountDto);
		eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), getAppUrl(request)));
		return new GenericResponse("success");
	}

	private String getAppUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

}
