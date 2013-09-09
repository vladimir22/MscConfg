package com.mscconfig.mvc.components.anet;

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

	String apiLoginId;
	String transactionKey;
	String relayResponseUrl;
	String amount;
	String expDate;
	String cardNum;
	String apiUrl;
	RequestKind  requestKind;
	Map<String,String> params;
	Map<String,String> replaceMap;

	public RequestEnity() {
	}

	public RequestEnity(String apiLoginId, String transactionKey, String relayResponseUrl, String amount, String expDate, String cardNum, String apiUrl, RequestKind requestKind) {
		this.apiLoginId = apiLoginId;
		this.transactionKey = transactionKey;
		this.relayResponseUrl = relayResponseUrl;
		this.amount = amount;
		this.expDate = expDate;
		this.cardNum = cardNum;
		this.apiUrl = apiUrl;
		this.requestKind = requestKind;
		this.params = requestKind.getParams(apiLoginId,transactionKey,cardNum,expDate,amount,relayResponseUrl);
		this.replaceMap = requestKind.getReplaceMap(cardNum,expDate,amount);
	}

	public String getApiLoginId() {
		return apiLoginId;
	}

	public void setApiLoginId(String apiLoginId) {
		this.apiLoginId = apiLoginId;
	}

	public String getTransactionKey() {
		return transactionKey;
	}

	public void setTransactionKey(String transactionKey) {
		this.transactionKey = transactionKey;
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

	public String getApiUrl() {
		return apiUrl;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
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
