package com.mscconfig.commands;

import com.mscconfig.commands.factories.RegexPattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Vladimir
 * Date: 22.05.13
 * Time: 9:57
 * Instance stores parameter(value, regexList, name) for NsnCmd
 */
public class Param {
	public static final Logger log = LoggerFactory.getLogger(Param.class);
	private String value;
	private List<String> regexList;
	private String name;


	public Param(String regexForCatchData,List<String> regexes) {
		regexList = new ArrayList<String>();
		regexList.add(0,regexForCatchData);
		regexList.addAll(regexes);
	}

	public Param(String title,RegexPattern regexPattern) {
		regexList = regexPattern.getRegexpList(title);
	}



	private String regexpProcess(String text, String regexp, boolean replace){
		log.debug("text="+text);
		log.debug("regexp="+regexp);
		Pattern pattern = Pattern.compile(regexp,Pattern.DOTALL);	// хватаем все строки

		Matcher matcher = pattern.matcher(text);
		String value="";
		int i =0;
		if(replace){
			value = matcher.replaceAll("");
			log.debug("value="+value);
			return value;
		}
		while(matcher.find()){
			value = matcher.group();
			i++;
		}
		if (i>1)
			log.warn(new StringBuilder("Found in response='").append(text)
					.append("', regexp='").append(regexp).append("' many matches =").append(i).append(" !!!").toString());
		log.debug("value="+value);
		return value;
	}

	/**
	 * Handles cmd response by regexList and set value
	 * @param response
	 */
	public void fillData(String response){
		String value =   regexpProcess(response, regexList.get(0), false);
		 for(int i=1; i<regexList.size();i++){
			 value =   regexpProcess(value, regexList.get(i), true);
		 }
		setValue(value);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<String> getRegexList() {
		return regexList;
	}

	public void setRegexList(List<String> regexList) {
		this.regexList = regexList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "{" + value +"}";

	}
}
