package org.odyssey.security;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

	private HttpServletRequest request;

	private LoginAttemptService loginAttemptService;

	@Autowired
	public AuthenticationFailureListener(HttpServletRequest request, LoginAttemptService loginAttemptService) {
		this.request = request;
		this.loginAttemptService = loginAttemptService;
	}

	@Override
	public void onApplicationEvent(final AuthenticationFailureBadCredentialsEvent e) {
		// final WebAuthenticationDetails auth = (WebAuthenticationDetails) e.getAuthentication().getDetails();
		// if (auth != null) {
		// loginAttemptService.loginFailed(auth.getRemoteAddress());
		// }
		final String xfHeader = request.getHeader("X-Forwarded-For");
		if (xfHeader == null) {
			loginAttemptService.loginFailed(request.getRemoteAddr());
		} else {
			loginAttemptService.loginFailed(xfHeader.split(",")[0]);
		}
	}

}