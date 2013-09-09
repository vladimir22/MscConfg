package com.mscconfig.mvc.components.anet;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * User: Vladimir
 * Date: 03.09.13
 * Time: 10:40
 * Static methods for HTTP Processing
 */
public class HttpUtils {
	private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);

	public static String sendPostMethod( String url, Map<String,String> params) throws IOException {
		String in = null;
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(url);
		for (Map.Entry<String,String> entry : params.entrySet()){
			method.addParameter(entry.getKey(), entry.getValue());
		}
		int statusCode = client.executeMethod(method);
		if (statusCode != -1) {
			in = method.getResponseBodyAsString();
		}
		log.debug("POST-METHOD Response is :"+in);
		return in;
	}

	public static String sendPostMethod( PostMethod method) throws IOException {
		String in = null;
		HttpClient client = new HttpClient();
		int statusCode = client.executeMethod(method);
		if (statusCode != -1) {
			in = method.getResponseBodyAsString();
		}
		log.debug("POST-METHOD Response is :"+in);
		return in;
	}
	public static String sendPostMethod( PostMethod method, RequestEnity reqEntity) throws IOException {
		String in = null;
		HttpClient client = new HttpClient();
		int statusCode = client.executeMethod(method);
		if (statusCode != -1) {
			in = method.getResponseBodyAsString();
		}
		log.debug("POST-METHOD Response is :"+in);
		return in;
	}

	public static PostMethod preparePostMethod( String url, Map<String,String> params) throws IOException {
		PostMethod method = new PostMethod(url);
		for (Map.Entry<String,String> entry : params.entrySet()){
			method.addParameter(entry.getKey(), entry.getValue());
		}
		return  method;
	}

	public static PostMethod preparePostMethod(RequestEnity reqEntity) throws IOException {
		String url = reqEntity.getApiUrl();
		Map<String,String> params = reqEntity.getParams();
		PostMethod method = preparePostMethod(url, params);
		return method;
	}

	public static String sendPostMethod(RequestEnity reqEntity) throws IOException {
		String url = reqEntity.getApiUrl();
		RequestKind requestKind = reqEntity.getRequestKind();
		String apiLoginId = reqEntity.getApiLoginId();
		String transactionKey = reqEntity.getTransactionKey();
		String relayResponseUrl = reqEntity.getRelayResponseUrl();
		String amount = reqEntity.getAmount();
		String expDate = reqEntity.getExpDate();
		String cardNum = reqEntity.getCardNum();
		String apiUrl = reqEntity.getApiUrl();

		Map<String,String> params = requestKind.getParams(apiLoginId,transactionKey,cardNum,expDate,amount,relayResponseUrl);
		StringBuilder answer =  new StringBuilder(sendPostMethod(apiUrl,params));

		makeReplacing(answer, reqEntity);
		return answer.toString();
	}
	public static void makeReplacing( StringBuilder answer, RequestEnity reqEntity){
		RequestKind requestKind = reqEntity.getRequestKind();
		String amount = reqEntity.getAmount();
		String expDate = reqEntity.getExpDate();
		String cardNum = reqEntity.getCardNum();

		Map<String,String> replaceMap =  requestKind.getReplaceMap(cardNum,expDate,amount);
		if(replaceMap!=null)
			makeReplacing(answer,replaceMap);
	}

	public static void makeReplacing( StringBuilder answer, Map<String,String> replaceMap){
		for (Map.Entry<String,String> entry : replaceMap.entrySet()){
			makeReplacing(answer, entry.getKey(), entry.getValue());
		}
	}
	public static void makeReplacing( StringBuilder answer, String target, String replacement){
		int indexOfTarget = -1;
		while( ( indexOfTarget = answer.indexOf( target ) ) > 0 ) {
			answer.replace( indexOfTarget, indexOfTarget + target.length(), replacement );
		}
	}



}
