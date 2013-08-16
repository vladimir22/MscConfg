package com.mscconfig.commands;

import com.mscconfig.services.SshCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * User: Vladimir
 * Date: 25.06.13
 * Time: 15:13
 * Запускалка комманд
 */
@Component("cmdRunner")
public class СmdRunner {

	public static final Logger log = LoggerFactory.getLogger(СmdRunner.class);

	public static NsnCmd execute(SshCommandService sshCommandService,NsnCmd nsnCmd,boolean isTest) throws IOException {


			if (isTest) sshCommandService.executeTestNsnCmd(nsnCmd);  // наполняем значениями
			else{
				sshCommandService.executeNsnCmd(nsnCmd);
			}
			log.info("+++++++++++++++++++++++++ NsnCmd +++++++++++++++++++++++++++");
			log.info(nsnCmd.toString());
			log.info("+++++++++++++++++++++++++ NsnCmd End +++++++++++++++++++++++++++");


		return nsnCmd;
	}
}
