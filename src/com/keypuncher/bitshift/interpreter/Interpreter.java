package com.keypuncher.bitshift.interpreter;

import com.keypuncher.bitshift.*;
import com.keypuncher.bitshift.items.*;
import com.keypuncher.bitshift.rooms.*;

public class Interpreter {

	public static String[] inputs;
	
	public void interpret(String input) {
		String[] inputs = input.toLowerCase().split(" ");
		Interpreter.inputs = inputs;
		int arg0 = Room.NULL, arg1 = Room.NULL, arg2 = Room.NULL;
		
		for (int i = 0; i < inputs.length && i < 3; i++) {
			if (i == 0)
				arg0 = decodeScriptInput(inputs[i], 0);
			if (i == 1)
				arg1 = decodeScriptInput(inputs[i], 1);
			if (i == 2)
				arg2 = decodeScriptInput(inputs[i], 2);
		}

		if(!GlobalVariables.currentRoom.executeScriptCommand(arg0, arg1, arg2)) {
			for (int i = 0; i < inputs.length && i < 3; i++) {
				if (i == 0)
					arg0 = decode(inputs[i], 0);
				if (i == 1)
					arg1 = decode(inputs[i], 1);
				if (i == 2)
					arg2 = decode(inputs[i], 2);
			}
			GlobalVariables.currentRoom.executeCommand(arg0, arg1, arg2);
		}
	}

	public int decodeScriptInput(String input, int argnum) {
		if (argnum == 0) {
			if (GlobalVariables.currentRoom.keyWordsArg0 != null
					&& GlobalVariables.currentRoom.keyWordsArg0.size() > 0) {
				for (int i = 0; i < GlobalVariables.currentRoom.keyWordsArg0.size(); i++) {
					if (GlobalVariables.currentRoom.keyWordsArg0.get(i).equals(input))
						return i + Room.MINIMUMVALUE;
				}
			}
		} else if (argnum == 1) {
			if (GlobalVariables.currentRoom.keyWordsArg1 != null
					&& GlobalVariables.currentRoom.keyWordsArg1.size() > 0) {
				for (int i = 0; i < GlobalVariables.currentRoom.keyWordsArg1.size(); i++) {
					if (GlobalVariables.currentRoom.keyWordsArg1.get(i).equals(input))
						return i + Room.MINIMUMVALUE;
				}
			}
		} else if (argnum == 2) {
			if (GlobalVariables.currentRoom.keyWordsArg2 != null
					&& GlobalVariables.currentRoom.keyWordsArg2.size() > 0) {
				for (int i = 0; i < GlobalVariables.currentRoom.keyWordsArg2.size(); i++) {
					if (GlobalVariables.currentRoom.keyWordsArg2.get(i).equals(input))
						return i + Room.MINIMUMVALUE;
				}
			}
		}
		
		return Room.NULL;
	}
	
	public int decode(String input, int argnum) {
		for (int i = 0; i < Room.baseKeyWords.length; i++) {
			if (Room.baseKeyWords[i].equals(input))
				return Room.baseKeyWordValues[i];
		}

		for (Item item : GlobalVariables.player.getItems()) {
			if (input.equals(item.name.toLowerCase())) {
				return item.id;
			}
		}

		for (Item item : GlobalVariables.currentRoom.items) {
			if (input.equals(item.name.toLowerCase())) {
				return item.id;
			}
		}

		return Room.NULL;
	}

}
