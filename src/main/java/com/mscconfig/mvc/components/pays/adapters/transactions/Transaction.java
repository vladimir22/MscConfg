package com.mscconfig.mvc.components.pays.adapters.transactions;

import com.mscconfig.mvc.components.pays.adapters.ConvertException;
import com.mscconfig.mvc.components.pays.adapters.ConvertManager;

import java.util.Map;

/**
 * User: Vladimir
 * Date: 10.09.13
 * Time: 13:34
 * Common interface for Transaction Enums
 */
public interface Transaction  {

	public static final Integer U = ConvertManager.ANET_TO_UCHARGE;
	public static final Integer A =ConvertManager.UCHARGE_TO_ANET;

	public abstract Map<String,String> getParams(Map<String,String> params) throws ConvertException;
}
