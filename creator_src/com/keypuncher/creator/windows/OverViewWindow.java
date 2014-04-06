package com.keypuncher.creator.windows;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import com.keypuncher.bitshift.*;
import com.keypuncher.bitshift.interpreter.*;
import com.keypuncher.bitshift.rooms.*;
import com.keypuncher.creator.*;
import com.keypuncher.creator.windows.overview.*;

public class OverViewWindow extends Canvas implements Runnable, MouseListener,
		MouseMotionListener {
	private static final long serialVersionUID = 3324774073436935328L;

	public int WIDTH = 640, HEIGHT = 480;
	public boolean running = false;
	public boolean buffered = false;
	private BufferStrategy bs;

	private OverviewNode selectedNode;
	private boolean dragging = false;
	private boolean draggingScreen = false;
	private int xdif, ydif;
	private int xOffs, yOffs, mx, my;
	
	public OverViewWindow() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addMouseListener(this);
		addMouseMotionListener(this);
		setFocusable(true);
		requestFocus();
	}

	public void start() {
		new Thread(this).start();
	}

	public void run() {
		running = true;
		while (running) {
			render();
			update();
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void render() {
		if (!buffered) {
			buffered = true;
			createBufferStrategy(2);
			bs = getBufferStrategy();
		}

		Graphics2D g = (Graphics2D) bs.getDrawGraphics();

		g.setColor(new Color(0X3977B4));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.setColor(new Color(0XC0C0C0));
		for(int x = 0;x < WIDTH/20;x++) {
			g.drawLine(x*20, 0, x*20, HEIGHT);
		}
		for(int y = 0;y < HEIGHT/20;y++) {
			g.drawLine(0, y*20, WIDTH, y*20);
		}
		
		g.translate(xOffs, yOffs);

		for (OverviewNode node : GlobalVariables.nodes) {
			if (selectedNode == null || !selectedNode.equals(node)) {
				g.setColor(Color.black);
				for (OverviewNode subNode : node.subNodes) {
					g.drawLine(subNode.x + subNode.width / 2, subNode.y
							+ subNode.height / 2, node.x + node.width / 2,
							node.y + node.height / 2);
				}
			}
		}

		for (OverviewNode node : GlobalVariables.nodes) {
			if (selectedNode != null && selectedNode.equals(node)) {
				g.setColor(Color.red);
				for (OverviewNode subNode : node.subNodes) {
					g.drawLine(subNode.x + subNode.width / 2, subNode.y
							+ subNode.height / 2, node.x + node.width / 2,
							node.y + node.height / 2);
				}
			}
		}

		for (OverviewNode node : GlobalVariables.nodes) {
			if (selectedNode != null && selectedNode.equals(node)) {
				node.renderHighlighted(g, Color.red);
			} else if(node.room.name.equals(GlobalVariables.startingRoom)) {
				node.renderHighlighted(g, Color.yellow);
			} else
				node.render(g);
		}

		g.dispose();
		bs.show();
	}

	public void update() {
		for (Room room : GlobalVariables.rooms) {
			boolean valid = false;
			for (OverviewNode node : GlobalVariables.nodes) {
				if (node.room.name.equals(room.name)) {
					node.room = room;
					valid = true;
					break;
				}
			}
			if (!valid)
				GlobalVariables.nodes.add(new OverviewNode(-xOffs+20, -yOffs+20, room));
		}
		int i = 0;
		while (i < GlobalVariables.nodes.size()) {
			boolean valid = false;
			for (Room room : GlobalVariables.rooms) {
				if (GlobalVariables.nodes.get(i).room.name.equals(room.name)) {
					i++;
					valid = true;
					break;
				}
			}
			if (!valid)
				GlobalVariables.nodes.remove(GlobalVariables.nodes.get(i));
		}

		for (OverviewNode node : GlobalVariables.nodes) {
			node.subNodes.clear();
			for (Command c : node.room.commands) {
				for (String s : c.script.split("\n")) {
					if (s.contains("_setroom")) {
						Room r = ScriptAnalyser.getSubRoom(s);
						for (OverviewNode compareNode : GlobalVariables.nodes) {
							if (compareNode.room.equals(r)) {
								node.subNodes.add(compareNode);
								break;
							}
						}
					}
				}
			}

		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		selectedNode = null;
		for (OverviewNode node : GlobalVariables.nodes) {
			if (new Rectangle(node.x, node.y, node.width, node.height)
					.contains(e.getX()-xOffs, e.getY()-yOffs)) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					Creator.roomWindow.edit(node.room);
					Creator.roomWindow.setVisible(true);
				}
				dragging = true;
				selectedNode = node;
				xdif = e.getX() - node.x;
				ydif = e.getY() - node.y;
				break;
			}
		}
		if(selectedNode == null) {
			if(e.getButton() == MouseEvent.BUTTON3) {
				draggingScreen = true;
				mx = e.getX();
				my = e.getY();
			}
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		dragging = false;
		draggingScreen = false;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (selectedNode != null && dragging) {
			selectedNode.x = e.getX() - xdif;
			selectedNode.y = e.getY() - ydif;
		}
		if (draggingScreen) {
			xOffs += e.getX() - mx;
			yOffs += e.getY() - my;
			mx = e.getX();
			my = e.getY();
		}
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

}
