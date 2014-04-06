package com.keypuncher.bitshift.dialogue;

import java.io.Serializable;
import java.util.*;

import com.keypuncher.bitshift.interpreter.ScriptAnalyser;

public class DialogueOption implements Serializable {
	private static final long serialVersionUID = -3734795591008621603L;
	public String option;
	public String response;
	public DialogueOption[] options;
	public ArrayList<String> script = new ArrayList<String>();
	public ArrayList<Integer> optionIDs = new ArrayList<Integer>();

	public DialogueOption(String option, String response) {
		this.option = option;
		this.response = response;
	}

	public void setOptions(DialogueOption[] options) {
		this.options = options;
	}

	public void triggerOption() {
		ScriptAnalyser.executeScript(arrayListToString(script));
	}

	public String arrayListToString(ArrayList<String> as) {
		String a = "";
		for (String s : as) {
			a += s + "\n";
		}
		return a;
	}

}
