package com.mscconfig.commands.exceptions;

import java.io.IOException;

/**
 * User: Vladimir
 * Date: 24.05.13
 * Time: 12:50
 * Исключение кот. используем в NsnCmd
 */
public class NsnCmdException extends IOException {
	public NsnCmdException() {
		super("NsnException");
	}

	public NsnCmdException(String message) {
		super("NsnException:"+message);
	}
}
