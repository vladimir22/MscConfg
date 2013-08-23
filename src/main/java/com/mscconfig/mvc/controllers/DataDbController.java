package com.mscconfig.mvc.controllers;

import com.mscconfig.entities.MgwData;
import com.mscconfig.mvc.components.MessageSourceBean;
import com.mscconfig.repository.MgwDataRepository;
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

/**
 * User: Vladimir
 * Date: 23.08.13
 * Time: 10:20
 * Controller handles interaction with DB data
 */
@Controller
public class DataDbController extends AbstractController {

	private static final Logger log = LoggerFactory.getLogger(DataDbController.class);

	protected static final String REQ_DATA_PAGE = "/dataPage";
	protected static final String VIEW_DATA_PAGE = "datadb";
	protected static final String MODEL_ATTR_MGWDATA ="mgwData";
	protected static final String MODEL_ATTR_MGWDATAS ="mgwDatas";
	protected static final String REQ_GET_JSON_MGWDATAS ="/api/mgwdata";
	protected static final String REQ_ADD_MGWDATA ="/addMgwData";
	protected static final String REQ_DEL_MGWDATA ="/deleteMgwData/{mgw_id}";
	@Autowired
	MessageSourceBean messageSourceBean;

	@Autowired
	private MgwDataRepository mgwRepository;

	@RequestMapping(value = REQ_DATA_PAGE, method = RequestMethod.GET)
	public String getDataPage(ModelMap model) {
		model.addAttribute(MODEL_ATTR_MGWDATA, new MgwData()); // fill model data for jsp
		model.addAttribute(MODEL_ATTR_MGWDATAS, mgwRepository.findAll());
		return VIEW_DATA_PAGE;
	}

	@RequestMapping(value = REQ_GET_JSON_MGWDATAS, method = RequestMethod.GET)
	public @ResponseBody String getJsonMgwDatas(ModelMap model) throws JSONException {
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

	@RequestMapping(value = REQ_ADD_MGWDATA, method = RequestMethod.POST)
	public String addMgwData(@ModelAttribute("mgwData") MgwData mgwData, BindingResult result) {
		mgwRepository.save(mgwData);
		return getRedirectedPath(REQ_DATA_PAGE);
	}

	@RequestMapping(REQ_DEL_MGWDATA)
	public String deleteMgwData(@PathVariable("mgw_id") Long mgwId) {
		mgwRepository.delete(mgwRepository.findOne(mgwId));
		return getRedirectedPath(REQ_DATA_PAGE);
	}

	@RequestMapping(value = "/test/messages", method = RequestMethod.GET)
	public String getTestMessages(ModelMap model) {
		log.debug("Get message :" + messageSourceBean.getMessage("label.mainPage"));
		log.debug("Get message :"+messageSourceBean.getMessage("label.mainPage",new Object[111]));
		return getRedirectedPath(REQ_DATA_PAGE);
	}

}
