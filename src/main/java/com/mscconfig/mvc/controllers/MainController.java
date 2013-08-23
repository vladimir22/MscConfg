package com.mscconfig.mvc.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * User: Vladimir
 * Date: 23.08.13
 * Time: 10:40
 * Main mappings in controllers
 */
@Controller
public class MainController {

	private static final Logger log = LoggerFactory.getLogger(MainController.class);

	public static final String VIEW_INTERNAL_SERVER_ERROR = "error";
	public static final String VIEW_NOT_FOUND = "404";
	public static final String VIEW_ACCESS_DENIED = "403";


	@RequestMapping(value = "/404", method = RequestMethod.GET)
	public String show404Page() {
		log.debug("Rendering 404 page");
		return VIEW_NOT_FOUND;
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String show403Page() {
		log.debug("Rendering 403 page");
		return VIEW_ACCESS_DENIED;
	}

// TODO - Temprorary mappings.Will be turn off after make right tunning in web.xml: Dispatcher Servlet & Spring security */

	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	@RequestMapping("/")
	public String home() {
		return "index";
	}

	@RequestMapping("/login")
	public String login(ModelMap model) {   /*Spring 3.2.0.Release заработал с spring Security 3.1.4 , только через этот маппинг (3.1.2.RELEASE работало на прямую :( где-то копать надо в web.xml: Dispatcher servlet mapping)*/
		return "login";
	}

}
