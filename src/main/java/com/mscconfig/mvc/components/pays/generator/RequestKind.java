package com.mscconfig.mvc.components.pays.generator;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Vladimir
 * Date: 03.09.13
 * Time: 11:10
 * Templates for HTTP - requests
 */
public enum RequestKind {

	/*--------------------Drupal7 Testing (module:commerce_authnet-7.x-1.1.tar.gz) ---------------------------*/
	ANET_D7_AC("ANET(AIM)Drupal7:Authorization and Capture (No FingerPrint)") {
		@Override
		public Map<String,String> getParams(RequestEnity entity) {
			Map<String,String> params = RequestParams.ANET_D7_DATA.getParams(entity);
			params.put("x_type","AUTH_CAPTURE");
			return params;
		}
	},
	ANET_D7_AO("ANET(AIM)Drupal7:Authorization Only (No FingerPrint)") {
		@Override
		public Map<String,String> getParams(RequestEnity entity) {
			Map<String,String> params = RequestParams.ANET_D7_DATA.getParams(entity);
			params.put("x_type","AUTH_ONLY");
			return params;
		}
	},
	ANET_D7_PRIOR_AC("ANET(AIM)Drupal7:PRIOR_AUTH_CAPTURE need specify x_trans_id=? (No FingerPrint)") {
		@Override
		public Map<String,String> getParams(RequestEnity entity) {
			Map<String,String> params = RequestParams.ANET_D7_PROCESSING_DATA.getParams(entity);
			params.put("x_type","PRIOR_AUTH_CAPTURE");
			params.put("x_amount",entity.getAmount());
			params.put("x_trans_id","?????????????");
			return params;
		}
	},
	ANET_D7_VOID("ANET(AIM)Drupal7:VOID need specify x_trans_id=? (No FingerPrint)") {
		@Override
		public Map<String,String> getParams(RequestEnity entity) {
			Map<String,String> params = RequestParams.ANET_D7_PROCESSING_DATA.getParams(entity);
			params.put("x_type","VOID");
			params.put("x_trans_id","?????????????");
			return params;
		}
	},
	ANET_D7_CREDIT("ANET(AIM)Drupal7:CREDIT need specify x_trans_id=? (No FingerPrint)") {
		@Override
		public Map<String,String> getParams(RequestEnity entity) {
			Map<String,String> params = RequestParams.ANET_D7_PROCESSING_DATA.getParams(entity);
			params.put("x_type","CREDIT");

			params.put("x_amount",entity.getAmount());

			String cardNum =entity.getCardNum();
			params.put("x_card_num",cardNum.substring(cardNum.length()-4)); // required last 4 digits of cardNum

			params.put("x_invoice_num","1");
			params.put("x_cust_id","0");
			params.put("x_customer_ip","80.255.64.197");
			params.put("x_email","");
			params.put("x_trans_id","?????????????");
			return params;
		}
	},

	/*--------------------Common ANET Transactions---------------------------*/
	ANET_AC("ANET(AIM)MainRequest:Authorization and Capture (No FingerPrint)") {
		@Override    //  (92) The gateway no longer supports the requested method of integration.
		public Map<String,String> getParams(RequestEnity entity) {

			Map<String,String> params = RequestParams.ANET_LOGIN.getParams(entity);
			params.putAll(RequestParams.ANET_CLIENT_DATA.getParams(entity));

			params.put("x_type","AUTH_CAPTURE");
			params.put("x_delim_data","TRUE"); //For AIM must be: x_delim_data=TRUE & x_relay_response = FALSE
			params.put("x_test_request","FALSE");
			return params;
		}

	},
	ANET_AC_FP("ANET: Authorization and Capture (with FingerPrint)") {
		@Override
		public Map<String,String> getParams(RequestEnity entity) {

			Map<String,String> params = RequestParams.ANET_LOGIN_FP.getParams(entity);
			params.putAll(RequestParams.ANET_CLIENT_DATA.getParams(entity));
			params.put("x_type","AUTH_CAPTURE");
			params.put("x_invoice_num",String.valueOf(System.currentTimeMillis()));
			return params;
		}
	},
	ANET_AUTH_ONLY("ANET: Authorization Only (with FingerPrint)") {
		@Override
		public Map<String,String> getParams(RequestEnity entity) {

			Map<String,String> params = RequestParams.ANET_LOGIN_FP.getParams(entity);
			params.putAll(RequestParams.ANET_CLIENT_DATA.getParams(entity));
			params.put("x_type","AUTH_ONLY");

			params.put("x_invoice_num",String.valueOf(System.currentTimeMillis()));

			/*params.put("x_relay_url",relayResponseUrl);*/
			params.put("x_relay_response","FALSE");

			params.put("notes","extra hot please");

			params.put("x_test_request","FALSE");

			return params;
		}

	},
	ANET_VOID("ANET:Void(with FingerPrint) need specify: x_trans_id or x_split_tender_id") {
		@Override
		public Map<String,String> getParams(RequestEnity entity) {

			Map<String,String> params = RequestParams.ANET_LOGIN_FP.getParams(entity);
			//params.putAll(RequestParams.ANET_CLIENT_DATA.getParams(entity));
			params.put("x_type","VOID");

			params.put("x_test_request","FALSE");
			return params;
		}
	},
	ANET_REFUND("ANET:Credit(Refund)(with FingerPrint) need specify: x_trans_id") {
		@Override
		public Map<String,String> getParams(RequestEnity entity) {

			Map<String,String> params = RequestParams.ANET_LOGIN_FP.getParams(entity);
			//params.putAll(RequestParams.ANET_CLIENT_DATA.getParams(entity));
			params.put("x_type","CREDIT");

			params.put("x_test_request","FALSE");
			return params;
		}
	},
	ANET_AUTH_CAPTURE_SIM("ANET(SIM): Authorization Only") {
		@Override
		public Map<String,String> getParams(RequestEnity entity) {

			Map<String,String> params = RequestParams.ANET_LOGIN_FP.getParams(entity);

			params.put("x_type","AUTH_ONLY");

			params.put("x_amount",entity.getAmount());

			params.put("x_show_form","payment_form");
			params.put("submit_button","Submit");

			params.put("x_test_request","FALSE");

			return params;
		}
		@Override
		public  Map<String,String>  getReplaceMap(RequestEnity entity){
			HashMap<String,String> replaceMap = new HashMap<String, String>();

			replaceMap.put("action=\"/gateway/transact.dll\"","action=\"https://test.authorize.net/gateway/transact.dll\"");

			replaceMap.put("<input type=\"text\" class=\"input_text\" id=\"x_card_num\" name=\"x_card_num\" maxLength=\"16\" autocomplete=\"off\" value=\"\">",
					"<input type=\"text\" class=\"input_text\" id=\"x_card_num\" name=\"x_card_num\" maxLength=\"16\" autocomplete=\"off\" value=\""+entity.getCardNum()+"\">");

			replaceMap.put("<input type=\"text\" class=\"input_text\" id=\"x_exp_date\" name=\"x_exp_date\" maxLength=\"20\" autocomplete=\"off\" value=\"\">",
					"<input type=\"text\" class=\"input_text\" id=\"x_exp_date\" name=\"x_exp_date\" maxLength=\"20\" autocomplete=\"off\" value=\""+entity.getExpDate()+"\">");
			replaceMap.put("<img src=\"content","<img src=\"https://test.authorize.net/gateway/content");
			return replaceMap;
		}
	},

	/*--------------------- UCHARGE MainRequests (incompleted fields) -----------------------------------------*/
	UCHARGE_LOGIN_MAIN("UCHARGE ProcessingRequest:Need specify referenceNumber:(response) and requestType:(sale-auth, sale, credit-auth, credit, void, refund,increment ...)"){
		@Override
		public Map<String, String> getParams(RequestEnity entity) {

			Map<String,String> params = RequestParams.UCHARGE_LOGIN.getParams(entity);
			return params;
		}
		@Override
		public  Map<String,String>  getReplaceMap(RequestEnity entity){
			return RequestParams.UCHARGE_LOGIN.getReplaceMap(entity);
		}
	},
	UCHARGE_CNP_MAIN("UCHARGE(CNP)MainRequest:Need specify requestType:(sale-auth, sale, credit-auth, credit)"){
		@Override
		public Map<String, String> getParams(RequestEnity entity) {

			Map<String,String> params = RequestParams.UCHARGE_CLIENT_DATA_CNP.getParams(entity);
			return params;
		}
		@Override
		public  Map<String,String>  getReplaceMap(RequestEnity entity){
			return RequestParams.UCHARGE_CLIENT_DATA_CNP.getReplaceMap(entity);
		}
	},
	UCHARGE_CARD_PRESENT_MAIN("UCHARGE(CARD_PRESENT)MainRequest:Need specify requestType:(sale-auth, sale, credit-auth, credit)"){
		@Override
		public Map<String, String> getParams(RequestEnity entity) {

			Map<String,String> params = RequestParams.UCHARGE_CLIENT_DATA_CP.getParams(entity);
			params.put("requestType","sale");
			return params;
		}
		@Override
		public  Map<String,String>  getReplaceMap(RequestEnity entity){
			return RequestParams.UCHARGE_CLIENT_DATA_CP.getReplaceMap(entity);
		}
	},
	UCHARGE_SALE_AUTH("UCHARGE(CNP)Sale-Auth: with test fields"){
		@Override
		public Map<String, String> getParams(RequestEnity entity) {

			Map<String,String> params = RequestParams.UCHARGE_CLIENT_DATA_CNP.getParams(entity);
			params.put("requestType","sale-auth");
			return params;
		}
		@Override
		public  Map<String,String>  getReplaceMap(RequestEnity entity){
			return RequestParams.UCHARGE_CLIENT_DATA_CNP.getReplaceMap(entity);
		}
	},

	/*----------------------UCHARGE Common Completed requests-------------------------------*/
	UCHARGE_CNP_SALE_BASIC("UCHARGE(CNP)Sale: Only main fields"){
		@Override
		public Map<String, String> getParams(RequestEnity entity) {

			Map<String,String> params = RequestParams.UCHARGE_CLIENT_DATA_CNP.getParams(entity);
			params.put("requestType","sale");
			return params;
		}
		@Override
		public  Map<String,String>  getReplaceMap(RequestEnity entity){
			return RequestParams.UCHARGE_CLIENT_DATA_CNP.getReplaceMap(entity);
		}
	},
	UCHARGE_CARD_PRESENT_SALE("UCHARGE(CARD_PRESENT): Sale"){
		@Override
		public Map<String, String> getParams(RequestEnity entity) {

			Map<String,String> params = RequestParams.UCHARGE_CLIENT_DATA_CP.getParams(entity);
			params.put("requestType","sale");
			return params;
		}
		@Override
		public  Map<String,String>  getReplaceMap(RequestEnity entity){
			return RequestParams.UCHARGE_CLIENT_DATA_CP.getReplaceMap(entity);
		}
	};


	private String kindName;

	private RequestKind(String stringVal) {
		kindName =stringVal;
	}

	public abstract Map<String,String>  getParams(RequestEnity entity);

	public  Map<String,String>  getReplaceMap(RequestEnity entity){
		return null;
	}

	public String getKindName() {
		return kindName;
	}

	public void setKindName(String kindName) {
		this.kindName = kindName;
	}

	public String toString(){
		return kindName;
	}


}
