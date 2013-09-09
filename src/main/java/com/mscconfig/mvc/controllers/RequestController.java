package com.mscconfig.mvc.controllers;

import com.mscconfig.mvc.components.anet.HttpUtils;
import com.mscconfig.mvc.components.anet.RequestEnity;
import com.mscconfig.mvc.components.anet.RequestKind;
import net.authorize.ResponseField;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Vladimir
 * Date: 02.09.13
 * Time: 12:55
 * Controller handle requests for ANEt & UCHARGE
 */
@Controller
public class RequestController {

	private static final Logger log = LoggerFactory.getLogger(RequestController.class);

	protected static final String REQ_DATA_FOR_PAYPAGE = "/paypageData";
	protected static final String VIEW_ANET_PAGE = "/anetview";

	String apiLoginId = "7C2r3bW235";
	String transactionKey = "7C4CcR7m464876ht";
	String relayResponseUrl = "http://localhost:8080/relay_response.jsp";

	/*-------------------Custom Requests--------------------------------*/
	@RequestMapping(value = "/checkout_form", method = RequestMethod.GET)
	public String getCheckoutForm(){
		log.debug("Executed checkout_form");
	return "anet/checkout_form";
	}
	@RequestMapping(value = "/checkout_form_sim", method = RequestMethod.GET)
	public String getCheckoutFormSim(){
		log.debug("Executed checkout_form_sim");
		return "anet/checkout_form_sim";
	}
	@RequestMapping(value = "/relay_response", method = RequestMethod.GET)
	public String getRelayResponseForm(){
		log.debug("Executed  relay_response");
		return "anet/relay_response";
	}
	@RequestMapping(value = "/order_receipt", method = RequestMethod.GET)
	public String getReceiptPageForm(){
		log.debug("Executed order_receipt");
		return "anet/order_receipt";
	}


	/* In Future will send begin parameters for payrequest.jsp*/
	@RequestMapping(value = REQ_DATA_FOR_PAYPAGE, method = RequestMethod.GET)
	public @ResponseBody String getJsonPayPageData(ModelMap model) throws JSONException {
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("apiUrl","");
		jsonArray.put(jsonObject);

		return jsonArray.toString();
		}




	/*-------------------Special Requests--------------------------------*/
	@RequestMapping(value = "/payrequest", method = RequestMethod.GET)
	public String postAnetReq(ModelMap model) {
		log.debug("Executed paypage");

		String apiUrl = "https://test.authorize.net/gateway/transact.dll";
		String apiLoginId = "7C2r3bW235";
		String transactionKey = "7C4CcR7m464876ht";
		String relayResponseUrl = "http://localhost:8080/relay_response_postanet";

		String cardNum = "4111111111111111";
		String expDate = "1213";
		String amount = "100";

		model.addAttribute("apiUrl",apiUrl );
		model.addAttribute("apiLoginId", apiLoginId);
		model.addAttribute("transactionKey",transactionKey);
		model.addAttribute("relayResponseUrl",relayResponseUrl );

		model.addAttribute("amount",amount );
		model.addAttribute("expDate",expDate );
		model.addAttribute("cardNum",cardNum );

		RequestKind[] requestKinds = RequestKind.class.getEnumConstants();
		RequestKind selectedRequestKind = requestKinds[0];

		model.addAttribute("requestKinds",requestKinds );
		model.addAttribute("selectedRequestKind",selectedRequestKind );

		/*List<String> requestKindNames = new ArrayList<String>();
		 for(RequestKind en: requestKinds){
			 requestKindNames.add(en.toString());
		 }
		model.addAttribute("requestKindNames",requestKindNames );*/




		return "anet/custom/payrequest";
	}
	@RequestMapping(value = "/relay_response_postanet")
	public String relayResponseAnet(){
		log.debug("relay_response_postanet");
		return /*"anet/custom/postanet"*/ null;
	}
	@RequestMapping(value = "/relay_response_postanet", method = RequestMethod.GET)
	public String relayResponseGetAnet(){
		log.debug("relay_response_postanet");
		return /*"anet/custom/postanet"*/ null;
	}

	@RequestMapping(value = "/relay_response_postanet", method = RequestMethod.POST)
	public String relayResponsePostAnet(){
		log.debug("relay_response_postanet");
		return /*"anet/custom/postanet"*/ null;
	}


	@RequestMapping(value = "/postanetAjax", method = RequestMethod.GET)
	public @ResponseBody
	void postAnetAjaxReq(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute("apiUrl") String apiUrl, @ModelAttribute("apiLoginId") String apiLoginId ,
			@ModelAttribute("transactionKey") String transactionKey , @ModelAttribute("relayResponseUrl") String relayResponseUrl,
			@ModelAttribute("amount") String amount , @ModelAttribute("expDate") String expDate ,
			@ModelAttribute("cardNum") String cardNum, @ModelAttribute("requestKindName") String requestKindName,
			@ModelAttribute("name1") String name1, @ModelAttribute("value1") String value1,
			@ModelAttribute("name2") String name2, @ModelAttribute("value2") String value2,
			@ModelAttribute("name3") String name3, @ModelAttribute("value3") String value3 	)  throws IOException {

		log.debug("Executed postanet");

		Map<String,String> customParams = new HashMap<>();
		customParams.put(name1,value1);
		customParams.put(name2,value2);
		customParams.put(name3,value3);
		customParams.remove("");
		RequestKind selectedRequestKind = null;
		RequestKind[] requestKinds = RequestKind.class.getEnumConstants();
		for(RequestKind reqEntity : requestKinds){
			if(reqEntity.getKindName().equals(requestKindName))
				selectedRequestKind = reqEntity;
		}
		RequestEnity reqEntity = new RequestEnity(apiLoginId,transactionKey,relayResponseUrl,
													amount,expDate,cardNum,
														apiUrl,selectedRequestKind);

		Map<String,String> params = reqEntity.getRequestKind().getParams(apiLoginId, transactionKey, cardNum, expDate, amount, relayResponseUrl);
		params.putAll(customParams);
		reqEntity.setParams(params);

		PostMethod postMethod = HttpUtils.preparePostMethod(reqEntity);
		StringBuilder answer= new StringBuilder(HttpUtils.sendPostMethod(postMethod));
		HttpUtils.makeReplacing(answer,reqEntity);

		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter printWriter=  response.getWriter();

		printWriter.write("<div id='httpTrace' style='width: 100%;'>");
		printWriter.write("<div id='httpRequest' style='width: 40%; float: left; display: inline-block;' >");
		printWriter.write("<h1 style='padding:0 0 0 30px'> Request:</h1>");
		printWriter.write("<br>");
		printWriter.write("<p id='uri' style='color:green'> Path:</p>");
		printWriter.write("<br>");

		printWriter.write("<p>");
		printWriter.write(postMethod.getURI().toString());
		printWriter.write("</p>");

		printWriter.write("<p id='params' style='color:green'> Parameters:</p>");

		printWriter.write("<p>");
		NameValuePair[] nameValuePairs = postMethod.getParameters();
		for (NameValuePair pair: nameValuePairs){
			printWriter.write(pair.getName().replace(" ","&nbsp")+ " : " + pair.getValue().replace(" ","&nbsp"));
			printWriter.write("<br>");
		}
		printWriter.write("</p>");
		printWriter.write("</div>");

		printWriter.write("<div id='httpResponse' style='width: 40%; display: inline-block;'>");
		printWriter.write("<h1 style='padding:0 0 0 30px'> Response:</h1>");
		printWriter.write("<br>");
		printWriter.write(answer.toString());
		printWriter.write("</div>");
		printWriter.write("</div>");

	}



	private void parseResponse(){
		String apiLoginId = "7C2r3bW235";
		String receiptPageUrl = "http://localhost:8080/order_receipt/";
        /*
        * Leave the MD5HashKey as is - empty string, unless you have explicitly
        * set it in the merchant interface:
        * Account > Settings > Security Settings > MD5-Hash
        */
		String MD5HashKey = "MyHash";
		net.authorize.sim.Result result =
				net.authorize.sim.Result.createResult(apiLoginId, MD5HashKey,
						/*request.getParameterMap()*/null);
		// perform Java server side processing...
		// ...
		// build receipt url buffer
		StringBuffer receiptUrlBuffer = new StringBuffer(receiptPageUrl);
		if(result != null) {
			receiptUrlBuffer.append("?");
			receiptUrlBuffer.append(
					ResponseField.RESPONSE_CODE.getFieldName()).append("=");
			receiptUrlBuffer.append(result.getResponseCode().getCode());
			receiptUrlBuffer.append("&");
			receiptUrlBuffer.append(
					ResponseField.RESPONSE_REASON_CODE.getFieldName()).append("=");
			receiptUrlBuffer.append(
					result.getReasonResponseCode().getResponseReasonCode());
			receiptUrlBuffer.append("&");
			receiptUrlBuffer.append(
					ResponseField.RESPONSE_REASON_TEXT.getFieldName()).append("=");
			receiptUrlBuffer.append(
					result.getResponseMap().get(
							ResponseField.RESPONSE_REASON_TEXT.getFieldName()));
			if(result.isApproved()) {
				receiptUrlBuffer.append("&").append(
						ResponseField.TRANSACTION_ID.getFieldName()).append("=");
				receiptUrlBuffer.append(result.getResponseMap().get(
						ResponseField.TRANSACTION_ID.getFieldName()));
			}
		}
	}
}

