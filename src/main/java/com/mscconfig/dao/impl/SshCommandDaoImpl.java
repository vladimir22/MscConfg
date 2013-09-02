package com.mscconfig.dao.impl;

import com.mscconfig.commands.NsnCmd;
import com.mscconfig.commands.Param;
import com.mscconfig.commands.exceptions.NsnCmdException;
import com.mscconfig.dao.SshCommandDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Map;
/**
 * User: Vladimir
 * Date: 23.05.13
 * Time: 10:19
 * Реализация ДАО - выполнение комманд
 */
@Configuration
@PropertySource("classpath:/ssh.properties")
@Repository
public class SshCommandDaoImpl implements SshCommandDao {
	public static final Logger log = LoggerFactory.getLogger(SshCommandDaoImpl.class);

	String response;
	@Autowired //срабатывает только после создания объекта!Не работает в конструкторе и с static fields(null)
	Environment env;

	public SshCommandDaoImpl() {
	}

	private  SshManager getSshManager(){
		String host = env.getProperty("mss.ipadress");
		Integer port = Integer.valueOf(env.getProperty("ssh.port"));
		String userName = env.getProperty("netact.login");
		String password = env.getProperty("netact.pwd");
		return SshManager.getInstance(host, port, userName, password);
	}

	/**
	 * Выполнение строки-команды ssh
	 * @param cmd
	 * @return
	 */
	@Override
	public String executeCmd(String cmd) throws IOException {
		try {
			SshManager sshManager = getSshManager();
			sshManager.openConnection();
			response = sshManager.executeCmd(cmd);
			sshManager.closeConnection();
		}  catch (InterruptedException e) {
			log.error("Command execution failed :",e);
		}
		return response;
	}
    /**
	 * Executes all required ordered command list, stores values at NsnCmd's
	 * @param nsnCmd
	 */
	@Override
	public void executeNsnCmd(NsnCmd nsnCmd) throws IOException {
		try {
			SshManager sshManager = getSshManager();
			sshManager.openConnection();
			List<NsnCmd> cmdList = nsnCmd.getOrderedCmdList();
			for(NsnCmd cmd : cmdList){
				String response = sshManager.executeCmd(cmd.getCompletedCmd());
				storeCommandValues(cmd, response);
				if(log.isInfoEnabled()) log.info("NsnCmd executed:"+ cmd.getCompletedCmd());
			}
			sshManager.closeConnection();
		} catch (InterruptedException e) {
			log.error("NsnCmd execution failed :",e);
		}
	}
	private void storeCommandValues(NsnCmd cmd, String response){
		for(Map.Entry<String, Param> val:cmd.getValues().entrySet()){
			cmd.setFullText(response);
			val.getValue().fillData(response);
		}
	}
	/**
	 * Тестовое(оффлайн) выполнение ssh комманды
	 * @param cmd
	 * @return
	 */
	@Override
	@Deprecated
	public String executeTestCmd(String cmd) {
		return SSHClientTest.cmdMap.get(cmd);
	}
	/**
	 * Test Executing NsnCmd
	 * @param nsnCmd
	 * @throws NsnCmdException
	 */
	@Override
	@Deprecated
	public void executeTestNsnCmd(NsnCmd nsnCmd) throws IOException {
		List<NsnCmd> cmdList = nsnCmd.getOrderedCmdList();
		for(NsnCmd cmd : cmdList){
			String response = SSHClientTest.cmdMap.get(cmd.getCompletedCmd());
			storeCommandValues(cmd, response);
			if(log.isInfoEnabled()) log.info("TestNsnCmd executed:"+ cmd.getCompletedCmd());
		}
	}

}
