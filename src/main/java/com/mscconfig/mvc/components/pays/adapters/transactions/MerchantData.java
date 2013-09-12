package com.mscconfig.mvc.components.pays.adapters.transactions;

/**
 * User: Vladimir
 * Date: 10.09.13
 * Time: 13:56
 *  Stores all needed merchant(secure) data
 */
public class MerchantData {

	private String anetApiUrl;
	private String anetApiLoginId;
	private String anetTransactionKey;

	private String uchargeApiUrl;
	private String uchargeMerchantAccountCode;
	private String uchargeUserName;
	private String uchargePassword;

	public MerchantData(String anetApiUrl, String anetApiLoginId, String anetTransactionKey, String uchargeApiUrl, String uchargeMerchantAccountCode, String uchargeUserName, String uchargePassword) {
		this.anetApiUrl = anetApiUrl;
		this.anetApiLoginId = anetApiLoginId;
		this.anetTransactionKey = anetTransactionKey;
		this.uchargeApiUrl = uchargeApiUrl;
		this.uchargeMerchantAccountCode = uchargeMerchantAccountCode;
		this.uchargeUserName = uchargeUserName;
		this.uchargePassword = uchargePassword;
	}

	public String getAnetApiUrl() {
		return anetApiUrl;
	}

	public void setAnetApiUrl(String anetApiUrl) {
		this.anetApiUrl = anetApiUrl;
	}

	public String getAnetApiLoginId() {
		return anetApiLoginId;
	}

	public void setAnetApiLoginId(String anetApiLoginId) {
		this.anetApiLoginId = anetApiLoginId;
	}

	public String getAnetTransactionKey() {
		return anetTransactionKey;
	}

	public void setAnetTransactionKey(String anetTransactionKey) {
		this.anetTransactionKey = anetTransactionKey;
	}

	public String getUchargeApiUrl() {
		return uchargeApiUrl;
	}

	public void setUchargeApiUrl(String uchargeApiUrl) {
		this.uchargeApiUrl = uchargeApiUrl;
	}

	public String getUchargeMerchantAccountCode() {
		return uchargeMerchantAccountCode;
	}

	public void setUchargeMerchantAccountCode(String uchargeMerchantAccountCode) {
		this.uchargeMerchantAccountCode = uchargeMerchantAccountCode;
	}

	public String getUchargeUserName() {
		return uchargeUserName;
	}

	public void setUchargeUserName(String uchargeUserName) {
		this.uchargeUserName = uchargeUserName;
	}

	public String getUchargePassword() {
		return uchargePassword;
	}

	public void setUchargePassword(String uchargePassword) {
		this.uchargePassword = uchargePassword;
	}
}
