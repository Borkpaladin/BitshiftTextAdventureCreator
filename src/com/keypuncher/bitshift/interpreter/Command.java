package com.keypuncher.bitshift.interpreter;

import java.io.Serializable;

public class Command implements Serializable {
	private static final long serialVersionUID = -3022145271559556141L;
	public String name;
	public String script;
	public String arg0, arg1, arg2;

	public Command(String name, String script, String arg0, String arg1,
			String arg2) {
		this.name = name;
		this.script = script;
		this.arg0 = arg0;
		this.arg1 = arg1;
		this.arg2 = arg2;
	}

}
