package com.mscconfig.mvc.components.pays.adapters;

import com.mscconfig.mvc.components.pays.adapters.transactions.AnetTransaction;
import com.mscconfig.mvc.components.pays.adapters.transactions.MerchantData;
import com.mscconfig.mvc.components.pays.adapters.transactions.UchargeTranstaction;
import com.mscconfig.mvc.components.pays.generator.HttpUtils;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.URI;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Vladimir
 * Date: 10.09.13
 * Time: 11:22
 * Convertion Manager reqognize kind of request and needed mapping
 */
public class ConvertManager {
	public static final Integer ANET_MESSAGE =0;    //messageType
	public static final Integer UCHARGE_MESSAGE =1;

	public static final Integer ANET_TO_UCHARGE =0;    //convertionType
	public static final Integer UCHARGE_TO_ANET =1;

	private final static String ANET_TRANSACTION_TYPE ="x_type";
	private final static AnetTransaction ANET_DEFAULT_TRANSACTION_TYPE = AnetTransaction.AUTH_CAPTURE;

	private MerchantData  merchantData;

	public ConvertManager(String anetApiUrl, String anetApiLoginId, String anetTransactionKey, String uchargeApiUrl, String uchargeMerchantAccountCode, String uchargeUserName, String uchargePassword) {
		this.merchantData = new MerchantData(anetApiUrl,anetApiLoginId,anetTransactionKey,uchargeApiUrl,uchargeMerchantAccountCode,uchargeUserName,uchargePassword);
	}

	public PostMethod  convert(PostMethod postMethod) throws IOException {
		URI uri = postMethod.getURI();
		String uriName = uri.getHost();
		if (uriName.contains("authorize.net"))
			postMethod = convertToUcharge(postMethod);
		return postMethod;
	}

	public StringBuilder  convertResponse(PostMethod originalPostMethod,PostMethod convertedPostMethod, StringBuilder answer) throws IOException {
		URI originalUri = originalPostMethod.getURI();
		URI convertUri = convertedPostMethod.getURI();
		String originalUriName = originalUri.getHost();
		String convertUriName = convertUri.getHost();
		if (originalUriName.contains("authorize.net")&&convertUriName.contains("unipaygateway.com")){
			Map<String,String> originalRequestParams =getRequestParams(originalPostMethod);
			Map<String,String> convertedRequestParams =getRequestParams(convertedPostMethod);
			Boolean delimitedData = Boolean.valueOf(originalRequestParams.get("x_delim_data"));
			if(delimitedData){
				String delimChar = originalRequestParams.get("x_delim_char");
				if  (delimChar==null) delimChar = ",";
				String encapChar = originalRequestParams.get("x_encap_char");


			}

			Map<String,String> originalPesponseParams = getResponseParams(answer,UCHARGE_MESSAGE);
		}
		return null;
	}

	private Map<String,String> getRequestParams (PostMethod postMethod){
		NameValuePair[] nameValuePairs = postMethod.getParameters();
		Map<String,String> params = new HashMap<String,String>();
		for (NameValuePair pair: nameValuePairs){
			String name = pair.getName();
			String value = pair.getValue();
			params.put(name, value);
		}
		return params;

	}

	public Map<String,String> getAnetResponseMap(StringBuilder answer,String delimChar, String encapChar) {
		int startIndex = -1;
		int endIndex = -1;
		int fieldIndex =0;

		Map<String,String> params = new HashMap<String,String>();
		while (endIndex < answer.length()) {
			endIndex = answer.indexOf(delimChar,startIndex+1);
			if (endIndex==-1) endIndex = answer.length();
			String value = answer.substring(startIndex+1,endIndex);
			startIndex = endIndex;

			if((encapChar!=null)&&(!encapChar.isEmpty()))
				value = value.replace(encapChar,"");
			params.put(ConvertMapping.getAnetResponseField(fieldIndex++),value);
		}
		return params;
	}

	public Map<String,String> getUchargeResponseMap(StringBuilder answer,String fieldEnd, String valueEnd) {
		int fieldIndex = -1;
		int valueIndex = -1;
		Map<String,String> params = new HashMap<String,String>();

		while (valueIndex < answer.length()) {
			String field = answer.substring(valueIndex+1,answer.indexOf(fieldEnd,valueIndex+1));
			fieldIndex = answer.indexOf(fieldEnd,valueIndex);

			valueIndex = answer.indexOf(valueEnd,fieldIndex+1);
			if(valueIndex==-1) valueIndex =answer.length();
			String value = answer.substring(fieldIndex+1,valueIndex);

			params.put(field,value);
		}
		return params;
	}

	private Map<String,String> getResponseParams (StringBuilder answer,Integer messageType){
		if (messageType.equals(UCHARGE_MESSAGE)){
			   return getUchargeResponseMap(answer,"=","&");
		}
		if (messageType.equals(ANET_MESSAGE)){
			return getAnetResponseMap(answer,"=","&");
		}

		return null;
	}

	private PostMethod  convertToUcharge(PostMethod anetPostMethod) throws IOException {
		AnetTransaction anetTransaction = ANET_DEFAULT_TRANSACTION_TYPE;
		Map<String,String> anetParams = new HashMap<String,String>();

		// TODO When to deside how sending Ucharge merchant data in Anet request, this might be change
		anetParams.put("u_userName",this.merchantData.getUchargeUserName());
		anetParams.put("u_password",this.merchantData.getUchargePassword());
		anetParams.put("u_merchantAccountCode",this.merchantData.getUchargeMerchantAccountCode());

		NameValuePair[] nameValuePairs = anetPostMethod.getParameters();
		for (NameValuePair pair: nameValuePairs){
			String name = pair.getName();
			String value = pair.getValue();
			anetParams.put(name, value);
			if(name.equals(ANET_TRANSACTION_TYPE)){
				if(!value.isEmpty()) anetTransaction= AnetTransaction.valueOf(value.toUpperCase());
			}
		}
		UchargeTranstaction uchargeTransaction = (UchargeTranstaction) ConvertMapping.getMappedTransaction(anetTransaction);
		Map<String,String> uchargeParams = uchargeTransaction.getParams(anetParams);
		PostMethod uchargePostMethod = HttpUtils.preparePostMethod(merchantData.getUchargeApiUrl(),uchargeParams);

		return uchargePostMethod;
	}

}
