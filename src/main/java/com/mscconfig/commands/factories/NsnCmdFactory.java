package com.mscconfig.commands.factories;

import com.mscconfig.commands.NsnCmd;
import com.mscconfig.commands.Param;
import com.mscconfig.commands.exceptions.NsnCmdException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

/**
 * User: Vladimir
 * Date: 22.05.13
 * Time: 10:09
 * Класс-фабрика. Собирает нужную команду.
 */
@Component("nsncmdFactory")
public class NsnCmdFactory {


	public static final Logger log = LoggerFactory.getLogger(NsnCmdFactory.class);

	public NsnCmdFactory() {
	}

	public NsnCmd getNsnCmdBean(String beanName) throws NsnCmdException {
		ApplicationContext context = new AnnotationConfigApplicationContext(NsnCmdBeans.class);
		NsnCmd nsnCmd = (NsnCmd) context.getBean(beanName);
		if(nsnCmd==null) throw new NsnCmdException("Could not find NsnCmd in NsnCmdBeans.class ");
	   return nsnCmd;
	}
/*	public NsnCmd createTestCmd() throws NsnCmdException {
		NsnCmd childCmd = new NsnCmd();
		childCmd.setCmd("exemmlmx -c \"ZRQI:ROU:NAME=AIMSI;\" -n \"MSS-239663\"");
		                                                 //берем 1 строку               удаляем "имя:      "     удаляем "  \n"
		childCmd.addValue("SUBANALYSIS_NAME", new Param("SUBANALYSIS NAME(.+?\n){1}", "SUBANALYSIS NAME:\\s+", "(\\s*?)\n"));
		childCmd.addValue("STATE_OF_SUBANALYSIS", new Param("STATE OF SUBANALYSIS(.+?\n){1}", "STATE OF SUBANALYSIS:\\s+", "(\\s*?)\n"));
		childCmd.addValue("DEFAULT_RESULT", new Param("DEFAULT RESULT(.+?\n){1}", "DEFAULT RESULT:\\s+", "(\\s*?)\n"));
		                                                  // берем 3 строки                удаляем из них 2                 удаляем "пробел все \n"
		childCmd.addValue("VALUE_OF_ATTRIBUTE", new Param("VALUE OF ATTRIBUTE:(.+?\n){3}", "VALUE OF ATTRIBUTE:(.+?\n){2}", "(\\s+)( .*)\n"));
																// берем 3 строки                удаляем из них 2                 удал. "цифры пробелы"  удаляем "пробелы:пробелы все \n"
		childCmd.addValue("RESULT_OF_SUBANALYSIS", new Param("RESULT OF SUBANALYSIS(.+?\n){3}","RESULT OF SUBANALYSIS(.+?\n){2}","^(\\d+)(\\s*)","(\\s*):(\\s*)(.*)\n"));

		NsnCmd parentCmd = new NsnCmd("exemmlmx -c \"ZRQI:ROU:NAME=%RESULT_OF_SUBANALYSIS%;\" -n \"MSS-239663\"",childCmd);

		parentCmd.addValue("SUBANALYSIS_NAME", new Param("SUBANALYSIS NAME(.+?\n){1}", "SUBANALYSIS NAME:\\s+", "(\\s*?)\n"));
		parentCmd.addValue("DEFAULT_RESULT", new Param("DEFAULT RESULT(.+?\n){1}", "DEFAULT RESULT:\\s+", "(\\s*?)\n"));
		parentCmd.addValue("VALUE_OF_ATTRIBUTE",  new Param("VALUE OF ATTRIBUTE:(.+?\n){3}", "VALUE OF ATTRIBUTE:(.+?\n){2}", "(\\s*)( .*)\n"));
		parentCmd.addValue("RESULT_OF_SUBANALYSIS", new Param("RESULT OF SUBANALYSIS(.+?\n){3}","RESULT OF SUBANALYSIS(.+?\n){2}","^(\\d+)(\\s*)","(\\s*):(\\s*)(.*)\n"));

		return parentCmd;
	}

	public NsnCmd createDispVsubCmd(String msisdn) throws NsnCmdException {
		NsnCmd nsnCmd = new NsnCmd();
		nsnCmd.setCmd("exemmlmx -c \"ZMVO:MSISDN="+msisdn+";\" -n \"MSS-239663\"");
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

		for (String val : paramList){
			nsnCmd.addValue(val, new Param(val + "(.+?\n){1}", "(\\s*)" + val + "(\\W+)", "(\\s*?)\n"));
		}
		return nsnCmd;
	}*/


}
