package com.mscconfig.mvc.components.pays.generator;

import net.authorize.sim.Fingerprint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Vladimir
 * Date: 04.09.13
 * Time: 15:59
 * Begin Params for RequestKind Enums
 */
public enum RequestParams {

	ANET_LOGIN("ANET: Login Parameters"){
		@Override  // Not work!!! : (92) The gateway no longer supports the requested method of integration
		public Map<String, String> getParams(RequestEnity entity) {
			Map<String,String> params = new HashMap<String, String>();
			params.put("x_login",entity.getAnetApiLoginId());
			params.put("x_tran_key",entity.getAnetTransactionKey());

			params.put("x_method","CC");
			params.put("x_version","3.1");
			return params;
		}
	},
	ANET_LOGIN_FP("ANET: Login(FingerPrint) Parameters"){
		@Override
		public Map<String, String> getParams(RequestEnity entity) {
			Map<String,String> params = new HashMap<String, String>();

			Fingerprint fingerprint = Fingerprint.createFingerprint(entity.getAnetApiLoginId(),	entity.getAnetTransactionKey(),
																	1234567890, entity.getAmount());
			long x_fp_sequence = fingerprint.getSequence();
			long x_fp_timestamp = fingerprint.getTimeStamp();
			String x_fp_hash = fingerprint.getFingerprintHash();

			params.put("x_login",entity.getAnetApiLoginId());

			params.put("x_fp_sequence",String.valueOf(x_fp_sequence));
			params.put("x_fp_timestamp",String.valueOf(x_fp_timestamp));
			params.put("x_fp_hash",String.valueOf(x_fp_hash));

			params.put("x_method","CC");
			params.put("x_version","3.1");
			return params;
		}
	},
	ANET_CLIENT_DATA("ANET: Client Card Data"){
		@Override
		public Map<String, String> getParams(RequestEnity entity) {
			Map<String,String> params = new HashMap<String, String>();
			params.put("x_card_num",entity.getCardNum());
			params.put("x_exp_date",entity.getExpDate());
			params.put("x_amount",entity.getAmount());
			return params;
		}
	},
	ANET_D7_DATA("ANET: Drupal7 module custom data"){
		@Override
		public Map<String, String> getParams(RequestEnity entity) {

			Map<String,String> params = RequestParams.ANET_LOGIN.getParams(entity);
			params.putAll(RequestParams.ANET_CLIENT_DATA.getParams(entity));
			params.put("x_card_code","111"); // CCV Code (for TestCard Any numbers)
			params.put("x_currency_code","USD");

			params.put("x_delim_data","TRUE");
			params.put("x_delim_char","|");
			params.put("x_encap_char","\"");
			params.put("x_relay_response","FALSE");

			params.put("x_invoice_num",String.valueOf(System.currentTimeMillis()).substring(0,1)); //Generate Random invoice number
			params.put("x_description","");
			params.put("x_test_request","FALSE");

			params.put("x_email","");
			params.put("x_email_customer","1");
			params.put("x_cust_id","0");
			params.put("x_customer_ip","80.255.64.197");
			params.put("x_first_name","Vladimir");
			params.put("x_last_name","");
			params.put("x_company","");
			params.put("x_address","Donetsk");
			params.put("x_city","Donetsk");
			params.put("x_state","Donetsk");
			params.put("x_zip","83000");
			params.put("x_country","UA");

			params.put("x_solution_id","A1000009");//Maybe D7 module ID

			return params;
		}
	},
	ANET_D7_PROCESSING_DATA("ANET: Drupal7 module data for (VOID,CREDIT,PRIOR_AUTH_CAPTURE"){
		@Override
		public Map<String, String> getParams(RequestEnity entity) {
			Map<String,String> params = new HashMap<String, String>();

			params.put("x_login",entity.getAnetApiLoginId());
			params.put("x_tran_key",entity.getAnetTransactionKey());

			params.put("x_delim_char","|");
			params.put("x_delim_data","TRUE");
			params.put("x_encap_char","\"");
			params.put("x_relay_response","FALSE");

			params.put("x_email_customer","1");

			params.put("x_test_request","FALSE");
			params.put("x_version","3.1");
			params.put("x_solution_id","A1000009");


			return params;
		}
	},

	/* -----------UCHAR Parameters ------------------------*/

	UCHARGE_LOGIN("UCHARGE: Login Parameters"){
		@Override
		public Map<String, String> getParams(RequestEnity entity) {
			HashMap<String,String> params = new HashMap<String, String>();

			/*requestType  will be added after*/
			params.put("userName", entity.getUcharUserName());
			params.put("password",entity.getUcharPassword());
			params.put("merchantAccountCode",entity.getUcharMerchantAccountCode());
			return params;
		}
		@Override
		public  Map<String,String>  getReplaceMap(RequestEnity entity){
			HashMap<String,String> replaceMap = new HashMap<String, String>();
			replaceMap.put("&","<br>");
			replaceMap.put("+","&nbsp");
			return replaceMap;
		}
	},
	UCHARGE_CLIENT_DATA_CNP("UCHARGE: CardNotPresent Client Data"){
		@Override
		public Map<String, String> getParams(RequestEnity entity) {
			Map<String,String> params = UCHARGE_LOGIN.getParams(entity);

			params.put("transactionIndustryType","RE");
			params.put("accountType","R");  /*D-Debit Card ; R-Credit Card*/
			params.put("amount",entity.getUcharAmount());

			params.put("accountNumber", entity.getCardNum());
			params.put("accountAccessory",entity.getExpDate());

			/*Place here optional parameters for CNP */
			return params;
		}
		@Override
		public  Map<String,String>  getReplaceMap(RequestEnity entity){
			return UCHARGE_LOGIN.getReplaceMap(entity);
		}
	},

	UCHARGE_CLIENT_DATA_CP("UCHARGE: CardPresent main Parameters"){
		@Override
		public Map<String, String> getParams(RequestEnity entity) {
			Map<String,String> params = UCHARGE_LOGIN.getParams(entity);

			params.put("transactionIndustryType","RE");
			params.put("accountType","R");  /*D-Debit Card ; R-Credit Card*/
			params.put("amount",entity.getUcharAmount());

			params.put("trackData",entity.getCardNum()); // Must be like : "%B5499740000000057^Smith/John^13121011000 1111A123456789012?"
			return params;
		}
		@Override
		public  Map<String,String>  getReplaceMap(RequestEnity entity){
			return UCHARGE_LOGIN.getReplaceMap(entity);
		}

	},
	UCHARGE_CNP_TEST("UCHARGE: CardNotPresent with test Parameters"){
		@Override
		public Map<String, String> getParams(RequestEnity entity) {
			Map<String,String> params = UCHARGE_CLIENT_DATA_CNP.getParams(entity);

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
		public  Map<String,String>  getReplaceMap(RequestEnity entity){
			return UCHARGE_CLIENT_DATA_CNP.getReplaceMap(entity);
		}
	}
	;
	private static final Logger log = LoggerFactory.getLogger(RequestParams.class);


	private String commonParamsName;
	private RequestParams(String stringVal) {
		commonParamsName =stringVal;
	}
	public abstract Map<String,String> getParams(RequestEnity entity);

	public  Map<String,String>  getReplaceMap(RequestEnity entity){
		return null;
	}
	public String getCommonParamsName() {
		return commonParamsName;
	}
	public void setCommonParamsName(String commonParamsName) {
		this.commonParamsName = commonParamsName;
	}
}
