package com.mscconfig.mvc.components.pays.adapters.transactions;

import com.mscconfig.mvc.components.pays.adapters.ConvertException;

import java.util.Map;

/**
 * User: Vladimir
 * Date: 10.09.13
 * Time: 13:34
 * Please describe this stuff
 */
public interface Transaction  {
	public abstract Map<String,String> getParams(Map<String,String> params) throws ConvertException;
}
