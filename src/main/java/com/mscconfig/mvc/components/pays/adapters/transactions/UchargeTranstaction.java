package com.mscconfig.mvc.components.pays.adapters.transactions;

import com.mscconfig.mvc.components.pays.adapters.ConvertException;
import com.mscconfig.mvc.components.pays.adapters.ConvertMapping;
import com.mscconfig.mvc.components.pays.adapters.ConvertUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * User: Vladimir
 * Date: 10.09.13
 * Time: 12:43
 * Kind of Transactions in Ucharge gateway
 */
public enum UchargeTranstaction implements Transaction{
	SALE {
		@Override
		public Map<String, String> getParams(Map<String,String> inParams) throws ConvertException {

			Map<String, String> outParams = ConvertUtils.getOutParams(inParams);
			outParams.put("requestType", "sale");

			outParams.put("accountType", "R");
			outParams.put("transactionIndustryType", "RE");
			// TODO Handle amount convertation , in future realise mechanism
            Double amount = Double.valueOf(outParams.get("amount"));
			amount*=100;
			Integer a =amount.intValue();
			outParams.put("amount", a.toString());
			return outParams;
		}
	},
	SALE_AUTH {
		@Override
		public Map<String, String> getParams(Map<String,String> inParams) throws ConvertException {
			Map<String, String> outParams = ConvertUtils.getOutParams(inParams);
			outParams.put("requestType", "sale-auth");

			outParams.put("accountType", "R");
			outParams.put("transactionIndustryType", "RE");
			// TODO Handle amount convertation , in future realise mechanism
			Double amount = Double.valueOf(outParams.get("amount"));
			amount*=100;
			Integer a =amount.intValue();
			outParams.put("amount", a.toString());
			return outParams;
		}
	},
	CAPTURE {
		@Override
		public Map<String, String> getParams(Map<String,String> params) {
			return null;  //To change body of implemented methods use File | Settings | File Templates.
		}
	},
	VOID{
		@Override
		public Map<String, String> getParams(Map<String,String> inParams) throws ConvertException {
			Map<String, String> outParams = ConvertUtils.getOutParams(inParams);
			outParams.put("requestType", "void");
			return outParams;
		}
	},
	REFUND{
		@Override
		public Map<String, String> getParams(Map<String,String> inParams) throws ConvertException {
			Map<String, String> outParams = ConvertUtils.getOutParams(inParams);
			outParams.put("requestType", "void");
			return outParams;
		}
	},
	CREDIT {
		@Override
		public Map<String, String> getParams(Map<String,String> params) {
			return null;  //To change body of implemented methods use File | Settings | File Templates.
		}
	},

	CREDIT_AUTH {
		@Override
		public Map<String, String> getParams(Map<String,String> params) {
			return null;  //To change body of implemented methods use File | Settings | File Templates.
		}
	},
	INCREMENT {
		@Override
		public Map<String, String> getParams(Map<String,String> params) {
			return null;  //To change body of implemented methods use File | Settings | File Templates.
		}
	},
	DECLINE {
		@Override
		public Map<String, String> getParams(Map<String,String> params) {
			return null;  //To change body of implemented methods use File | Settings | File Templates.
		}
	},
	CHARGEBACK {
		@Override
		public Map<String, String> getParams(Map<String,String> params) {
			return null;  //To change body of implemented methods use File | Settings | File Templates.
		}
	};


}
