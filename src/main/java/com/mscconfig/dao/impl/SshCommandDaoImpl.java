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
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}
		return response;  //To change body of implemented methods use File | Settings | File Templates.
	}
    /**
	 * Выполняет комманду (и все вложенные тоже).Наполняем значениями весть стэк комманд
	 * @param nsnCmd
	 */
	@Override
	public void executeNsnCmd(NsnCmd nsnCmd) throws IOException {
		try {
			SshManager sshManager = getSshManager();
			sshManager.openConnection();
			NsnCmd cmd = nsnCmd.getStartCmd(); // берем начальную комманду
			while(cmd!=null){    // выполняем все вложенные комманды (снизу вверх)
				String response = sshManager.executeCmd(cmd.getCompletedCmd());

				for(Map.Entry<String, Param> val:cmd.getValues().entrySet()){   // заносим значения в карту NsnCmd объекта
					val.getValue().fillData(response);
					log.info(val.toString());
				}
				cmd = cmd.getParentCmd();   // если отца нет выходим из цикла
			}
			sshManager.closeConnection();
		} catch (InterruptedException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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
	 * Тестовое выполнение НСН Комманды
	 * @param nsnCmd
	 * @throws NsnCmdException
	 */
	@Override
	@Deprecated
	public void executeTestNsnCmd(NsnCmd nsnCmd) throws IOException {
		NsnCmd cmd = nsnCmd.getStartCmd(); // берем начальную комманду
		while(cmd!=null){    // выполняем все вложенные комманды (снизу вверх) до объекта nsnCmd
			String completedCmd =  cmd.getCompletedCmd();
			String response = SSHClientTest.cmdMap.get(completedCmd);
			if (response==null) throw new IOException("Unknown command for SSHClientTest map :"+completedCmd);
			for(Map.Entry<String, Param> val:cmd.getValues().entrySet()){   // заносим значения в карту
				val.getValue().fillData(response);
				//log.info(val.toString());
			}
			cmd = cmd.getParentCmd();   // если отца нет выходим из цикла
		}
	}

}
