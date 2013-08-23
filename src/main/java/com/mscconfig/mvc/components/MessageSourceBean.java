package com.mscconfig.mvc.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Locale;

/**
 * User: Vladimir
 * Date: 22.08.13
 * Time: 11:07
 * Component extends functionality  MessageSource in working with static resources - messages
 */
@Component("messageSourceBean")
public class MessageSourceBean {
	public static final Logger log = LoggerFactory.getLogger(MessageSourceBean.class);

	@Resource
	private MessageSource messageSource;

	/**
	 * Return static messages from "file.properites"
	 *  Example string :  customer.name=Yong Mook Kim, age : {0}, URL : {1}
	 * @param messageCode -  message name (Ex:"customer.name")
	 * @param locale  - required Locale (Ex:Locale.ENGLISH)
	 * @param messageParameters - required params for compose message (Ex: new Object[] { 32,"http://www.blabla.com" }
	 * @return - localized message
	 */
	public String getMessage(String messageCode,Locale locale, Object... messageParameters) {
		String message = messageSource.getMessage(messageCode, messageParameters, locale);
		log.debug("messageSource.getMessage{}:", message);
		return message;
	}

	/**
	 *
	 * @param messageCode
	 * @param messageParameters
	 * @return
	 */
	public String getMessage(String messageCode, Object... messageParameters) {
		Locale locale = LocaleContextHolder.getLocale();
		log.debug("Current locale is : ", locale);
		String message =getMessage(messageCode,locale, messageParameters );
		return message;
	}

	public String getMessage(String messageCode , Locale locale) {
     String message = getMessage(messageCode, null, locale);
	return message;
	}

	public String getMessage(String messageCode) {
		String message = getMessage(messageCode, null);
		return message;
	}

}
