package com.mscconfig.services.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Vladimir
 * Date: 06.08.13
 * Time: 13:54
 * Расширенный хандлер удачной аутентификации, при успешном входе на сайт
 * записываем в карту доп. данные пользователя и записываем в аттрибут сессии( header.jsp работает с этими данными)
 */
@Service("customLoginSuccessHandler")
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	public static final Logger log = LoggerFactory.getLogger(CustomLoginSuccessHandler.class);



	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException, ServletException, IOException {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			CustomUserDetails customUserDetails =(CustomUserDetails) auth.getPrincipal();

			String username = customUserDetails.getUsername();
			String firstName = customUserDetails.getFirstName();
			String lastName = customUserDetails.getLastName();

			Map<String, String> loggedUserMap =  new HashMap<String, String>();
			loggedUserMap.put("firstName",firstName);
			loggedUserMap.put("lastName", lastName);

			HttpSession session = request.getSession();
			session.setAttribute("loggedUserMap", loggedUserMap);
			if(log.isInfoEnabled()) log.info("Login success for USER: '"+username+"' - "+firstName+" "+lastName);
		}
		super.onAuthenticationSuccess(request, response, authentication);
	}
}
