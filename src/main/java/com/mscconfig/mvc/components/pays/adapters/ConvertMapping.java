package com.mscconfig.mvc.components.pays.adapters;
import com.mscconfig.mvc.components.pays.adapters.transactions.AnetTransaction;
import com.mscconfig.mvc.components.pays.adapters.transactions.Transaction;
import com.mscconfig.mvc.components.pays.adapters.transactions.UchargeTranstaction;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * User: Vladimir
 * Date: 10.09.13
 * Time: 8:26
 * Field Mapping Converter Anet <-> Ucharge
 */
public class ConvertMapping {
    private static final Map<String,String> FIELDS = new HashMap<String,String>();
	private static final Map<Transaction,Transaction> TRANSACTIONS = new HashMap<Transaction,Transaction>();
	static {

		FIELDS.put("u_userName", "userName");               // u_*  my own fields added in ConvertManager
		FIELDS.put("u_password", "password");
		FIELDS.put("u_merchantAccountCode", "merchantAccountCode");

		FIELDS.put("x_card_num", "accountNumber");
		FIELDS.put("x_exp_date", "accountAccessory");
		FIELDS.put("x_amount", "amount");
		FIELDS.put("x_card_code", "csc");

		FIELDS.put("x_invoice_num", "transactionCode");
		FIELDS.put("x_description", "memo");

		/*FIELDS.put("x_relay_response", "notifyURL");*/   //Need handling

		FIELDS.put("x_cust_id", "customerAccountCode");
		FIELDS.put("x_first_name", "holderName");
		/*FIELDS.put("x_last_name", "holderName");*/   // Needed logic realisation
		FIELDS.put("x_address", "street");
		FIELDS.put("x_city", "city");
		FIELDS.put("x_state", "state");
		FIELDS.put("x_zip", "zipCode");
		FIELDS.put("x_country", "countryCode");
		FIELDS.put("x_email", "email");

       	FIELDS.put("", "");
		FIELDS.put(null, null);

		TRANSACTIONS.put(AnetTransaction.AUTH_CAPTURE, UchargeTranstaction.SALE);
		TRANSACTIONS.put(AnetTransaction.AUTH_ONLY, UchargeTranstaction.SALE_AUTH);
	}





	/**
	 * Get Mapping field from static map
	 * @param field
	 * @return mapping field
	 * @throws ConvertException if no mapping fields founded
	 */
	public static String getMappedField(String field) throws ConvertException {
		if((field==null)||(field.isEmpty())) throw new ConvertException(" incorrect input field");
		String mapField = FIELDS.get(field);
		if ((mapField==null)||(mapField.isEmpty())) {
			if (!FIELDS.containsValue(field)) return null;
			for (Map.Entry<String, String> entry : FIELDS.entrySet()) {
				if (field.equals(entry.getValue())) {
					mapField =  entry.getKey();
				}
			}
		}
	return mapField;
	}
	public static Transaction getMappedTransaction(Transaction transaction) throws ConvertException {
		if(transaction==null) throw new ConvertException(" incorrect transaction");
		Transaction mapTansaction = TRANSACTIONS.get(transaction);
		if (mapTansaction==null) {
			if (!TRANSACTIONS.containsValue(mapTansaction)) throw new ConvertException(" can't find mapping transaction");
			for (Map.Entry<Transaction, Transaction> entry : TRANSACTIONS.entrySet()) {
				if (transaction.equals(entry.getValue())) {
					mapTansaction =  entry.getKey();
				}
			}
		}
		return mapTansaction;
	}

}
