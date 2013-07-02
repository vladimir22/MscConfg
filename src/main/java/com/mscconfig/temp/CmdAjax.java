package com.mscconfig.temp;

import java.io.Serializable;

/**
 * User: Vladimir
 * Date: 27.06.13
 * Time: 13:38
 * Please describe this stuff
 */
public class CmdAjax implements Serializable {

	private String number ;
	private String cmdName;
	private Boolean cmdTest;
	private String cmdResponse;
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getCmdName() {
		return cmdName;
	}

	public void setCmdName(String cmdName) {
		this.cmdName = cmdName;
	}

	public Boolean getCmdTest() {
		return cmdTest;
	}

	public void setCmdTest(Boolean cmdTest) {
		this.cmdTest = cmdTest;
	}

	public String getCmdResponse() {
		return cmdResponse;
	}

	public void setCmdResponse(String cmdResponse) {
		this.cmdResponse = cmdResponse;
	}

	@Override
	public String toString() {
		return "CmdAjax{" +
				"number='" + number + '\'' +
				", cmdName='" + cmdName + '\'' +
				", cmdTest=" + cmdTest +
				", cmdResponse=" + cmdResponse +
				'}';
	}
}

