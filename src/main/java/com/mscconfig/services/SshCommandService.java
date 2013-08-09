package com.mscconfig.services;

import com.mscconfig.commands.NsnCmd;

import java.io.IOException;

/**
 * User: Vladimir
 * Date: 23.05.13
 * Time: 10:53
 * Интерфейс к ссш сервису (нужен т.к. @Autowired вяжет через интерфейсы !)
 */
public interface SshCommandService {
	String SERVICE_NAME = "sshCommandService";

	String executeCmd(String cmd) throws IOException;
	String executeTestCmd(String cmd);
	void executeTestNsnCmd(NsnCmd nsnCmd) throws IOException;
	void executeNsnCmd(NsnCmd nsnCmd) throws IOException;
}
