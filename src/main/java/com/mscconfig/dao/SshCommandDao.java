package com.mscconfig.dao;

import com.mscconfig.commands.NsnCmd;
import com.mscconfig.commands.exceptions.NsnCmdException;

import java.io.IOException;

/**
 * User: Vladimir
 * Date: 23.05.13
 * Time: 10:17
 * Please describe this stuff
 */
public interface SshCommandDao {
	String executeCmd(String cmd) throws IOException;
	String executeTestCmd(String cmd);
	void executeTestNsnCmd(NsnCmd nsnCmd) throws IOException;
	void executeNsnCmd(NsnCmd nsnCmd) throws IOException;
}
