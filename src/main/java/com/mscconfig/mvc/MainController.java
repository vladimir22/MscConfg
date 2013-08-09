package com.mscconfig.mvc;

import com.mscconfig.commands.CmdFactory;
import com.mscconfig.commands.NsnCmd;
import com.mscconfig.commands.СmdRunner;
import com.mscconfig.entities.MgwData;
import com.mscconfig.repository.MgwDataRepository;

import com.mscconfig.services.SshCommandService;
import com.mscconfig.temp.CmdAjax;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StopWatch;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Configuration
@Controller
@SessionAttributes
public class MainController {
	public static final Logger log = LoggerFactory.getLogger(MainController.class);

	@Resource
	@Qualifier("loggedUserMap")
	private Map<String, String> loggedUserMap;

	@RequestMapping("/index")
	public String index() {
		return "index";
	}

	@RequestMapping("/")
	public String home() {
		return "index";
	}

	/*Spring 3.2.0.Release заработал с spring Security 3.1.4 , только через этот маппинг (3.1.2.RELEASE работало на прямую :( где-то капать надо в web.xml: Dispatcher servlet mapping)  */
	@RequestMapping("/login")
	public String login(ModelMap model) {
		return "login";
	}
	@RequestMapping("/404")
	public String access_denied() {
		return "404";
	}
	@RequestMapping("/403")
	public String no_page() {
		return "403";
	}

	/*--- MgwData --*/
	@Autowired
	private MgwDataRepository mgwRepository;

	/**
	 * Наполняем модель выдаем страницу с моделью
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/dataPage", method = RequestMethod.GET)
	public String listUsers(ModelMap model) {
		// добавляем в в карту-модель объект для работы с jsp страницей
		model.addAttribute("mgwData", new MgwData());
		model.addAttribute("mgwDatas", mgwRepository.findAll());
		return "datadb";
	}

	@RequestMapping(value = "/api/mgwdata", method = RequestMethod.GET)
	public @ResponseBody String listUsersJson(ModelMap model) throws JSONException {
		JSONArray userArray = new JSONArray();
		for (MgwData mgwData : mgwRepository.findAll()) {
			JSONObject mgwJSON = new JSONObject();
			mgwJSON.put("id", mgwData.getMgw_id());
			mgwJSON.put("name", mgwData.getName());
			mgwJSON.put("IP-address", mgwData.getIp());
			userArray.put(mgwJSON);
		}
		return userArray.toString();
	}


	@RequestMapping(value = "/addMgwData", method = RequestMethod.POST)
	public String addUser(@ModelAttribute("mgwData") MgwData mgwData, BindingResult result) {
		log.info("INFO MgwData");
		mgwRepository.save(mgwData);
		log.warn("WARN MgwData");
		return "redirect:/dataPage";
	}

	@RequestMapping("/deleteMgwData/{mgw_id}")
	public String deleteMgwData(@PathVariable("mgw_id") Long mgwId) {

		mgwRepository.delete(mgwRepository.findOne(mgwId));

		return "redirect:/dataPage";
	}

	/*--------------cmd.jsp : Страница создания и выполнения комманд ----------------*/

	@Autowired                          //никак не могу вытащить @Autowired из другого класса (@Component,@Controller)
	SshCommandService sshCommandService ; // тут работатет, поэтому вытягиваю и передаю ссылку
	@Autowired
	CmdFactory cmdFactory;   //@Component здесь тоже вытягивается !!!

	@RequestMapping(value = "/cmdPage", method = RequestMethod.GET)
	public String cmdPage(ModelMap model) {
		return "cmd";
	}
	/**
	 * Принимает Ajax-объект (комманда, параметры) от аякс ф-ии , выполняем , выдаем HTML-ответ в response
	 * @param response
	 * @param cmdAjax  - объект кот. формирует js ф-я
	 * @throws IOException
	 */
	@RequestMapping(value="/cmdRecieve",  method = RequestMethod.POST)
	public  @ResponseBody
	void execCmd(HttpServletResponse response,@RequestBody  CmdAjax cmdAjax ) throws IOException { // return to ajax func. (dataType='html')
		log.info("/cmdRecieve execute : "+cmdAjax.toString());
		NsnCmd nsnCmd = null;
		//TODO сделать по взрослому (енум или через фабрику)
		if(cmdAjax.getCmdName().toLowerCase().equals("tempcmd"))
		nsnCmd =  cmdFactory.createTestCmd();

		if(cmdAjax.getCmdName().toLowerCase().equals("vsubcmd"))
			nsnCmd =  cmdFactory.createDispVsubCmd(cmdAjax.getNumber());
	nsnCmd = executeCmd(response, nsnCmd,cmdAjax.getCmdTest());
	}

	private NsnCmd executeCmd(HttpServletResponse response, NsnCmd cmd, Boolean isTest) throws IOException {
		StopWatch watch = new StopWatch();
		watch.start();
		String ajaxString ;
		if(cmd==null) ajaxString = "<b>NO SSH RESPONSE !!!</b>";
		else{
			cmd = СmdRunner.execute(sshCommandService,cmd,isTest);
			ajaxString = cmd.toString().replace("\n","<br>").replace("COMMAND:","<b>COMMAND:</b>").replace("VALUES:", "<b>VALUES:</b>");
		}
		watch.stop();
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		response.getWriter().write(ajaxString);
		response.getWriter().write("<br> <br>  <font size=\"3\" color=\"green\" face=\"Arial\"> Execution time :"+watch.getTotalTimeSeconds()+" seconds </font>");
		return cmd;
	}



	@RequestMapping(value = "/addmgw", method = RequestMethod.POST)
	public String addContact(@ModelAttribute("contact")
								 MgwData mgwData, BindingResult result) {

		System.out.println("Name:" + mgwData.getName());

		return "redirect:mgws.html";
	}

	@RequestMapping("/mgws")
	public ModelAndView showContacts() {

		return new ModelAndView("mgw-tile", "command", new MgwData());
	}


}