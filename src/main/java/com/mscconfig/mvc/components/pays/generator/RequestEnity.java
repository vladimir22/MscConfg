package com.mscconfig.mvc.components.pays.generator;

import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * User: Vladimir
 * Date: 03.09.13
 * Time: 11:58
 * Entity stores all data for request
 */
@Component("requestEnity")
public class RequestEnity {

	String anetApiUrl;
	String anetApiLoginId;
	String anetTransactionKey;

	String ucharApiUrl;
	String ucharMerchantAccountCode;
	String ucharUserName;
	String ucharPassword;
	String relayResponseUrl;
	String amount;
	String expDate;
	String cardNum;

	RequestKind  requestKind;
	Map<String,String> params;
	Map<String,String> replaceMap;

	public RequestEnity() {
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

	public String getUcharMerchantAccountCode() {
		return ucharMerchantAccountCode;
	}

	public void setUcharMerchantAccountCode(String ucharMerchantAccountCode) {
		this.ucharMerchantAccountCode = ucharMerchantAccountCode;
	}

	public String getUcharUserName() {
		return ucharUserName;
	}

	public void setUcharUserName(String ucharUserName) {
		this.ucharUserName = ucharUserName;
	}

	public String getUcharPassword() {
		return ucharPassword;
	}

	public void setUcharPassword(String ucharPassword) {
		this.ucharPassword = ucharPassword;
	}

	public Map<String, String> getReplaceMap() {
		return replaceMap;
	}

	public void setReplaceMap(Map<String, String> replaceMap) {
		this.replaceMap = replaceMap;
	}

	public String getRelayResponseUrl() {
		return relayResponseUrl;
	}

	public void setRelayResponseUrl(String relayResponseUrl) {
		this.relayResponseUrl = relayResponseUrl;
	}

	public String getAmount() {
		return amount;
	}

	public String getAnetAmount() {
		return amount;
	}

	public String getUcharAmount() {
		Double amount = Double.valueOf(this.amount);
		amount*=100;
		Integer a =amount.intValue();
		return a.toString();
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getAnetApiUrl() {
		return anetApiUrl;
	}

	public void setAnetApiUrl(String anetApiUrl) {
		this.anetApiUrl = anetApiUrl;
	}

	public String getUcharApiUrl() {
		return ucharApiUrl;
	}

	public void setUcharApiUrl(String ucharApiUrl) {
		this.ucharApiUrl = ucharApiUrl;
	}

	public RequestKind getRequestKind() {
		return requestKind;
	}

	public void setRequestKind(RequestKind requestKind) {
		this.requestKind = requestKind;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
}
