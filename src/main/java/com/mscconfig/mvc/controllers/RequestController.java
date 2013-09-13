package com.mscconfig.mvc.controllers;


import com.mscconfig.mvc.components.pays.adapters.ConvertManager;
import com.mscconfig.mvc.components.pays.generator.HttpUtils;
import com.mscconfig.mvc.components.pays.generator.RequestEnity;
import com.mscconfig.mvc.components.pays.generator.RequestKind;
import com.mscconfig.mvc.components.pays.generator.RequestParams;
import net.authorize.ResponseField;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.URIException;
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
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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

		String anetApiUrl = "https://test.authorize.net/gateway/transact.dll";
		String anetApiLoginId = "7C2r3bW235";
		String anetTransactionKey = "7C4CcR7m464876ht";
		String ucharApiUrl = "https://sandbox.unipaygateway.com/gates/httpform";
		String ucharMerchantAccountCode = "10001";
		String ucharUserName = "vv.ilc@mail.ru";
		String ucharPassword = "Siemens1";
		String relayResponseUrl = "http://localhost:8080/relay_response_postanet";
		String cardNum = "4111111111111111";
		String expDate = "1213";
		String amount = "1";

		model.addAttribute("anetApiUrl",anetApiUrl );
		model.addAttribute("anetApiLoginId", anetApiLoginId);
		model.addAttribute("anetTransactionKey",anetTransactionKey);

		model.addAttribute("ucharApiUrl",ucharApiUrl);
		model.addAttribute("ucharMerchantAccountCode",ucharMerchantAccountCode );
		model.addAttribute("ucharUserName",ucharUserName );
		model.addAttribute("ucharPassword",ucharPassword);

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
			@ModelAttribute("anetApiUrl") String anetApiUrl,
			@ModelAttribute("anetApiLoginId") String anetApiLoginId ,
			@ModelAttribute("anetTransactionKey") String anetTransactionKey,

			@ModelAttribute("ucharApiUrl") String ucharApiUrl,
			@ModelAttribute("ucharMerchantAccountCode") String ucharMerchantAccountCode,
			@ModelAttribute("ucharUserName") String ucharUserName,
			@ModelAttribute("ucharPassword") String ucharPassword,

            @ModelAttribute("relayResponseUrl") String relayResponseUrl,

			@ModelAttribute("cardNum") String cardNum,
			@ModelAttribute("expDate") String expDate,
			@ModelAttribute("amount") String amount ,

			@ModelAttribute("requestKindName") String requestKindName,

			@ModelAttribute("name1") String name1, @ModelAttribute("value1") String value1,
			@ModelAttribute("name2") String name2, @ModelAttribute("value2") String value2,
			@ModelAttribute("name3") String name3, @ModelAttribute("value3") String value3,
			@ModelAttribute("name4") String name4, @ModelAttribute("value4") String value4,
			@ModelAttribute("name5") String name5, @ModelAttribute("value5") String value5,
			@ModelAttribute("name6") String name6, @ModelAttribute("value6") String value6,
			@ModelAttribute("name7") String name7, @ModelAttribute("value7") String value7,
			@ModelAttribute("name8") String name8, @ModelAttribute("value8") String value8,
			@ModelAttribute("name9") String name9, @ModelAttribute("value9") String value9,
			@ModelAttribute("name10") String name10, @ModelAttribute("value10") String value10,
			@ModelAttribute("makeRequestConvertation") Boolean makeRequestConvertation,
			@ModelAttribute("makeResponseConvertation") Boolean makeResponseConvertation,
			@ModelAttribute("showUnformattedMessage") Boolean showUnformattedMessage

	) throws IOException, URISyntaxException {

		log.debug("Executed postanet");


		RequestKind selectedRequestKind = null;
		RequestKind[] requestKinds = RequestKind.class.getEnumConstants();
		for(RequestKind reqEntity : requestKinds){
			if(reqEntity.getKindName().equals(requestKindName))
				selectedRequestKind = reqEntity;
		}
		RequestEnity entity = new RequestEnity();
		entity.setAnetApiUrl(anetApiUrl);
		entity.setAnetApiLoginId(anetApiLoginId);
		entity.setAnetTransactionKey(anetTransactionKey);

		entity.setUcharApiUrl(ucharApiUrl);
		entity.setUcharMerchantAccountCode(ucharMerchantAccountCode);
		entity.setUcharUserName(ucharUserName);
		entity.setUcharPassword(ucharPassword);

		entity.setRelayResponseUrl(relayResponseUrl);

		entity.setCardNum(cardNum);
		entity.setExpDate(expDate);
		entity.setAmount(amount);

		entity.setRequestKind(selectedRequestKind);

		Map<String,String> params = selectedRequestKind.getParams(entity);

		Map<String,String> customParams = new HashMap<>();
		customParams.put(name1,value1);
		customParams.put(name2,value2);
		customParams.put(name3,value3);
		customParams.put(name4,value4);
		customParams.put(name5,value5);
		customParams.put(name6,value6);
		customParams.put(name7,value7);
		customParams.put(name8,value8);
		customParams.put(name9,value9);
		customParams.put(name10,value10);

		for(Entry<String, String> entry : customParams.entrySet()) {
			String customValue = entry.getValue();
			if (!customValue.isEmpty())
				if(customValue.toUpperCase().equals("DEL"))
					params.remove(entry.getKey());
				else
					params.put(entry.getKey(),entry.getValue());

		}

		entity.setParams(params);

		Map<String,String> replaceMap = selectedRequestKind.getReplaceMap(entity);

		PostMethod postMethod = HttpUtils.preparePostMethod(entity);

		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter printWriter=  response.getWriter();

		printWriter.write("<div id='httpTrace' style='width: 100%;'>");

		writeRequest(printWriter,postMethod,"original");

		StringBuilder answer= new StringBuilder();
		if (makeRequestConvertation){
			ConvertManager convertManager = new ConvertManager(anetApiUrl,anetApiLoginId,anetTransactionKey,
					ucharApiUrl,ucharMerchantAccountCode,ucharUserName,ucharPassword);
			PostMethod convertedPostMethod = convertManager.convert(postMethod);

			/*selectedRequestKind.makeConvertation(entity);
			postMethod = HttpUtils.preparePostMethod(entity);*/
			writeRequest(printWriter,convertedPostMethod,"converted");
			replaceMap = RequestParams.UCHARGE_LOGIN.getReplaceMap(entity);
			answer.append(HttpUtils.sendPostMethod(convertedPostMethod));
			if (makeResponseConvertation){
			StringBuilder convertedAnswer = convertManager.convertResponse(postMethod,convertedPostMethod,answer);
			}
		}else
			answer.append(HttpUtils.sendPostMethod(postMethod));
		if (!showUnformattedMessage){
			HttpUtils.makeReplacing(answer,replaceMap);
		}


		printWriter.write("<div id='httpResponse' style='width: 40%; display: inline-block;'>");
		printWriter.write("<h1 style='padding:0 0 0 30px'> Response:</h1>");
		printWriter.write("<br>");
		printWriter.write(answer.toString());
		printWriter.write("</div>");
		printWriter.write("</div>");
	}

	private void writeRequest(PrintWriter printWriter, PostMethod postMethod, String divName) throws URIException {
		printWriter.write("<div id='"+divName+"' style='width: 40%; float: left; display: inline-block;' >");
		printWriter.write("<h1 style='padding:0 0 0 30px'> Request("+divName+"):</h1>");
		printWriter.write("<p id='uri' style='color:green'> Path:");
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

