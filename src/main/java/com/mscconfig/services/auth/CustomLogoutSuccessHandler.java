package com.mscconfig.services.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * User: Vladimir
 * Date: 07.08.13
 * Time: 10:31
 * Please describe this stuff
 */
@Service("customLogoutSuccessHandler")
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
	public static final Logger log = LoggerFactory.getLogger(CustomLogoutSuccessHandler.class);
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
								Authentication auth) throws IOException, ServletException {

		if (auth != null) {
			HttpSession session = request.getSession();
			CustomUserDetails customUserDetails =(CustomUserDetails) auth.getPrincipal();
			if(log.isInfoEnabled()) log.info("Logout success for USER: '"+customUserDetails.getUsername()+"' - "+customUserDetails.getFirstName()+" "+customUserDetails.getLastName());
			session.removeAttribute("loggedUserMap");

		}

		setDefaultTargetUrl("/login");
		super.onLogoutSuccess(request, response, auth);
	}
}
