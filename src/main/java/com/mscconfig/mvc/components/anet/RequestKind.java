package com.mscconfig.mvc.components.anet;

import net.authorize.sim.Fingerprint;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Vladimir
 * Date: 03.09.13
 * Time: 11:10
 * Templates for HTTP -requests
 */
public enum RequestKind {

	ANET_AUTH_CAPTURE("ANET(DPM): Authorization and Capture") {
		@Override
		public Map<String,String> getParams(String apiLoginId, String transactionKey,
											String cardNum, String expDate, String amount,
											String relayResponseUrl  ) {

			Fingerprint fingerprint = Fingerprint.createFingerprint(apiLoginId,	transactionKey,	1234567890, amount);
			long x_fp_sequence = fingerprint.getSequence();
			long x_fp_timestamp = fingerprint.getTimeStamp();
			String x_fp_hash = fingerprint.getFingerprintHash();

			HashMap<String,String> params = new HashMap<String, String>();

			params.put("x_type","AUTH_CAPTURE");

			params.put("x_login",apiLoginId);
			params.put("x_fp_sequence",String.valueOf(x_fp_sequence));
			params.put("x_fp_timestamp",String.valueOf(x_fp_timestamp));
			params.put("x_fp_hash",String.valueOf(x_fp_hash));

			params.put("x_method","CC");
			params.put("x_version","3.1");

			params.put("x_card_num",cardNum);
			params.put("x_exp_date",expDate);
			params.put("x_amount",amount);

			params.put("x_invoice_num",String.valueOf(System.currentTimeMillis()));

			/*params.put("x_relay_url",relayResponseUrl);*/
			params.put("x_relay_response","FALSE");  // for "TRUE" you must have REAL relayResponseUrl

			params.put("notes","extra hot please");

			params.put("x_test_request","FALSE");

			return params;
		}
	},
	ANET_AUTH_ONLY("ANET(DPM): Authorization Only") {
		@Override
		public Map<String,String> getParams(String apiLoginId, String transactionKey,
											String cardNum, String expDate, String amount,
											String relayResponseUrl  ) {

			Fingerprint fingerprint = Fingerprint.createFingerprint(apiLoginId,	transactionKey,	1234567890, amount);
			long x_fp_sequence = fingerprint.getSequence();
			long x_fp_timestamp = fingerprint.getTimeStamp();
			String x_fp_hash = fingerprint.getFingerprintHash();

			HashMap<String,String> params = new HashMap<String, String>();

			params.put("x_type","AUTH_CAPTURE");

			params.put("x_login",apiLoginId);
			params.put("x_fp_sequence",String.valueOf(x_fp_sequence));
			params.put("x_fp_timestamp",String.valueOf(x_fp_timestamp));
			params.put("x_fp_hash",String.valueOf(x_fp_hash));

			params.put("x_method","CC");
			params.put("x_version","3.1");

			params.put("x_card_num",cardNum);
			params.put("x_exp_date",expDate);
			params.put("x_amount",amount);

			params.put("x_invoice_num",String.valueOf(System.currentTimeMillis()));

			/*params.put("x_relay_url",relayResponseUrl);*/
			params.put("x_relay_response","FALSE");

			params.put("notes","extra hot please");

			params.put("x_test_request","FALSE");

			return params;
		}
	},
	ANET_AUTH_CAPTURE_SIM("ANET(SIM): Authorization Only") {
		@Override
		public Map<String,String> getParams(String apiLoginId, String transactionKey,
											String cardNum, String expDate, String amount,
											String relayResponseUrl  ) {

			Fingerprint fingerprint = Fingerprint.createFingerprint(apiLoginId,	transactionKey,	1234567890, amount);
			long x_fp_sequence = fingerprint.getSequence();
			long x_fp_timestamp = fingerprint.getTimeStamp();
			String x_fp_hash = fingerprint.getFingerprintHash();

			HashMap<String,String> params = new HashMap<String, String>();

			params.put("x_type","AUTH_ONLY");

			params.put("x_login",apiLoginId);
			params.put("x_fp_sequence",String.valueOf(x_fp_sequence));
			params.put("x_fp_timestamp",String.valueOf(x_fp_timestamp));
			params.put("x_fp_hash",String.valueOf(x_fp_hash));

			params.put("x_version","3.1");
			params.put("x_method","CC");

			params.put("x_amount",amount);

			params.put("x_show_form","payment_form");
			params.put("submit_button","Submit");

			params.put("x_test_request","FALSE");

			return params;
		}
		@Override
		public  Map<String,String>  getReplaceMap(String cardNum, String expDate, String amount){
			HashMap<String,String> replaceMap = new HashMap<String, String>();

			replaceMap.put("action=\"/gateway/transact.dll\"","action=\"https://test.authorize.net/gateway/transact.dll\"");

			replaceMap.put("<input type=\"text\" class=\"input_text\" id=\"x_card_num\" name=\"x_card_num\" maxLength=\"16\" autocomplete=\"off\" value=\"\">",
					"<input type=\"text\" class=\"input_text\" id=\"x_card_num\" name=\"x_card_num\" maxLength=\"16\" autocomplete=\"off\" value=\""+cardNum+"\">");

			replaceMap.put("<input type=\"text\" class=\"input_text\" id=\"x_exp_date\" name=\"x_exp_date\" maxLength=\"20\" autocomplete=\"off\" value=\"\">",
					"<input type=\"text\" class=\"input_text\" id=\"x_exp_date\" name=\"x_exp_date\" maxLength=\"20\" autocomplete=\"off\" value=\""+expDate+"\">");
			replaceMap.put("<img src=\"content","<img src=\"https://test.authorize.net/gateway/content");
			return replaceMap;
		}
	},
	UCHARGE_LOGIN_MAIN("UCHARGE ProcessingRequest:Need specify referenceNumber:(response) and requestType:(sale-auth, sale, credit-auth, credit, void, refund,increment ...)"){
		@Override
		public Map<String, String> getParams(String apiLoginId, String transactionKey, String cardNum, String expDate, String amount, String relayResponseUrl) {

			Map<String,String> params = CommonParams.UCHARGE_LOGIN.getParams(apiLoginId,transactionKey,cardNum,expDate,amount,relayResponseUrl);
			return params;
		}
		@Override
		public  Map<String,String>  getReplaceMap(String cardNum, String expDate, String amount){
			return CommonParams.UCHARGE_LOGIN.getReplaceMap(cardNum,expDate,amount);
		}
	},
	UCHARGE_CNP_MAIN("UCHARGE(CNP)MainRequest:Need specify requestType:(sale-auth, sale, credit-auth, credit)"){
		@Override
		public Map<String, String> getParams(String apiLoginId, String transactionKey, String cardNum, String expDate, String amount, String relayResponseUrl) {

			Map<String,String> params = CommonParams.UCHARGE_CNP_MAIN.getParams(apiLoginId,transactionKey,cardNum,expDate,amount,relayResponseUrl);
			return params;
		}
		@Override
		public  Map<String,String>  getReplaceMap(String cardNum, String expDate, String amount){
			return CommonParams.UCHARGE_CNP_MAIN.getReplaceMap(cardNum,expDate,amount);
		}
	},
	UCHARGE_CARD_PRESENT_MAIN("UCHARGE(CARD_PRESENT)MainRequest:Need specify requestType:(sale-auth, sale, credit-auth, credit)"){
		@Override
		public Map<String, String> getParams(String apiLoginId, String transactionKey, String cardNum, String expDate, String amount, String relayResponseUrl) {

			Map<String,String> params = CommonParams.UCHARGE_CARD_PRESENT_MAIN.getParams(apiLoginId,transactionKey,cardNum,expDate,amount,relayResponseUrl);
			params.put("requestType","sale");
			return params;
		}
		@Override
		public  Map<String,String>  getReplaceMap(String cardNum, String expDate, String amount){
			return CommonParams.UCHARGE_CARD_PRESENT_MAIN.getReplaceMap(cardNum,expDate,amount);
		}
	},
	UCHARGE_SALE_AUTH("UCHARGE(CNP)Sale-Auth: with test fields"){
		@Override
		public Map<String, String> getParams(String apiLoginId, String transactionKey, String cardNum, String expDate, String amount, String relayResponseUrl) {

			Map<String,String> params = CommonParams.UCHARGE_CNP_MAIN.getParams(apiLoginId,transactionKey,cardNum,expDate,amount,relayResponseUrl);
			params.put("requestType","sale-auth");
			return params;
		}
		@Override
		public  Map<String,String>  getReplaceMap(String cardNum, String expDate, String amount){
			return CommonParams.UCHARGE_CNP_MAIN.getReplaceMap(cardNum,expDate,amount);
		}
	},

	/*Completed requests*/

	UCHARGE_CNP_SALE_BASIC("UCHARGE(CNP)Sale: Only main fields"){
		@Override
		public Map<String, String> getParams(String apiLoginId, String transactionKey, String cardNum, String expDate, String amount, String relayResponseUrl) {

			Map<String,String> params = CommonParams.UCHARGE_CNP_MAIN.getParams(apiLoginId,transactionKey,cardNum,expDate,amount,relayResponseUrl);
			params.put("requestType","sale");
			return params;
		}
		@Override
		public  Map<String,String>  getReplaceMap(String cardNum, String expDate, String amount){
			return CommonParams.UCHARGE_CNP_MAIN.getReplaceMap(cardNum,expDate,amount);
		}
	},
	UCHARGE_CARD_PRESENT_SALE("UCHARGE(CARD_PRESENT): Sale"){
		@Override
		public Map<String, String> getParams(String apiLoginId, String transactionKey, String cardNum, String expDate, String amount, String relayResponseUrl) {

			Map<String,String> params = CommonParams.UCHARGE_CARD_PRESENT_MAIN.getParams(apiLoginId,transactionKey,cardNum,expDate,amount,relayResponseUrl);
			params.put("requestType","sale");
			return params;
		}
		@Override
		public  Map<String,String>  getReplaceMap(String cardNum, String expDate, String amount){
			return CommonParams.UCHARGE_CARD_PRESENT_MAIN.getReplaceMap(cardNum,expDate,amount);
		}
	};


	private String kindName;

	private RequestKind(String stringVal) {
		kindName =stringVal;
	}

	public abstract Map<String,String>  getParams(String apiLoginId, String transactionKey, String cardNum, String expDate, String amount, String relayResponseUrl  );

	public  Map<String,String>  getReplaceMap(String cardNum, String expDate, String amount){
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
