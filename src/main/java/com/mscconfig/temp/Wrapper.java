package com.mscconfig.temp;

import java.io.Serializable;

/**
 * User: Vladimir
 * Date: 27.06.13
 * Time: 13:38
 * Please describe this stuff
 */
public class Wrapper  implements Serializable {

	private static final long serialVersionUID = 1L;
		private String name;
		private String name2;

	@Override
	public String toString() {
		return "Wrapper{" +
				"name='" + name + '\'' +
				", name2='" + name2 + '\'' +
				'}';
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}
}

