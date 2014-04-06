package com.keypuncher.bitshift;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import com.keypuncher.bitshift.console.*;
import com.keypuncher.bitshift.console.Console;
import com.keypuncher.bitshift.dialogue.*;
import com.keypuncher.bitshift.interpreter.*;
import com.keypuncher.bitshift.menu.Menu;
import com.keypuncher.bitshift.rooms.*;
import com.keypuncher.entities.*;

public class Game {

	public static final String[] basicGameHelp = {
			"Basic Game Commands:",
			"    - Help : Provides info on basic game commands",
			"    - Look : Provides a description on the current room",
			"          look at [x] : provides a description of the given item/object [x]",
			"    - Exit : Returns to the main menu",
			"    - Save : Saves the current game state",
			"          save [title] : saves the current game state with the given title [x]",
			"          save [title] [dir] : saves the current game state at directory [dir]",
			"    - Load : Loads a saved game state",
			"          load [title] : loads a saved game state from a file with the given title [x]",
			"          load [title] [dir] : loads a saved game state from a file at the directory [dir]",
			"    - Inventory : Displays your current inventory",
			"    - Talk to [x] : Starts a conversation with a specified person [x]",
			"    - Walk [direction] : Walks in a given direction (e.g. north, south, southeast)",
			"    - Pick up [x] : Adds an avaliable item to your inventory",
			"    - Drop [x] : Removes an avaliable item from your inventory"

	};

	public static enum State {
		GAME, MENU, DIALOGUE,
	}

	private static State state = State.GAME;

	private static Menu menu;
	private static Dialogue dialogue;
	private Interpreter interpreter;

	public Game() {
		interpreter = new Interpreter();
		Console.clear();
		newGame();
		setMenu(Menu.mainMenu);
		Main.frame.setTitle(GlobalVariables.title);
	}

	public static void newGame() {
		load(new File("data.dat"));
		Console.clear();
		GlobalVariables.player = new Player();
		resetRooms();
		boolean roomSet = false;
		for (Room room : GlobalVariables.rooms) {
			if (room.name.equals(GlobalVariables.startingRoom)) {
				Game.setRoom(room);
				roomSet = true;
				break;
			}
		}
		if (!roomSet)
			Game.setRoom(GlobalVariables.rooms.get(0));
	}

	public static void resetRooms() {
		for (Room room : GlobalVariables.rooms) {
			room.items.clear();
		}

		for (String s : GlobalVariables.globalTriggers.keySet()) {
			GlobalVariables.globalTriggers.put(s,
					GlobalVariables.defaultGlobalTriggers.get(s));
		}

		for (String s : GlobalVariables.globalVariables.keySet()) {
			GlobalVariables.defaultGlobalVariables.put(s,
					GlobalVariables.defaultGlobalVariables.get(s));
		}
	}

	public static boolean save(File file) {
		GlobalVariablesSaveData data = new GlobalVariablesSaveData();
		data.nodes = GlobalVariables.nodes;
		data.rooms = GlobalVariables.rooms;
		data.startingRoom = GlobalVariables.startingRoom;
		data.items = GlobalVariables.items;
		data.globalVariables = GlobalVariables.globalVariables;
		data.globalTriggers = GlobalVariables.globalTriggers;
		data.defaultGlobalVariables = GlobalVariables.defaultGlobalVariables;
		data.defaultGlobalTriggers = GlobalVariables.defaultGlobalTriggers;
		data.dialogue = GlobalVariables.dialogue;
		data.mainMenuText = GlobalVariables.mainMenuText;
		data.aboutMenuText = GlobalVariables.aboutMenuText;
		data.title = GlobalVariables.title;
		data.currentRoom = GlobalVariables.currentRoom;
		data.player = GlobalVariables.player;
		
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(data);
			objectOut.close();
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static boolean load(File file) {
		try {
			FileInputStream fin = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fin);
			GlobalVariablesSaveData data = (GlobalVariablesSaveData) ois
					.readObject();

			GlobalVariables.nodes = data.nodes;
			GlobalVariables.rooms = data.rooms;
			GlobalVariables.startingRoom = data.startingRoom;
			GlobalVariables.items = data.items;
			GlobalVariables.globalVariables = data.globalVariables;
			GlobalVariables.globalTriggers = data.globalTriggers;
			GlobalVariables.defaultGlobalVariables = data.defaultGlobalVariables;
			GlobalVariables.defaultGlobalTriggers = data.defaultGlobalTriggers;
			GlobalVariables.dialogue = data.dialogue;
			GlobalVariables.mainMenuText = data.mainMenuText;
			GlobalVariables.aboutMenuText = data.aboutMenuText;
			GlobalVariables.title = data.title;
			GlobalVariables.currentRoom = data.currentRoom;
			GlobalVariables.player = data.player;
			
			Console.clear();
			Console.println("Game loaded.", 0X00FF00);
			Console.newLine();
			state = State.GAME;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void update() {
		Console.update();
	}

	public void render(Graphics2D g) {
		Console.render(g);
	}

	public void input(KeyEvent e) {
		int key = e.getKeyCode();
		switch (state) {
		case GAME:
			if (key == KeyEvent.VK_BACK_SPACE)
				Console.backspace();
			else if (key == KeyEvent.VK_ENTER) {
				interpreter.interpret(Console.getInput());
				Console.clearInput();
			} else if (Character.isDefined(e.getKeyChar()))
				Console.input(e.getKeyChar());
			break;
		case DIALOGUE:
			dialogue.input(e.getKeyChar());
			break;
		case MENU:
			menu.input(e);
			break;
		}
	}

	public static void printHelp() {
		for (String output : basicGameHelp) {
			Console.println(output, 0X70BB70);
		}
	}

	public static void setRoom(Room nextRoom) {
		GlobalVariables.currentRoom = nextRoom;
		GlobalVariables.currentRoom.printIntro();
		GlobalVariables.currentRoom.printDescription();
		GlobalVariables.currentRoom.firstEntrance = false;
		state = State.GAME;
	}

	public static void setMenu(Menu menu2) {
		menu = menu2;
		Console.clear();
		menu.printInfo();
		state = State.MENU;
	}

	public static void setDialogue(Dialogue dialogue2) {
		dialogue = dialogue2.clone();
		dialogue.displayOptions();
		state = State.DIALOGUE;
	}

	public static void setState(State state2) {
		state = state2;
	}

}
