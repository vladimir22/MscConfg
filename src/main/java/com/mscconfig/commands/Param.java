package com.mscconfig.commands;

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
 * Объект хранит имя, регекс паттерн, значение
 */
public class Param {
	public static final Logger log = LoggerFactory.getLogger(Param.class);
	private String value; 		// значение
	private List<String> regexList;


	public Param(String... regexes) {
		regexList = new ArrayList<String>();
		for(int i = 0; i < regexes.length; i++){
			regexList.add(regexes[i]);
		}
	}

	private String regexpProcess(String text, String regexp, boolean replace){
		Pattern pattern = Pattern.compile(regexp,Pattern.DOTALL);	// хватаем все строки
		Matcher matcher = pattern.matcher(text);
		String value="";
		int i =0;
		if(replace) return matcher.replaceAll("");

		while(matcher.find()){
			value = matcher.group();
			i++;
		}
		if (i>1)
			log.warn(new StringBuilder("Found in response='").append(text)
					.append("', regexp='").append(regexp).append("' many matches =").append(i).append(" !!!").toString());

		return value;
	}

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

	@Override
	public String toString() {
		return "{" + value +"}";

	}
}
