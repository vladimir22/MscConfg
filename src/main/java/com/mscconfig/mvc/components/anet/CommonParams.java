package com.mscconfig.mvc.components.anet;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Vladimir
 * Date: 04.09.13
 * Time: 15:59
 * Begin Params for fill request
 */
public enum CommonParams {
	ANET("ANET: Common Parameters"){
		@Override
		public Map<String, String> getParams(String apiLoginId, String transactionKey, String cardNum, String expDate, String amount, String relayResponseUrl) {
			return null;  //To change body of implemented methods use File | Settings | File Templates.
		}
	},
	UCHARGE_LOGIN("UCHARGE: Login Parameters"){
		@Override
		public Map<String, String> getParams(String apiLoginId, String transactionKey, String cardNum, String expDate, String amount, String relayResponseUrl) {
			HashMap<String,String> params = new HashMap<String, String>();

			/*requestType  will be added after*/
			params.put("userName","vv.ilc@mail.ru");
			params.put("password","Siemens#1");
			params.put("merchantAccountCode",apiLoginId);
			return params;
		}
		@Override
		public  Map<String,String>  getReplaceMap(String cardNum, String expDate, String amount){
			HashMap<String,String> replaceMap = new HashMap<String, String>();
			replaceMap.put("&","<br>");
			replaceMap.put("+","&nbsp");
			return replaceMap;
		}
	},
	UCHARGE_CLIENT_DATA("UCHARGE: Client's common Parameters"){
		@Override
		public Map<String, String> getParams(String apiLoginId, String transactionKey, String cardNum, String expDate, String amount, String relayResponseUrl) {

			Map<String,String> params = UCHARGE_LOGIN.getParams(apiLoginId,transactionKey,cardNum,expDate,amount,relayResponseUrl);

			params.put("transactionIndustryType","RE");
						/*Client data*/
			params.put("accountType","R");  /*D-Debit Card ; R-Credit Card*/
			params.put("amount",amount);
			return params;
		}
		@Override
		public  Map<String,String>  getReplaceMap(String cardNum, String expDate, String amount){
			return UCHARGE_LOGIN.getReplaceMap(cardNum,expDate, amount);
		}
	},
	UCHARGE_CNP_MAIN("UCHARGE: CardNotPresent with basic Parameters"){
		@Override
		public Map<String, String> getParams(String apiLoginId, String transactionKey, String cardNum, String expDate, String amount, String relayResponseUrl) {
			Map<String,String> params = UCHARGE_CLIENT_DATA.getParams(apiLoginId,transactionKey,cardNum,expDate,amount,relayResponseUrl);

			params.put("accountNumber",cardNum);
			params.put("accountAccessory",expDate);

			/*Place here optional parameters for CNP */
			return params;
		}
		@Override
		public  Map<String,String>  getReplaceMap(String cardNum, String expDate, String amount){
			return UCHARGE_CLIENT_DATA.getReplaceMap(cardNum,expDate, amount);
		}
	},
	UCHARGE_CNP_TEST("UCHARGE: CardNotPresent with test Parameters"){
		@Override
		public Map<String, String> getParams(String apiLoginId, String transactionKey, String cardNum, String expDate, String amount, String relayResponseUrl) {
			Map<String,String> params = UCHARGE_CLIENT_DATA.getParams(apiLoginId,transactionKey,cardNum,expDate,amount,relayResponseUrl);

			/*Place here optional parameters for CNP */
			params.put("holderType","P");
			params.put("holderName","John Smith");
			params.put("street","12 Main St");
			params.put("city","Denver");
			params.put("state","CO");
			params.put("zipCode","30301");

			return params;
		}
		@Override
		public  Map<String,String>  getReplaceMap(String cardNum, String expDate, String amount){
			return UCHARGE_CLIENT_DATA.getReplaceMap(cardNum,expDate, amount);
		}
	},
	UCHARGE_CARD_PRESENT_MAIN("UCHARGE: CardPresent main Parameters"){
		@Override
		public Map<String, String> getParams(String apiLoginId, String transactionKey, String cardNum, String expDate, String amount, String relayResponseUrl) {
			Map<String,String> params = UCHARGE_CLIENT_DATA.getParams(apiLoginId,transactionKey,cardNum,expDate,amount,relayResponseUrl);
			params.put("trackData","%B5499740000000057^Smith/John^13121011000 1111A123456789012?");
			return params;
		}
		@Override
		public  Map<String,String>  getReplaceMap(String cardNum, String expDate, String amount){
			return UCHARGE_CLIENT_DATA.getReplaceMap(cardNum,expDate, amount);
		}

	};

	private String commonParamsName;
	private CommonParams(String stringVal) {
		commonParamsName =stringVal;
	}
	public abstract Map<String,String> getParams(String apiLoginId, String transactionKey, String cardNum, String expDate, String amount, String relayResponseUrl  );

	public  Map<String,String>  getReplaceMap(String cardNum, String expDate, String amount){
		return null;
	}
	public String getCommonParamsName() {
		return commonParamsName;
	}
	public void setCommonParamsName(String commonParamsName) {
		this.commonParamsName = commonParamsName;
	}
}
