package com.mscconfig.mvc;

import com.mscconfig.commands.CmdFactory;
import com.mscconfig.commands.NsnCmd;
import com.mscconfig.commands.exceptions.NsnCmdException;
import com.mscconfig.commands.СmdRunner;
import com.mscconfig.entities.MgwData;
import com.mscconfig.services.SshCommandService;
import com.mscconfig.temp.AjaxObj;
import com.mscconfig.temp.Wrapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StopWatch;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;


@Controller
/*@SessionAttributes*/
public class MainController {
	public static final Logger log = LoggerFactory.getLogger(MainController.class);
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String listUsers(ModelMap model) {
        model.addAttribute("user", new User());
		AjaxObj ajaxObj = new AjaxObj();
		ajaxObj.setName("INIT_NAME");
		model.addAttribute("ajaxObj", ajaxObj);
        model.addAttribute("users", userRepository.findAll());
		// добавляем в в карту-модель объект для работы с jsp страницей
		model.addAttribute("mgwData", new MgwData());
		model.addAttribute("mgwDatas", mgwRepository.findAll());
        return "users";
    }

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public
    @ResponseBody
    String listUsersJson(ModelMap model) throws JSONException {
        JSONArray userArray = new JSONArray();
        for (User user : userRepository.findAll()) {
            JSONObject userJSON = new JSONObject();
            userJSON.put("id", user.getId());
            userJSON.put("firstName", user.getFirstName());
            userJSON.put("lastName", user.getLastName());
            userJSON.put("email", user.getEmail());
            userArray.put(userJSON);
        }
        return userArray.toString();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
	  public String addUser(@ModelAttribute("user") User user, BindingResult result) {

		userRepository.save(user);

		return "redirect:/";
	}

    @RequestMapping("/delete/{userId}")
    public String deleteUser(@PathVariable("userId") Long userId) {

        userRepository.delete(userRepository.findOne(userId));

        return "redirect:/";
    }

	/*--- MgwData --*/
	@Autowired
	private MgwDataRepository mgwRepository;
	@RequestMapping(value = "/addMgwData", method = RequestMethod.POST)
	public String addUser(@ModelAttribute("mgwData") MgwData mgwData, BindingResult result) {
		log.info("INFO MgwData");
		mgwRepository.save(mgwData);
		log.warn("WARN MgwData");
		return "redirect:/";
	}

	@RequestMapping("/deleteMgwData/{mgw_id}")
	public String deleteMgwData(@PathVariable("mgw_id") Long mgwId) {

		mgwRepository.delete(mgwRepository.findOne(mgwId));

		return "redirect:/";
	}

	@Autowired                          //никак не могу вытащить @Autowired из другого класса (@Component,@Controller)
	SshCommandService sshCommandService ; // тут работатет, поэтому вытягиваю и передаю ссылку
	@Autowired
	CmdFactory cmdFactory;   //@Component здесь тоже вытягивается !!!
	@Autowired
	СmdRunner cmdRunner;
	/* --- Тестовая кнопка --- */
	@RequestMapping(value = "/testBtn/{isTest}", method = RequestMethod.POST)
	public String testBtn(@PathVariable("isTest") Boolean isTest) throws NsnCmdException {
		log.warn("Start testBtn!!!");
		//new CmdFactory().start();
		NsnCmd nsnCmd = cmdFactory.createTestCmd();
		СmdRunner.execute(sshCommandService,nsnCmd,isTest);

		return "redirect:/";
	}

	@RequestMapping(value = "/helloajax", method = RequestMethod.GET)  //  выдает код в response  для hrefAjax
	// @ResponseBody will automatically convert the returned value into JSON format
	// You must have Jackson in your classpath!!!
	public @ResponseBody
	void fetchFlowDowns(HttpServletResponse response,@RequestParam("cmdname") String cmdname, @RequestParam("istest") Boolean isTest) throws Exception {
		log.info("helloajax executed!!!");
		if(cmdname==null) {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html");
			response.getWriter().write("Parameter cmdname == null !!!");
			return;
		}
		if(cmdname.toLowerCase().equals("tempcmd"))
			executeCmd(response, cmdFactory.createTestCmd(),isTest);

		if(cmdname.toLowerCase().equals("vsubcmd"))
			executeCmd(response, cmdFactory.createDispVsubCmd("380503281095"),isTest);
		else{
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/html");
			response.getWriter().write("Unknown cmdname : "+cmdname);
		}

	}

	@RequestMapping(value = "/searchvsub", method = RequestMethod.GET)  //  выдает код в response  для hrefAjax
	// @ResponseBody will automatically convert the returned value into JSON format
	// You must have Jackson in your classpath!!!
	public @ResponseBody
	void searchVsub(HttpServletResponse response,@RequestParam("msisdn") String msisdn,@RequestParam("term") Boolean isTest) throws Exception {
		log.info("helloajax executed!!!");
		NsnCmd nsnCmd = cmdFactory.createDispVsubCmd(msisdn);
		executeCmd(response, nsnCmd,isTest);
	}

	private NsnCmd executeCmd(HttpServletResponse response, NsnCmd cmd, Boolean isTest) throws IOException {
		StopWatch watch = new StopWatch();
		watch.start();
		cmd = СmdRunner.execute(sshCommandService,cmd,isTest);
		watch.stop();

		String ajaxString ;
		if (cmd !=null) 	ajaxString = cmd.toString().replace("\n","<br>").replace("COMMAND:","<b>COMMAND:</b>").replace("VALUES:", "<b>VALUES:</b>");
		else ajaxString = "<b>EMPTY SSH RESPONSE !!!</b>";

		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		response.getWriter().write(ajaxString);
		response.getWriter().write("<br> <br>  <font size=\"3\" color=\"green\" face=\"Arial\"> Execution time :"+watch.getTotalTimeSeconds()+" seconds </font>");

		return cmd;
	}

	/**
	 * JS формирует объект через json передает. Метод сериализует объект Wrapper.
	 * Очень внимательно с версиями Jackson - старая версия упорно не хотела сериализовывать!!!(Хром JS трейсер супер:))
	 * @param wrapper
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/AddUser",  method = RequestMethod.POST)
	public  @ResponseBody Wrapper addUser(@RequestBody final Wrapper wrapper ) throws IOException {
		log.info(wrapper.toString());
		return  wrapper ;
	}
}