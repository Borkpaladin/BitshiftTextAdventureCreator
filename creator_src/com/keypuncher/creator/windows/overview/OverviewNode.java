package com.keypuncher.creator.windows.overview;

import java.awt.*;
import java.io.Serializable;
import java.util.*;

import com.keypuncher.bitshift.rooms.*;

public class OverviewNode implements Serializable {
	private static final long serialVersionUID = -3352251446960265611L;
	public int x, y, width = 32, height = 32;
	public Room room;
	public ArrayList<OverviewNode> subNodes = new ArrayList<OverviewNode>();

	public OverviewNode(int x, int y, Room room) {
		this.room = room;
		this.x = x;
		this.y = y;
	}

	public void render(Graphics2D g) {
		width = 20 + g.getFontMetrics().stringWidth(room.name);
		g.setColor(Color.black);
		g.fillRect(x, y, width, height);
		g.setColor(Color.white);
		g.drawString(room.name, x + 10, y + 20);
		g.drawRect(x, y, width, height);
	}

	public void renderHighlighted(Graphics2D g, Color color) {
		width = 20 + g.getFontMetrics().stringWidth(room.name);
		g.setColor(Color.black);
		g.fillRect(x, y, width, height);
		g.setColor(Color.white);
		g.drawString(room.name, x + 10, y + 20);
		g.setColor(color);
		g.drawRect(x, y, width, height);
	}

}
