package com.keypuncher.bitshift.interpreter;

import com.keypuncher.bitshift.*;
import com.keypuncher.bitshift.console.Console;
import com.keypuncher.bitshift.dialogue.Dialogue;
import com.keypuncher.bitshift.rooms.*;

public class ScriptAnalyser {

	/*
	 * TODO add more script options
	 * 
	 */
	
	public static void executeScript(String input) {
		String[] scriptArray = input.split("\n");
		for (String script : scriptArray) {
			executeCommand(script);
		}
	}

	private static boolean testTrue(String script) {
		if (script.contains("=")) {
			return (script.substring(0, script.indexOf("=")).equals(script.substring(script.indexOf("=")+1)));
		} else if (script.contains(">")) {
			return (Double.parseDouble(script.substring(0, script.indexOf(">")))
					> Double.parseDouble(script.substring(script.indexOf(">")+1)));
		} else if (script.contains("<")) {
			return (Double.parseDouble(script.substring(0, script.indexOf("<")))
					< Double.parseDouble(script.substring(script.indexOf("<")+1)));
		}
		
		return false;
	}
	
	private static void executeCommand(String script) {
		if (script.toLowerCase().contains("_var")) {
			double value = 
					GlobalVariables.globalVariables.get(
					script.substring(script.indexOf("_var:") + 5, script.indexOf("_var:") 
							+ script.substring(script.indexOf("_var:")).indexOf(';')));
			script = script.substring(0, script.indexOf("_var:")) + Double.toString(value) 
					+ script.substring(script.indexOf("_var:")+script.substring(script.indexOf("_var:")).indexOf(';')+1);
			executeCommand(script);
		}
		
		else if (script.toLowerCase().contains("_trig")) {
			boolean value = 
					GlobalVariables.globalTriggers.get(
					script.substring(script.indexOf("_trig:") + 6, script.indexOf("_trig:")
							+script.substring(script.indexOf("_trig:")).indexOf(';')));
			script = script.substring(0, script.indexOf("_trig:")) + Boolean.toString(value) 
					+ script.substring(script.indexOf("_trig:")+script.substring(script.indexOf("_trig:")).indexOf(';')+1);
			executeCommand(script);
		}
		
		else if (script.toLowerCase().contains("_hasitem")) {
			String result = "false";
			String option = script.toLowerCase().substring(	
				script.indexOf(':', script.indexOf("_hasitem")) + 1, 
				script.indexOf(':', script.indexOf(':', script.indexOf("_hasitem"))+1));
			
			
			if(option.equals("player")) {
				if(GlobalVariables.player.getItems().contains(GlobalVariables.items.get(
						script.substring(script.indexOf(':', script.indexOf(':', script.indexOf("_hasitem"))+1)+1, 
								script.indexOf(';', script.indexOf(':', script.indexOf("_hasitem"))+1))))) {
					result = "true";
				} else {
					result = "false";
				}
			} else {
				for(Room room : GlobalVariables.rooms) {
					if(option.equals(room.name.toLowerCase())) {
						if(room.items.contains(GlobalVariables.items.get(
								script.substring(script.indexOf(':', script.indexOf(':', script.indexOf("_hasitem"))+1) + 1, 
										script.indexOf(';', script.indexOf(':', script.indexOf("_hasitem"))+1))))) {
							result = "true";
						} else {
							result = "false";
						}
					}
				}
			}

			
			executeCommand(script.substring(0, script.indexOf("_hasitem")) + result + 
					script.substring(script.indexOf(";", script.indexOf("_hasitem"))+1));
			
		}
		
		else if (script.toLowerCase().contains("_if[")) {
			if(testTrue(script.substring(script.indexOf("_if[")+4, script.indexOf("]")))) {
				executeCommand(script.substring(script.indexOf("]", script.indexOf("_if["))+1));
			} else return;
		}
		
		else if (script.toLowerCase().contains("_repeat")) {
			int recursion = Integer.parseInt(script.substring(script.indexOf(':')
					+ 1, script.indexOf('(')));
			for(int i = 0; i < recursion;i++) {
				executeCommand(script.substring(script.indexOf('(') + 1, script.lastIndexOf(')')));
			}
		}
		
		else if (script.toLowerCase().contains("_setroom")) {
			String roomID = script.substring(script.indexOf(':', script.indexOf("_setroom")) + 1, script.indexOf(';', script.indexOf("_setroom")));
			for (Room nextRoom : GlobalVariables.rooms) {
				if (nextRoom.name.equals(roomID)) {
					Game.setRoom(nextRoom);
					break;
				}
			}
		}
		
		else if (script.toLowerCase().contains("_setdialogue")) {
			String dialogueID = script.substring(script.indexOf(':') + 1, script.indexOf(';'));
			for (Dialogue dialogue : GlobalVariables.dialogue.values()) {
				if (dialogue.title.equals(dialogueID)) {
					Game.setDialogue(dialogue);
					break;
				}
			}
		}
		
		else if (script.toLowerCase().contains("_additem")) {
			String option = script.toLowerCase().substring(script.indexOf(':') + 1, script.lastIndexOf(':'));
			if(option.equals("player")) {
				GlobalVariables.player.addItem(GlobalVariables.items.get(
						script.substring(script.lastIndexOf(':') + 1, script.lastIndexOf(';'))));
			} else {
				for(Room room : GlobalVariables.rooms) {
					if(option.equals(room.name.toLowerCase())) {
						room.items.add(GlobalVariables.items.get(
								script.substring(script.lastIndexOf(':') + 1, script.lastIndexOf(';'))));
					}
				}
			}
		}
		
		else if (script.toLowerCase().contains("_newline")) {
			Console.newLine();
		}
		
		else if (script.toLowerCase().contains("_println")) {
			if(script.lastIndexOf(":") == script.indexOf(":")) {
				Console.println(script.substring(script.indexOf(":") + 1, script.indexOf(";")));
			} else {
				if(script.substring(script.lastIndexOf(":")+1).toLowerCase().contains("0x")) {
					Console.println(script.substring(script.indexOf(":") + 1, script.lastIndexOf(":")), 
							Integer.parseInt(script.substring(script.lastIndexOf(":")+3, script.indexOf(";")), 16)
							);
				} else {
					Console.println(script.substring(script.indexOf(":") + 1, script.lastIndexOf(":")), 
							Integer.parseInt(script.substring(script.lastIndexOf(":")+1, script.indexOf(";")))
							);
				}
				
			}
		}
		
		else if (script.toLowerCase().contains("_print")) {
			if(script.lastIndexOf(":") == script.indexOf(":")) {
				Console.print(script.substring(script.indexOf(":") + 1, script.indexOf(";")));
			} else {
				if(script.substring(script.lastIndexOf(":")+1).toLowerCase().contains("0x")) {
					Console.print(script.substring(script.indexOf(":") + 1, script.lastIndexOf(":")), 
							Integer.parseInt(script.substring(script.lastIndexOf(":")+3, script.indexOf(";")), 16)
							);
				} else {
					Console.print(script.substring(script.indexOf(":") + 1, script.lastIndexOf(":")), 
							Integer.parseInt(script.substring(script.lastIndexOf(":")+1, script.indexOf(";")))
							);
				}
				
			}
		}

		else if (script.contains("+")) {
			GlobalVariables.globalVariables.put(script.substring(0, script.indexOf("+")), 
					GlobalVariables.globalVariables.get(script.substring(0, script.indexOf("+"))).doubleValue() +
					Double.parseDouble(script.substring(script.indexOf("+")+1, script.indexOf(";"))));
		}
		
		else if (script.contains("=")) {
			GlobalVariables.globalVariables.put(script.substring(0, script.indexOf("=")), 
					Double.parseDouble(script.substring(script.indexOf("=")+1, script.indexOf(";"))));
		}
		
		else if (script.contains("-")) {
			GlobalVariables.globalVariables.put(script.substring(0, script.indexOf("-")), 
					GlobalVariables.globalVariables.get(script.substring(0, script.indexOf("-"))).doubleValue() -
					Double.parseDouble(script.substring(script.indexOf("-")+1, script.indexOf(";"))));
		}
		
		else if (script.contains(":")) {
			GlobalVariables.globalTriggers.put(script.substring(0, script.indexOf(":")), 
			Boolean.parseBoolean(script.substring(script.indexOf(":")+1, script.indexOf(";"))));
		}

	}
	
	public static Room getSubRoom(String script) {
		String roomID = script.substring(script.indexOf(':', script.indexOf("_setroom")) + 1, script.indexOf(';', script.indexOf("_setroom")));
		for (Room nextRoom : GlobalVariables.rooms) {
			if (nextRoom.name.equals(roomID)) {
				return(nextRoom);
			}
		}
		return null;
	}
	
}
