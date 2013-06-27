package com.mscconfig.temp;

import java.util.List;

/**
 * User: Vladimir
 * Date: 27.06.13
 * Time: 9:54
 * For testing ajax - send object list
 */
public class AjaxObj {

   private String name ;
	private List<String> nameList;
	private List<String> educationList;

	public AjaxObj() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getNameList() {
		return nameList;
	}

	public void setNameList(List<String> nameList) {
		this.nameList = nameList;
	}

	public List<String> getEducationList() {
		return educationList;
	}

	public void setEducationList(List<String> educationList) {
		this.educationList = educationList;
	}

	@Override
	public String toString() {
		return "AjaxObj{" +
				", nameList=" + nameList +
				", educationList=" + educationList +
				'}';
	}
}
