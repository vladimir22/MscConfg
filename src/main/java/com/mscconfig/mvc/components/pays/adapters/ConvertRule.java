package com.mscconfig.mvc.components.pays.adapters;

import com.mscconfig.mvc.components.pays.generator.RequestEnity;

import java.util.Map;

/**
 * User: Vladimir
 * Date: 13.09.13
 * Time: 9:52
 * Convertion rules for field values
 */
public enum ConvertRule {
	NO_RULE {
		@Override
		public String getConvertValue(String inValue, Integer convertionType) {
			return inValue;
		}
	},
	AMOUNT {
		@Override
		public String getConvertValue(String inValue,Integer convertionType) {
			Double inAmount = Double.valueOf(inValue);
			String outAmount =null;
			if (convertionType==ConvertManager.ANET_TO_UCHARGE){
				inAmount*=100;
				outAmount = String.valueOf(inAmount.intValue());
			}
			if (convertionType==ConvertManager.UCHARGE_TO_ANET){
                inAmount/=100;
				outAmount = String.valueOf(inAmount);
			}
			return outAmount;
		}
	};

	public abstract String  getConvertValue(String inValue,Integer convertionType);
}
