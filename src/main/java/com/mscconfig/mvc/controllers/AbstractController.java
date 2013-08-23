package com.mscconfig.mvc.controllers;

/**
 * User: Vladimir
 * Date: 23.08.13
 * Time: 10:40
 * Class methods for common controllers
 */
public abstract class AbstractController {
	protected String getRedirectedPath(String requestMapping) {
		StringBuilder redirectViewPath = new StringBuilder();
		redirectViewPath.append("redirect:");
		redirectViewPath.append(requestMapping);
		return redirectViewPath.toString();
	}
}
