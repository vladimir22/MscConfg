package com.mscconfig.mvc.components.pays.adapters;

import java.io.IOException;

/**
 * User: Vladimir
 * Date: 12.09.13
 * Time: 11:04
 * Custom exception using by transaction convertion
 */
public class ConvertException extends IOException {
	public ConvertException() {
		super("Transaction Convert failed");
	}

	public ConvertException(String message) {
		super("Transaction Convert failed :"+ message);
	}
}
