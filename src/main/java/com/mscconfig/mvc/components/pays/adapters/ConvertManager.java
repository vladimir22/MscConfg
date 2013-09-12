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
 * Convertation Manager
 */
public class ConvertManager {

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
