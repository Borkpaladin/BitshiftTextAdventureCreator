package com.keypuncher.bitshift;

import java.io.*;
import java.util.*;

import com.keypuncher.bitshift.dialogue.*;
import com.keypuncher.bitshift.items.*;
import com.keypuncher.bitshift.rooms.*;
import com.keypuncher.creator.windows.overview.*;
import com.keypuncher.entities.Player;

public class GlobalVariablesSaveData implements Serializable {

	private static final long serialVersionUID = 9099789417529838225L;
	public ArrayList<OverviewNode> nodes = new ArrayList<OverviewNode>();
	public ArrayList<Room> rooms = new ArrayList<Room>();
	public String startingRoom;

	public Map<String, Item> items = new HashMap<String, Item>();
	public Map<String, Double> globalVariables = new HashMap<String, Double>();
	public Map<String, Boolean> globalTriggers = new HashMap<String, Boolean>();
	public Map<String, Double> defaultGlobalVariables = new HashMap<String, Double>();
	public Map<String, Boolean> defaultGlobalTriggers = new HashMap<String, Boolean>();
	public Map<String, Dialogue> dialogue = new HashMap<String, Dialogue>();

	public ArrayList<String> mainMenuText = new ArrayList<String>(
			Arrays.asList("Welcome to the Bitshift Text Adventure Game Engine"));

	public ArrayList<String> aboutMenuText = new ArrayList<String>(
			Arrays.asList("Bitshift Text Adventure Game Engine:",
					"Programmed by Borkpaladin,", "Copyright <c> 2014"));

	public String title = "Bitshift Text Adventure Game Engine";

	public Room currentRoom;
	public Player player;

}
