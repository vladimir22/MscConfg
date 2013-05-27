package com.mscconfig.dao.impl;

/**
 * User: Vladimir
 * Date: 23.05.13
 * Time: 10:25
 * Please describe this stuff
 */
import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import com.sshtools.j2ssh.configuration.SshConnectionProperties;
import com.sshtools.j2ssh.connection.ChannelState;
import com.sshtools.j2ssh.io.IOStreamConnector;
import com.sshtools.j2ssh.session.SessionChannelClient;
import com.sshtools.j2ssh.transport.IgnoreHostKeyVerification;
import com.sshtools.j2ssh.util.InvalidStateException;
import java.io.*;
import java.util.TreeMap;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.Jdk14Logger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author vv_ilc
 */
public class SSHClient {
	public static final Logger log = LoggerFactory.getLogger(SSHClient.class);
	private String username;
	private String password;
	private String host;
	private int port;
	private SshClient sshClient = null;

	private SessionChannelClient session;
	private InputStream outCmdStream;
	private InputStreamReader outCmdReader;
	private BufferedReader outCmdBufReader;



	public SSHClient(String host, int port, String userName, String password) {
		this.host = host;
		this.port = port;
		this.username = userName;
		this.password = password;
		sshClient = new SshClient();
	}

	private final int authenticate() throws IOException {
		sshClient.connect(host, new IgnoreHostKeyVerification());
		PasswordAuthenticationClient pwd = new PasswordAuthenticationClient();
		pwd.setUsername(username);
		pwd.setPassword(password);
		return sshClient.authenticate(pwd);
	}

	public void openSession() throws IOException {
		int result = authenticate();
		if (result == AuthenticationProtocolState.FAILED)
			throw new IOException("The authentication failed");
	   	if (result == AuthenticationProtocolState.PARTIAL)
			   throw new IOException("The authentication succeeded but another"
					   + "authentication is required");

		log.info("Authentification success! Start opening Session...");


	}

	/**
	 * Выполняем комманду через синхронизированный метод
	 * @param cmd
	 * @return
	 * @throws IOException
	 */
	public String executeCmd(String cmd) throws IOException {
		if ((cmd == null)||(cmd.isEmpty())) return null;
		//if (session==null) throw new IOException("Session is not open (null)!!!");

		session = sshClient.openSessionChannel();
		outCmdStream = session.getInputStream();
		outCmdReader = new InputStreamReader(outCmdStream);
		outCmdBufReader = new BufferedReader(outCmdReader);

		boolean isExecute = session.executeCommand(cmd);
		if (log.isInfoEnabled()) log.info("-------------session.executeCommand(cmd) = "+isExecute+"-----------------");


		char[] cBuf = new char[1024];
		int bufRead;
		StringBuilder sb = new StringBuilder();
		while ((bufRead = outCmdBufReader.read(cBuf)) != -1) {
			sb.append(cBuf);
		}
		String response = sb.toString();
		if (log.isInfoEnabled()){
			log.info("===================================SSH Cmd Start===================================");
			log.info(response);
			log.info("===================================SSH Cmd End====================================");
		}

		outCmdBufReader.close();
		outCmdReader.close();
		outCmdStream.close();

		try {
			session.getState().waitForState(ChannelState.CHANNEL_CLOSED);
		} catch (InterruptedException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}

		session.close();

		return  response;

	}

	public void closeSession() throws IOException, InterruptedException {


		sshClient.disconnect();

	}


	public StringBuilder execute(String cmd) throws IOException, InterruptedException {




		StringBuilder sb = new StringBuilder();



			if (cmd != null) {
				System.out.println("-------------session.executeCommand(cmd) is "+session.executeCommand(cmd)+"-----------------");
			}
			String outLine="";

		char[] cBuf = new char[1024];
		int bufRead;
		while ((bufRead = outCmdBufReader.read(cBuf)) != -1) {
			sb.append(cBuf);
		}

			/*while ((outLine = outCmdBufReader.readLine()) != null) {
				//sb.append(outLine).append('\n');
				System.out.println(outLine);
			}*/
			outCmdBufReader.close();
			outCmdReader.close();
			outCmdStream.close();
			session.close();
			session.getState().waitForState(ChannelState.CHANNEL_CLOSED);
			sshClient.disconnect();

		return  sb;
	}

	public TreeMap<String, String> execute(String cmd, TreeMap<String, String> searchParams, String excludeStr)
			throws IOException, InvalidStateException, InterruptedException {


		Jdk14Logger mylog2 = (Jdk14Logger) LogFactory.getLog("com.sshtools"); // вырубаем INFO:
		mylog2.getLogger().setLevel(java.util.logging.Level.SEVERE);




		int result = authenticate();

		if (result == AuthenticationProtocolState.FAILED) {
			System.out.println("The authentication failed");
			return null;
		}

		if (result == AuthenticationProtocolState.PARTIAL) {
			System.out.println("The authentication succeeded but another"
					+ "authentication is required");
			return null;
		}

//        if (result == AuthenticationProtocolState.COMPLETE) {
//            System.out.println("The authentication is complete");
//        }

		SessionChannelClient session = sshClient.openSessionChannel();

		/**
		 * Writing to the session OutputStream
		 */
		InputStream outCmdStream = session.getInputStream();
		InputStreamReader outCmdReader = new InputStreamReader(outCmdStream);
		BufferedReader outCmdBufReader = new BufferedReader(outCmdReader);



		byte buffer[] = new byte[255];
		int read;

		if (cmd != null) {
			System.out.println("-------------session.executeCommand(cmd) is "+session.executeCommand(cmd)+"-----------------");
		}

		String outLine;
		while ((outLine = outCmdBufReader.readLine()) != null) {
			if ((excludeStr != null) && (excludeStr.length() > 5)) { // если нужно игнорировать строку
				if (outLine.contains(excludeStr)) {
					continue;
				}
			}
			for (String searchParam : searchParams.keySet()) {
				if (outLine.contains(searchParam)) {
//System.out.println(searchParam+", "+searchParam.length()+", "+outLine+", "+outLine.substring(searchParam.length()));
					searchParams.put(searchParam, outLine.trim().substring(searchParam.length()));
				}
			}

		}




		session.close();
		session.getState().waitForState(ChannelState.CHANNEL_CLOSED);
		sshClient.disconnect();


		return searchParams;

	}
}
