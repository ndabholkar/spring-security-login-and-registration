package org.odyssey.web.controller;

import java.util.Locale;
import org.odyssey.security.ActiveUserStore;
import org.odyssey.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

	ActiveUserStore activeUserStore;

	IUserService userService;

	@Autowired
	public UserController(ActiveUserStore activeUserStore, IUserService userService) {
		this.activeUserStore = activeUserStore;
		this.userService = userService;
	}

	@RequestMapping(value = "/loggedUsers", method = RequestMethod.GET)
	public String getLoggedUsers(final Locale locale, final Model model) {
		model.addAttribute("users", activeUserStore.getUsers());
		return "users";
	}

	@RequestMapping(value = "/loggedUsersFromSessionRegistry", method = RequestMethod.GET)
	public String getLoggedUsersFromSessionRegistry(final Locale locale, final Model model) {
		model.addAttribute("users", userService.getUsersFromSessionRegistry());
		return "users";
	}
}
