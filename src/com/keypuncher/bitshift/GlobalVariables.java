package com.keypuncher.bitshift;

import java.util.*;

import com.keypuncher.bitshift.dialogue.*;
import com.keypuncher.bitshift.items.*;
import com.keypuncher.bitshift.rooms.*;
import com.keypuncher.creator.windows.overview.OverviewNode;
import com.keypuncher.entities.Player;

public class GlobalVariables {
	
	public static ArrayList<OverviewNode> nodes = new ArrayList<OverviewNode>();
	public static ArrayList<Room> rooms = new ArrayList<Room>();
	public static String startingRoom;

	public static Map<String, Item> items = new HashMap<String, Item>();
	public static Map<String, Double> globalVariables = new HashMap<String, Double>();
	public static Map<String, Boolean> globalTriggers = new HashMap<String, Boolean>();
	public static Map<String, Double> defaultGlobalVariables = new HashMap<String, Double>();
	public static Map<String, Boolean> defaultGlobalTriggers = new HashMap<String, Boolean>();
	public static Map<String, Dialogue> dialogue = new HashMap<String, Dialogue>();

	public static ArrayList<String> mainMenuText = new ArrayList<String>(
			Arrays.asList("Welcome to the Bitshift Text Adventure Game Engine"));
	
	public static ArrayList<String> aboutMenuText = new ArrayList<String>(
			Arrays.asList("Bitshift Text Adventure Game Engine:",
					"Programmed by Borkpaladin,",
					"Copyright <c> 2014"));
	
	public static String title = "Bitshift Text Adventure Game Engine";

	public static Room currentRoom;
	public static Player player;

}
