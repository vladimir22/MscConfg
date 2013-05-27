package com.mscconfig.commands;

import com.mscconfig.commands.exceptions.NsnCmdException;
import com.mscconfig.services.SshCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import org.springframework.stereotype.Controller;

/**
 * User: Vladimir
 * Date: 22.05.13
 * Time: 10:09
 * Тест класс (что бы не захламлять контроллер)
 */
@Controller
public class Testing {
	public static final Logger log = LoggerFactory.getLogger(Testing.class);

	public Testing() {
	}
	public NsnCmd getTestCommand() throws NsnCmdException {
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

	public void executeSsh(SshCommandService sshCommandService,boolean isTest){
		try {
		NsnCmd nsnCmd = getTestCommand();

			if (isTest) sshCommandService.executeTestNsnCmd(nsnCmd);  // наполняем значениями
			else{
				sshCommandService.executeNsnCmd(nsnCmd);
            }
			log.info("+++++++++++++++++++++++++ NsnCmd +++++++++++++++++++++++++++");
			log.info(nsnCmd.toString());
			log.info("+++++++++++++++++++++++++ NsnCmd End +++++++++++++++++++++++++++");

		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
	}


}
