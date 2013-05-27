package com.mscconfig.mvc;

import com.mscconfig.commands.Testing;
import com.mscconfig.entities.MgwData;
import com.mscconfig.services.SshCommandService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
public class MainController {
	public static final Logger log = LoggerFactory.getLogger(MainController.class);
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String listUsers(ModelMap model) {
        model.addAttribute("user", new User());
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

	@Autowired
	SshCommandService sshCommandService;

	/* --- Тестовая кнопка --- */
	@RequestMapping(value = "/testBtn/{isTest}", method = RequestMethod.POST)
	public String testBtn(@PathVariable("isTest") Boolean isTest) {
		log.warn("Start testBtn!!!");
		//new Testing().start();
		new Testing().executeSsh(sshCommandService,isTest);
		//StringBuilder sb =sshCommandService.executeCmd("exemmlmx -c \"ZRQI:ROU:NAME=AIMSI;\" -n \"MSS-239663\"");
		//log.info(sb.toString());
		return "redirect:/";
	}
}