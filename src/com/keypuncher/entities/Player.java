package com.keypuncher.entities;

import java.io.Serializable;
import java.util.*;

import com.keypuncher.bitshift.items.*;

public class Player implements Serializable {
	private static final long serialVersionUID = 5401062978545818655L;
	private ArrayList<Item> items = new ArrayList<Item>();

	public void addItem(Item item) {
		item.inOriginalPosition = false;
		items.add(item);
	}

	public ArrayList<Item> getItems() {
		return items;
	}

	public void removeItem(Item item) {
		items.remove(item);
	}

}
