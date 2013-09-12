package com.mscconfig.mvc.components.pays.adapters.transactions;

import java.util.Map;

/**
 * User: Vladimir
 * Date: 10.09.13
 * Time: 12:03
 * Please describe this stuff
 */
public enum AnetTransaction implements Transaction {
	AUTH_CAPTURE {
		@Override
		public Map<String,String> getParams(Map<String,String> params) {

			return params;
		}
	}, //default
	AUTH_ONLY {
		@Override
		public Map<String,String> getParams(Map<String,String> params) {
			return params;
		}

	},
	CAPTURE_ONLY {
		@Override
		public Map<String,String> getParams(Map<String,String> params) {
			return params;
		}

	},
	CREDIT {
		@Override
		public Map<String,String> getParams(Map<String,String> params) {
			return params;
		}

	},
	PRIOR_AUTH_CAPTURE {
		@Override
		public Map<String,String> getParams(Map<String,String> params) {
			return params;
		}

	},
	VOID {
		@Override
		public Map<String,String> getParams(Map<String,String> params) {
			return params;
		}

	};



}
