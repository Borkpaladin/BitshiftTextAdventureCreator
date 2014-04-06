package com.keypuncher.bitshift.items;

import java.io.Serializable;

import com.keypuncher.bitshift.console.Console;

public class Item implements Serializable {
	private static final long serialVersionUID = -6485003472288604422L;
	public boolean pickedUp = false, inOriginalPosition = true;
	public String[] description;
	public String[] roomDescription, placedRoomDescription;
	public String name;
	public int id;

	public Item(String[] description, String[] roomDescription,
			String[] placedRoomDescription, String name, int id) {
		this.description = description;
		this.roomDescription = roomDescription;
		this.placedRoomDescription = placedRoomDescription;
		this.name = name;
		this.id = id;
	}

	public void printRoomDescription() {
		if (inOriginalPosition)
			for (String output : roomDescription)
				Console.println(output);
		else
			for (String output : placedRoomDescription)
				Console.println(output);
	}

	public void printDescription() {
		for (String output : description)
			Console.println(output);
	}

}
