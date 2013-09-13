package com.mscconfig.mvc.components.pays.adapters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * User: Vladimir
 * Date: 12.09.13
 * Time: 11:16
 * Common static methods for transaction converting
 */
public class ConvertUtils {


	public static Map<String,String>  getOutParams(Map<String,String> inParams, Integer convertionType) throws ConvertException {
		Map<String,String> outParams = new HashMap<>();
		for (Map.Entry<String,String> entry : inParams.entrySet()){
			String inField= entry.getKey();
			String inValue= entry.getValue();
			Map<String,ConvertRule> outField = ConvertMapping.getMappedField(inField);
			// In here future would be value convertations
			for(Entry<String,ConvertRule> field : outField.entrySet()){
				outParams.put(field.getKey(),field.getValue().getConvertValue(inValue,convertionType));
			}
		}
		return outParams;
	}


}
