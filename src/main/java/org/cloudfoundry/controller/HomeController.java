package org.cloudfoundry.controller;


import org.cloudfoundry.config.SwiftConnector;
import org.javaswift.joss.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	@Autowired
	SwiftConnector swiftConnector;
	
	/**
	 * Glsterfs-Swift test
	 */
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home(Model model) {
		Account account = swiftConnector.getAccount();
		
		model.addAttribute("tenant", swiftConnector.getTenantname());
		model.addAttribute("id", swiftConnector.getUsername());
		model.addAttribute("pass", swiftConnector.getPassword());
		model.addAttribute("url", swiftConnector.getAuthurl());
		
		
		
		return "home";
	}
}
