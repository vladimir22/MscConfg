package com.mscconfig.mvc.components.pays.adapters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Vladimir
 * Date: 12.09.13
 * Time: 11:16
 * Common static methods for transaction converting
 */
public class ConvertUtils {

	public static Map<String,String>  getOutParams(Map<String,String> inParams) throws ConvertException {
		Map<String,String> outParams = new HashMap<>();
		for (Map.Entry<String,String> entry : inParams.entrySet()){
			String inField= entry.getKey();
			String inValue= entry.getValue();
			String outField = ConvertMapping.getMappedField(inField);
			// In here future would be value convertations
			if((outField!=null)&&(!outField.isEmpty()))
					outParams.put(outField,inValue);
		}
		return outParams;
	}

	public static void filloutParams(Map<String,String> inParams, Map<String,String> outParams) throws ConvertException {
		for (Map.Entry<String,String> entry : outParams.entrySet()){
			String field= entry.getKey();
			String mapField = ConvertMapping.getMappedField(field);
			String value = inParams.get(mapField);
			outParams.put(field,value);
		}
	}

	public static void filloutParams(List<String> fields,Map<String,String> inParams, Map<String,String> outParams) throws ConvertException {
		for(String field : fields)
			outParams.put(field,getMappedFieldValue(field,inParams));
	}

	public static String getMappedFieldValue(String field, Map<String,String> inParams) throws ConvertException {
		String mapField = ConvertMapping.getMappedField(field);
	return 	inParams.get(mapField);

	}
	public static String getMappedField(String field) throws ConvertException {
		return 	ConvertMapping.getMappedField(field);

	}
}
