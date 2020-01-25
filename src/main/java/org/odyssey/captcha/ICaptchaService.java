package org.odyssey.captcha;

import org.odyssey.web.error.ReCaptchaInvalidException;

public interface ICaptchaService {

	void processResponse(final String response) throws ReCaptchaInvalidException;

	String getReCaptchaSite();

	String getReCaptchaSecret();
}
