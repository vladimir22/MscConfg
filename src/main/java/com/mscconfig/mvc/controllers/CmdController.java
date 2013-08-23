package com.mscconfig.mvc.controllers;

import com.mscconfig.commands.CmdFactory;
import com.mscconfig.commands.NsnCmd;
import com.mscconfig.commands.СmdRunner;

import com.mscconfig.services.SshCommandService;
import com.mscconfig.temp.CmdAjax;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * User: Vladimir
 * Date: 23.08.13
 * Time: 10:40
 * Controller handles NsN Commands
 */
@Configuration
@Controller
@SessionAttributes
public class CmdController extends AbstractController {
	private static final Logger log = LoggerFactory.getLogger(CmdController.class);

	protected static final String CMD_PAGE_REQ = "/cmdPage";
	protected static final String CMD_PAGE_VIEW = "cmd";

	protected static final String CMD_RECIEVE_AJAX_REQ = "/cmdRecieve";
	protected static final String CMD_FULLTEXT_AJAX_REQ = "/cmdPage/getFullText";
	NsnCmd nsnCmd;

	@Autowired
	SshCommandService sshCommandService ;
	@Autowired
	CmdFactory cmdFactory;

	@RequestMapping(value =CMD_PAGE_REQ, method = RequestMethod.GET)
	public String getCmdPageReq(ModelMap model) {
		return CMD_PAGE_VIEW;
	}
	/**
	 * Takes AjaxObject , then: wrap-> execute-> write HTML-answer in response
	 * @param response
	 * @param cmdAjax  - AjaxObject(command, parameters) created in java script
	 * @throws IOException
	 */
	@RequestMapping(value=CMD_RECIEVE_AJAX_REQ,  method = RequestMethod.POST)
	public  @ResponseBody void cmdRecieveAjaxReq(HttpServletResponse response,@RequestBody  CmdAjax cmdAjax ) throws IOException { // return to ajax func. (dataType='html')
		log.info("/cmdRecieve execute : "+cmdAjax.toString());
		nsnCmd = null;
		//TODO make more better (via factory or spring appcontext)
		if(cmdAjax.getCmdName().toLowerCase().equals("tempcmd"))
		nsnCmd =  cmdFactory.createTestCmd();

		if(cmdAjax.getCmdName().toLowerCase().equals("vsubcmd"))
			nsnCmd =  cmdFactory.createDispVsubCmd(cmdAjax.getNumber());
	nsnCmd = executeCmd(response, nsnCmd,cmdAjax.getCmdTest());
	}

	private NsnCmd executeCmd(HttpServletResponse response, NsnCmd cmd, Boolean isTest) throws IOException {
		StopWatch watch = new StopWatch();
		watch.start();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		String ajaxString ;
			if(cmd==null) ajaxString = "<b>Command not found!!!</b>";
			else{
				try{
				cmd = СmdRunner.execute(sshCommandService,cmd,isTest);
				ajaxString = cmd.toString().replace("\n","<br>").replace("COMMAND:","<b>COMMAND:</b>").replace("VALUES:", "<b>VALUES:</b>");
				ajaxString += "<br> <div class='full-text'> <input type='button' onclick=\"showFullText()\"  class=\"href-button yellow-color\" value =\"Show full log\"/> </div>";
				}catch (IOException e) {
					ajaxString ="<div > <span class='blink'> <p> Catch exception: </p> </span> <span class='blink'> <p>' "+e.getMessage()+"'</p> </span> </div>";
					e.printStackTrace();
				}
			}
		watch.stop();

		response.getWriter().write(ajaxString);
		response.getWriter().write("<br> <br> <span class='duration-time'> Execution time :"+watch.getTotalTimeSeconds()+" seconds </span>");
		return cmd;
	}

	/**
	 * Ruturn full command log (ssh answer)
	 * @param response
	 * @param cmdName
	 * @throws IOException
	 */
	@RequestMapping(value=CMD_FULLTEXT_AJAX_REQ,  method = RequestMethod.GET)
	public  @ResponseBody
	void cmdFulltextAjaxReq(HttpServletResponse response,@RequestBody  String cmdName ) throws IOException { // return to ajax func. (dataType='html')
		log.debug("/cmdPage/getFullText execute : "+cmdName);
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		String ajaxString;
		if(nsnCmd != null) ajaxString = nsnCmd.getFullText().toString().replace("\n","<br>");
		else ajaxString = "<b>Command not found!!!</b>";
		response.getWriter().write(ajaxString);
	}

}