package org.odyssey.security;

import java.io.IOException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.odyssey.persistence.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

//@Component("myAuthenticationSuccessHandler")
public class MyCustomLoginAuthenticationSuccessHandler extends AbstractAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication)
		throws IOException {
		addWelcomeCookie(gerUserName(authentication), response);
		redirectStrategy.sendRedirect(request, response, "/homepage.html?user=" + authentication.getName());

		onAuthenticationSuccess(request, authentication);
	}

	private String gerUserName(final Authentication authentication) {
		return ((User) authentication.getPrincipal()).getFirstName();
	}

	private void addWelcomeCookie(final String user, final HttpServletResponse response) {
		Cookie welcomeCookie = getWelcomeCookie(user);
		response.addCookie(welcomeCookie);
	}

	private Cookie getWelcomeCookie(final String user) {
		Cookie welcomeCookie = new Cookie("welcome", user);
		welcomeCookie.setMaxAge(60 * 60 * 24 * 30); // 30 days
		return welcomeCookie;
	}
}