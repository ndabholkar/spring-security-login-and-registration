package org.odyssey.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.odyssey.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;

public class AbstractAuthenticationSuccessHandler {

	@Autowired
	ActiveUserStore activeUserStore;

	protected RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	protected void onAuthenticationSuccess(HttpServletRequest request, Authentication authentication) {
		final HttpSession session = request.getSession(false);
		if (session != null) {
			session.setMaxInactiveInterval(30 * 60);
			String username;
			if (authentication.getPrincipal() instanceof User) {
				username = ((User) authentication.getPrincipal()).getEmail();
			} else {
				username = authentication.getName();
			}

			LoggedUser user = new LoggedUser(username, activeUserStore);
			session.setAttribute("user", user);
		}
		clearAuthenticationAttributes(request);
	}

	protected void clearAuthenticationAttributes(final HttpServletRequest request) {
		final HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}

	public void setRedirectStrategy(final RedirectStrategy redirectStrategy) {
		this.redirectStrategy = redirectStrategy;
	}

	protected RedirectStrategy getRedirectStrategy() {
		return redirectStrategy;
	}
}
