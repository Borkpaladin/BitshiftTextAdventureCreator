package com.keypuncher.bitshift.rooms;

import java.io.File;
import java.io.Serializable;
import java.util.*;

import com.keypuncher.bitshift.Game;
import com.keypuncher.bitshift.GlobalVariables;
import com.keypuncher.bitshift.console.Console;
import com.keypuncher.bitshift.interpreter.Command;
import com.keypuncher.bitshift.interpreter.Interpreter;
import com.keypuncher.bitshift.interpreter.ScriptAnalyser;
import com.keypuncher.bitshift.items.*;
import com.keypuncher.bitshift.menu.Menu;

public class Room implements Serializable {
	private static final long serialVersionUID = 5522696869862384062L;

	// Options
	public int[][][] options = new int[0XFF][0X1F][0X1F];

	// Actual options
	public static final int LOOKAROUND = 1;
	public static final int PROVIDEGAMEHELP = 2;
	public static final int INSPECTINVENTORY = 3;
	public static final int RETURNTOMAINMENU = 4;
	public static final int CLEARSCREEN = 5;
	public static final int SAVEGAME = 6;
	public static final int LOADGAME = 7;

	// Option triggers
	public static final int NULL = 0;

	public static final int WALK = 1;
	public static final int NORTH = 1;
	public static final int EAST = 2;
	public static final int SOUTH = 3;
	public static final int WEST = 4;

	public static final int PICK = 2;
	public static final int UP = 1;

	public static final int LOOK = 3;
	public static final int AT = 1;

	public static final int HELP = 4;

	public static final int INVENTORY = 5;

	public static final int DROP = 6;

	public static final int TALK = 7;
	public static final int TO = 1;

	public static final int EXIT = 8;

	public static final int CLEAR = 9;

	public static final int SAVE = 10;

	public static final int LOAD = 11;

	public static final int MINIMUMVALUE = 12;

	// Decoding
	public static final String[] baseKeyWords = { "walk", "north", "east",
			"south", "west", "pick", "up", "look", "at", "help", "inventory",
			"drop", "talk", "to", "exit", "clear", "save", "load" };
	public static final int[] baseKeyWordValues = { WALK, NORTH, EAST, SOUTH,
			WEST, PICK, UP, LOOK, AT, HELP, INVENTORY, DROP, TALK, TO, EXIT,
			CLEAR, SAVE, LOAD };

	public ArrayList<String> keyWordsArg0 = new ArrayList<String>();
	public ArrayList<String> keyWordsArg1 = new ArrayList<String>();
	public ArrayList<String> keyWordsArg2 = new ArrayList<String>();

	// Boolean flags
	public boolean firstEntrance = true;

	public ArrayList<Item> items = new ArrayList<Item>();
	public ArrayList<String> intro;
	public ArrayList<String> firstEntranceIntro;
	public ArrayList<String> description;

	// Scripting
	public ArrayList<Command> commands = new ArrayList<Command>();

	// Rooms
	public String name = "room";

	public Room() {
		this(new ArrayList<String>(), new ArrayList<String>(),
				new ArrayList<String>());
	}

	public Room(ArrayList<String> intro, ArrayList<String> description,
			ArrayList<String> firstEntranceIntro) {
		this.intro = intro;
		this.description = description;
		this.firstEntranceIntro = firstEntranceIntro;

		options[LOOK][NULL][NULL] = LOOKAROUND;
		options[HELP][NULL][NULL] = PROVIDEGAMEHELP;
		options[INVENTORY][NULL][NULL] = INSPECTINVENTORY;
		options[EXIT][NULL][NULL] = RETURNTOMAINMENU;
		options[CLEAR][NULL][NULL] = CLEARSCREEN;
		options[SAVE][NULL][NULL] = SAVEGAME;
		options[LOAD][NULL][NULL] = LOADGAME;

	}

	public void removeCommand(int i) {
		options[keyWordsArg0.indexOf(commands.get(i).arg0) + MINIMUMVALUE][keyWordsArg1
				.indexOf(commands.get(i).arg1) + MINIMUMVALUE][keyWordsArg2
				.indexOf(commands.get(i).arg2) + MINIMUMVALUE] = NULL;
		commands.remove(i);
	}

	public void addCommandKeyWords(String arg0, String arg1, String arg2) {
		if (!keyWordsArg0.contains(arg0.toLowerCase()))
			keyWordsArg0.add(arg0.toLowerCase());
		if (!keyWordsArg1.contains(arg1.toLowerCase()))
			keyWordsArg1.add(arg1.toLowerCase());
		if (!keyWordsArg2.contains(arg2.toLowerCase()))
			keyWordsArg2.add(arg2.toLowerCase());
	}

	public void addCommand(String name, String script, String arg0,
			String arg1, String arg2) {
		if (!keyWordsArg0.contains(arg0.toLowerCase()))
			keyWordsArg0.add(arg0.toLowerCase());
		if (!keyWordsArg1.contains(arg1.toLowerCase()))
			keyWordsArg1.add(arg1.toLowerCase());
		if (!keyWordsArg2.contains(arg2.toLowerCase()))
			keyWordsArg2.add(arg2.toLowerCase());

		commands.add(new Command(name, script, arg0, arg1, arg2));

	}

	public boolean executeScriptCommand(int arg0, int arg1, int arg2) {
		for (int i = 0; i < commands.size(); i++) {
			if (i < commands.size()
					&& commands.get(i) != null
					&& (arg0 == keyWordsArg0.indexOf(commands.get(i).arg0)
							+ MINIMUMVALUE || (arg0 == NULL && commands.get(i).arg0
							.equals("")))
					&& (arg1 == keyWordsArg1.indexOf(commands.get(i).arg1)
							+ MINIMUMVALUE || (arg1 == NULL && commands.get(i).arg1
							.equals("")))
					&& (arg2 == keyWordsArg2.indexOf(commands.get(i).arg2)
							+ MINIMUMVALUE || (arg2 == NULL && commands.get(i).arg2
							.equals("")))) {
				ScriptAnalyser.executeScript(commands.get(i).script);
				return true;
			}
		}
		return false;
	}

	public void executeCommand(int arg0, int arg1, int arg2) {
		boolean itemCommand = false;
		if (arg0 == LOOK && arg1 == AT) {
			for (Item item : GlobalVariables.player.getItems()) {
				if (item.id == arg2) {
					Console.newLine();
					item.printDescription();
					itemCommand = true;
					break;
				}
			}
			for (Item item : items) {
				if (item.id == arg2) {
					Console.newLine();
					item.printDescription();
					itemCommand = true;
					break;
				}
			}
		}

		if (arg0 == PICK && arg1 == UP) {
			for (Item item : items) {
				if (item.id == arg2) {
					Console.newLine();
					Console.println("You pick up the " + item.name + ".");
					GlobalVariables.player.addItem(item);
					itemCommand = true;
					items.remove(item);
					break;
				}
			}
		}

		if (arg0 == DROP) {
			for (Item item : GlobalVariables.player.getItems()) {
				if (item.id == arg1) {
					Console.newLine();
					Console.println("You drop the " + item.name
							+ " on the ground.");
					items.add(item);
					GlobalVariables.player.removeItem(item);
					itemCommand = true;
					break;
				}
			}
		}

		if (!itemCommand) {
			switch (options[arg0][arg1][arg2]) {
			case LOOKAROUND:
				Console.newLine();
				printDescription();
				break;

			case PROVIDEGAMEHELP:
				Console.newLine();
				Game.printHelp();
				break;

			case INSPECTINVENTORY:
				Console.newLine();
				if (GlobalVariables.player.getItems().size() < 1) {
					Console.println("Your inventory is empty.");

				} else {
					Console.println("You are carrying:");
					for (Item item : GlobalVariables.player.getItems()) {
						Console.println("    - " + item.name);
					}
				}

				break;

			case RETURNTOMAINMENU:
				Game.setMenu(Menu.mainMenu);
				break; // your poor, poor soul.

			case CLEARSCREEN:
				Console.clear();
				break;

			case SAVEGAME:
				if (Interpreter.inputs.length >= 2) {
					File saveFile;
					if (Interpreter.inputs.length < 3) {
						saveFile = new File(Interpreter.inputs[1] + ".sav");
					} else {
						saveFile = new File(Interpreter.inputs[1] + "\\"
								+ Interpreter.inputs[2] + ".sav");
					}
					Console.newLine();
					if (Game.save(saveFile)) {
						Console.println("Game saved, (" + saveFile + ").",
								0X00FF00);
					} else {
						Console.println("Error saving game, (" + saveFile
								+ ").", 0XFF0000);
					}

				} else {
					Console.newLine();
					Console.println("Error, insufficient arguments.", 0XFF0000);
				}
				break;
			case LOADGAME:
				if (Interpreter.inputs.length >= 2) {
					File saveFile;
					if (Interpreter.inputs.length < 3) {
						saveFile = new File(Interpreter.inputs[1] + ".sav");
					} else {
						saveFile = new File(Interpreter.inputs[1] + "\\"
								+ Interpreter.inputs[2] + ".sav");
					}
					Console.newLine();
					if (!Game.load(saveFile)) {
						Console.println("Error loading game, (" + saveFile
								+ ").", 0XFF0000);
					}
				} else {
					Console.newLine();
					Console.println("Error, insufficient arguments.", 0XFF0000);
				}
				break;
			default:
				Console.newLine();
				Console.println("Error, command not recognised.", 0XFF0000);
				break;
			}
		}
	}

	public void printIntro() {
		for (String output : intro)
			Console.println(output);
		if (firstEntrance) {
			for (String output : firstEntranceIntro)
				Console.println(output);
		}
	}

	public void printDescription() {
		for (String output : description)
			Console.println(output);
		if (items != null && items.size() > 0) {
			for (Item item : items)
				item.printRoomDescription();
		}
	}

}
