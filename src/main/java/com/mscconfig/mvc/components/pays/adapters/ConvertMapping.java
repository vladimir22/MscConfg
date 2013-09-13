package com.mscconfig.mvc.components.pays.adapters;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
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
 * Field & Transaction Mappings  for Anet<->Ucharge converter
 */
public class ConvertMapping {

	private static final Map<Transaction,Transaction> TRANSACTIONS = new HashMap<Transaction,Transaction>();
	/*Interface Table. Type Parameters:
	R - the type of the table row keys
	C - the type of the table column keys
	V - the type of the mapped values*/
	private static final Table<String,String,ConvertRule> TABLE_FIELD = HashBasedTable.create();

	private static final List<String> ANET_RESPONSE_LIST = new LinkedList<>();
	static {

		TABLE_FIELD.put("u_userName", "userName",						ConvertRule.NO_RULE);               // u_*  my own TABLE_FIELD added in ConvertManager
		TABLE_FIELD.put("u_password", "password",						ConvertRule.NO_RULE);
		TABLE_FIELD.put("u_merchantAccountCode", "merchantAccountCode",	ConvertRule.NO_RULE);

		TABLE_FIELD.put("x_card_num", "accountNumber",					ConvertRule.NO_RULE);
		TABLE_FIELD.put("x_exp_date", "accountAccessory",				ConvertRule.NO_RULE);
		TABLE_FIELD.put("x_amount", "amount",							ConvertRule.AMOUNT);
		TABLE_FIELD.put("x_card_code", "csc",							ConvertRule.NO_RULE);

		TABLE_FIELD.put("x_invoice_num", "transactionCode",				ConvertRule.NO_RULE);
		TABLE_FIELD.put("x_description", "memo",						ConvertRule.NO_RULE);
		/*TABLE_FIELD.put("x_relay_response", "notifyURL",				ConvertRule.NO_RULE);*/   //Need handling

		TABLE_FIELD.put("x_cust_id", "customerAccountCode",				ConvertRule.NO_RULE);
		TABLE_FIELD.put("x_first_name", "holderName",					ConvertRule.NO_RULE);
		/*TABLE_FIELD.put("x_last_name", "holderName",					ConvertRule.NO_RULE);*/   // Needed logic realisation
		TABLE_FIELD.put("x_address", "street",							ConvertRule.NO_RULE);
		TABLE_FIELD.put("x_city", "city",								ConvertRule.NO_RULE);
		TABLE_FIELD.put("x_state", "state",								ConvertRule.NO_RULE);
		TABLE_FIELD.put("x_zip", "zipCode",								ConvertRule.NO_RULE);
		TABLE_FIELD.put("x_country", "countryCode",						ConvertRule.NO_RULE);
		TABLE_FIELD.put("x_email", "email",								ConvertRule.NO_RULE);

		TABLE_FIELD.put("x_trans_id", "referenceNumber",				ConvertRule.NO_RULE);


		TRANSACTIONS.put(AnetTransaction.AUTH_CAPTURE, UchargeTranstaction.SALE);
		TRANSACTIONS.put(AnetTransaction.AUTH_ONLY, UchargeTranstaction.SALE_AUTH);
		TRANSACTIONS.put(AnetTransaction.VOID, UchargeTranstaction.VOID);
		TRANSACTIONS.put(AnetTransaction.CREDIT, UchargeTranstaction.REFUND);

		ANET_RESPONSE_LIST.add(0, "Response Code");
		ANET_RESPONSE_LIST.add(1, "Response Subcode");
		ANET_RESPONSE_LIST.add(2, "Response Reason Code");
		ANET_RESPONSE_LIST.add(3, "Response Reason Text");
		ANET_RESPONSE_LIST.add(4, "Authorization Code");
		ANET_RESPONSE_LIST.add(5, "AVS Response");
		ANET_RESPONSE_LIST.add(6, "Transaction ID");
		ANET_RESPONSE_LIST.add(7, "Invoice Number");
		ANET_RESPONSE_LIST.add(8, "Description");
		ANET_RESPONSE_LIST.add(9, "Amount");
		ANET_RESPONSE_LIST.add(10, "Method");
		ANET_RESPONSE_LIST.add(11, "Transaction Type");
		ANET_RESPONSE_LIST.add(12, "Customer ID");
		ANET_RESPONSE_LIST.add(13, "First Name");
		ANET_RESPONSE_LIST.add(14, "Last Name");
		ANET_RESPONSE_LIST.add(15, "Company");
		ANET_RESPONSE_LIST.add(16, "Address");
		ANET_RESPONSE_LIST.add(17, "City");
		ANET_RESPONSE_LIST.add(18, "State");
		ANET_RESPONSE_LIST.add(19, "ZIP Code");
		ANET_RESPONSE_LIST.add(20, "Country");
		ANET_RESPONSE_LIST.add(21, "Phone");
		ANET_RESPONSE_LIST.add(22, "Fax");
		ANET_RESPONSE_LIST.add(23, "Email Address");
		ANET_RESPONSE_LIST.add(24, "Ship To FirstName");
		ANET_RESPONSE_LIST.add(25, "Ship To LastName");
		ANET_RESPONSE_LIST.add(26, "Ship To Company");
		ANET_RESPONSE_LIST.add(27, "Ship To Address");
		ANET_RESPONSE_LIST.add(28, "Ship To City");
		ANET_RESPONSE_LIST.add(29, "Ship To State");
		ANET_RESPONSE_LIST.add(30, "Ship To ZIP Code ");
		ANET_RESPONSE_LIST.add(31, "Ship To Country");
		ANET_RESPONSE_LIST.add(32, "Tax");
		ANET_RESPONSE_LIST.add(33, "Duty");
		ANET_RESPONSE_LIST.add(34, "Freight");
		ANET_RESPONSE_LIST.add(35, "Tax Exempt");
		ANET_RESPONSE_LIST.add(36, "Purchase Order Number");
		ANET_RESPONSE_LIST.add(37, "MD5 Hash");
		ANET_RESPONSE_LIST.add(38, "Card Code Response");
		ANET_RESPONSE_LIST.add(39, "Cardholder Authentication Verification Response");
		ANET_RESPONSE_LIST.add(40, "dummy_field ");
		ANET_RESPONSE_LIST.add(41, "dummy_field ");
		ANET_RESPONSE_LIST.add(42, "dummy_field ");
		ANET_RESPONSE_LIST.add(43, "dummy_field ");
		ANET_RESPONSE_LIST.add(44, "dummy_field ");
		ANET_RESPONSE_LIST.add(45, "dummy_field");
		ANET_RESPONSE_LIST.add(46, "dummy_field ");
		ANET_RESPONSE_LIST.add(47, "dummy_field ");
		ANET_RESPONSE_LIST.add(48, "dummy_field ");
		ANET_RESPONSE_LIST.add(49, "dummy_field ");
		ANET_RESPONSE_LIST.add(50, "Account Number");
		ANET_RESPONSE_LIST.add(51, "Card Type");
		ANET_RESPONSE_LIST.add(52, "Split Tender ID");
		ANET_RESPONSE_LIST.add(53, "Requested Amount");
		ANET_RESPONSE_LIST.add(54, "Balance On Card");
		ANET_RESPONSE_LIST.add(55, "dummy_field ");
		ANET_RESPONSE_LIST.add(56, "dummy_field ");
		ANET_RESPONSE_LIST.add(57, "dummy_field ");
		ANET_RESPONSE_LIST.add(58, "dummy_field ");
		ANET_RESPONSE_LIST.add(59, "dummy_field ");
		ANET_RESPONSE_LIST.add(60, "dummy_field ");
		ANET_RESPONSE_LIST.add(61, "dummy_field ");
		ANET_RESPONSE_LIST.add(62, "dummy_field ");
		ANET_RESPONSE_LIST.add(63, "dummy_field ");
		ANET_RESPONSE_LIST.add(64, "dummy_field ");
		ANET_RESPONSE_LIST.add(65, "dummy_field ");
		ANET_RESPONSE_LIST.add(66, "dummy_field ");
		ANET_RESPONSE_LIST.add(67, "dummy_field ");




	}





	/**
	 * Get Mapping field from static map
	 * @param field
	 * @return mapping field
	 * @throws ConvertException if no mapping fields founded
	 */
	public static Map<String,ConvertRule> getMappedField(String field) throws ConvertException {
		if((field==null)||(field.isEmpty())) throw new ConvertException(" incorrect input field");
		Map<String,ConvertRule> mapField = TABLE_FIELD.row(field);
		if (mapField.isEmpty())
			mapField = TABLE_FIELD.column(field);

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
	public static String getAnetResponseField(Integer index){
		return ANET_RESPONSE_LIST.get(index);
	}

}
