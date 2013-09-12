package com.mscconfig.mvc.components.pays.generator;

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

	public static PostMethod preparePostMethod(RequestEnity entity) throws IOException {
		String url = null;
		if(entity.getRequestKind().name().startsWith("ANET"))
			url = entity.getAnetApiUrl();
		if(entity.getRequestKind().name().startsWith("UCHAR"))
			url = entity.getUcharApiUrl();
		if(url==null) throw new IOException("Can't define Api URL");

		Map<String,String> params = entity.getParams();
		PostMethod method = preparePostMethod(url, params);
		return method;
	}

	public static void makeReplacing( StringBuilder answer, RequestEnity entity){
		Map<String,String> replaceMap =  entity.getRequestKind().getReplaceMap(entity);
		if(replaceMap!=null)
			makeReplacing(answer,replaceMap);
	}

	public static void makeReplacing( StringBuilder answer, Map<String,String> replaceMap){
		if (replaceMap==null) return;
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
