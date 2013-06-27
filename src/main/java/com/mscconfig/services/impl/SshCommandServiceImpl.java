package com.mscconfig.services.impl;

import com.mscconfig.commands.NsnCmd;
import com.mscconfig.dao.SshCommandDao;
import com.mscconfig.services.SshCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * User: Vladimir
 * Date: 23.05.13
 * Time: 10:55
 * Сервис для работы с SSH коммандами
 */
@Service("sshCommandService")
public class SshCommandServiceImpl implements  SshCommandService {
	@Autowired
	private SshCommandDao sshCommandDao;

	@Override
	public String executeCmd(String cmd) throws IOException {
		return sshCommandDao.executeCmd(cmd);
	}

	@Override
	public String executeTestCmd(String cmd) {
		return sshCommandDao.executeTestCmd(cmd);
	}

	@Override
	public void executeTestNsnCmd(NsnCmd nsnCmd) throws IOException {
		sshCommandDao.executeTestNsnCmd(nsnCmd);
	}

	@Override
	public void executeNsnCmd(NsnCmd nsnCmd) throws IOException {
		sshCommandDao.executeNsnCmd(nsnCmd);
	}
}
