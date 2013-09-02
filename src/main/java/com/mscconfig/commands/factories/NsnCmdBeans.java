package com.mscconfig.commands.factories;

import com.mscconfig.commands.NsnCmd;
import com.mscconfig.commands.Param;
import com.mscconfig.commands.exceptions.NsnCmdException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Vladimir
 * Date: 27.08.13
 * Time: 15:40
 * Please describe this stuff
 */
@Configuration
public class NsnCmdBeans {

	@Bean(name="tempcmd")
	public NsnCmd tempcmd() throws NsnCmdException {
		NsnCmd childCmd = new NsnCmd();
		childCmd.setCmd("exemmlmx -c \"ZRQI:ROU:NAME=AIMSI;\" -n \"MSS-239663\"");
		childCmd.addValue("SUBANALYSIS_NAME",new Param("SUBANALYSIS NAME:",RegexPattern.SINGLEROW));
		childCmd.addValue("STATE_OF_SUBANALYSIS",new Param("STATE OF SUBANALYSIS:",RegexPattern.SINGLEROW));
		childCmd.addValue("DEFAULT_RESULT",new Param("DEFAULT RESULT:",RegexPattern.SINGLEROW));
		childCmd.addValue("VALUE_OF_ATTRIBUTE", new Param("VALUE OF ATTRIBUTE:",RegexPattern.THREE_ROWS));
		childCmd.addValue("RESULT_OF_SUBANALYSIS", new Param("RESULT OF SUBANALYSIS:",RegexPattern.THREE_ROWS_WITH_DIGITS));

		NsnCmd nsnCmd = new NsnCmd("exemmlmx -c \"ZRQI:ROU:NAME=%RESULT_OF_SUBANALYSIS%;\" -n \"MSS-239663\"",childCmd);
		nsnCmd.addValue("SUBANALYSIS_NAME", new Param("SUBANALYSIS NAME:", RegexPattern.SINGLEROW));
		nsnCmd.addValue("VALUE_OF_ATTRIBUTE", new Param("VALUE OF ATTRIBUTE:", RegexPattern.THREE_ROWS));
		nsnCmd.addValue("RESULT_OF_SUBANALYSIS", new Param("RESULT OF SUBANALYSIS:", RegexPattern.THREE_ROWS_WITH_DIGITS));
		return nsnCmd;
	}

	@Bean(name="vsubcmd")
	public NsnCmd vsubcmd() throws NsnCmdException {
		NsnCmd nsnCmd = new NsnCmd();
		nsnCmd.setCmd("exemmlmx -c \"ZMVO:MSISDN=@MSISDN@;\" -n \"MSS-239663\"");
		List<String> paramList = new ArrayList();
		paramList.add("INTERNATIONAL MOBILE SUBSCRIBER IDENTITY");
		paramList.add("ACTIVATION STATUS");
		paramList.add("LOCATION AREA CODE OF IMSI");
		paramList.add("MOBILE NOT REACHABLE FLAG");
		paramList.add("IMSI DETACH FLAG");
		paramList.add("DETACH CAUSE");
		paramList.add("LAST ACTIVATE DATE");
		paramList.add("LAST USED CELL ID");
		paramList.add("HLR-ADDRESS");
		paramList.add("SCP ADDRESS");
		paramList.add("MOBILE SUBSCRIBER INTERNATIONAL ISDN NUMBER");

		for (String paramName : paramList){
			nsnCmd.addValue(paramName, new Param(paramName,RegexPattern.SINGLEROW));
		}
		return nsnCmd;
	}

}
