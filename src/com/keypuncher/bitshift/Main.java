package com.keypuncher.bitshift;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

public class Main extends Canvas implements Runnable {
	private static final long serialVersionUID = -2125899241596216385L;

	public static String title = "Gamma - Text Adventure Game Engine";
	public static int WIDTH = 656, HEIGHT = 341;
	public static Font font;
	public static final int CHARWIDTH = 8, CHARHEIGHT = 12;
	public static final int CHARSPERWIDTH = 80, CHARSPERHEIGHT = 25;
	public static JFrame frame;

	private static boolean running = false;

	private BufferStrategy bs;
	private BufferedImage buffer;
	private boolean buffered = false;

	private int exitButtonColour = 0XC75050;
	private int minButtonCol = 0X282828;
	private long minButtonBackCol = 0X000000;
	private boolean touchingExitButton = false;

	private int mx, my;
	private boolean dragging = false;

	private boolean hasFocus = false;

	private Input input;
	private Game game;
	
	private static boolean test;

	public Main(boolean test) {
		Main.test = test;

		try {
			font = Font.createFont(Font.TRUETYPE_FONT,
					getClass().getResourceAsStream("font.ttf")).deriveFont(16f);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		input = new Input();
		buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));

		frame = new JFrame(title);
		game = new Game();


		BufferedImage icon = new BufferedImage(32, 32,
				BufferedImage.TYPE_INT_ARGB);
		Graphics g = icon.getGraphics();
		g.setFont(font.deriveFont(48f));
		g.setColor(new Color(0X660000));
		g.drawString("G", 3, 29);

		BufferedImage icon2 = new BufferedImage(16, 16,
				BufferedImage.TYPE_INT_ARGB);
		g = icon2.getGraphics();
		g.setFont(font.deriveFont(16f));
		g.setColor(new Color(0X505050));
		// g.drawString("G", 3, 12);

		ArrayList<Image> imageList = new ArrayList<Image>();
		imageList.add(icon2);
		imageList.add(icon);
		frame.setIconImages(imageList);

		if(!test)
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.setResizable(false);
		frame.setUndecorated(true);
		frame.pack();
		frame.setLocationRelativeTo(null);

		setFocusable(true);
		requestFocus(true);
		addKeyListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
		addFocusListener(input);

		frame.setVisible(true);

		new Thread(this).start();
	}

	public void renderWindow(Graphics2D g) {
		if (minButtonCol != 0XFFFFFF)
			minButtonBackCol = ((long)(((minButtonBackCol) >> 24) / 1.24) << 24) | 
			(0X00FFFFFFL & minButtonBackCol);

		if (!touchingExitButton) {
			exitButtonColour = ((int) (((exitButtonColour & 0XFF0000) >> 16) / 1.24) << 16)
					| ((int) (((exitButtonColour & 0X00FF00) >> 8) / 1.24) << 8)
					| ((int) (((exitButtonColour & 0X0000FF)) / 1.24));
			if (exitButtonColour < 0XC75050)
				exitButtonColour = 0XC75050;
		}
		
		if(hasFocus){
			g.setColor(Color.black);
			g.fillRect(0, 0, WIDTH, HEIGHT);
		}
		else { 
			g.setColor(new Color(0XEBEBEB));
			g.fillRect(0, 0, WIDTH, HEIGHT);
			g.setColor(new Color(0XDADADA));
			g.drawRect(7, 30, WIDTH-15, HEIGHT-38);
			g.setColor(new Color(0XD3D3D3));
			g.drawRect(0, 0, WIDTH-1, HEIGHT-1);
		}
		g.setFont(font);

		g.setColor(new Color(exitButtonColour));
		g.fillRect(WIDTH - 52, 1, 45, 20);
		g.setColor(Color.white);

		g.fillRect(WIDTH - (19 + 14), 8, 1, 1);
		g.fillRect(WIDTH - (18 + 14), 8, 1, 1);
		g.fillRect(WIDTH - (13 + 14), 8, 1, 1);
		g.fillRect(WIDTH - (12 + 14), 8, 1, 1);

		g.fillRect(WIDTH - (18 + 14), 9, 1, 1);
		g.fillRect(WIDTH - (17 + 14), 9, 1, 1);
		g.fillRect(WIDTH - (14 + 14), 9, 1, 1);
		g.fillRect(WIDTH - (13 + 14), 9, 1, 1);

		g.fillRect(WIDTH - (17 + 14), 10, 1, 1);
		g.fillRect(WIDTH - (16 + 14), 10, 1, 1);
		g.fillRect(WIDTH - (15 + 14), 10, 1, 1);
		g.fillRect(WIDTH - (14 + 14), 10, 1, 1);

		g.fillRect(WIDTH - (16 + 14), 11, 1, 1);
		g.fillRect(WIDTH - (15 + 14), 11, 1, 1);

		g.fillRect(WIDTH - (19 + 14), 14, 1, 1);
		g.fillRect(WIDTH - (18 + 14), 14, 1, 1);
		g.fillRect(WIDTH - (13 + 14), 14, 1, 1);
		g.fillRect(WIDTH - (12 + 14), 14, 1, 1);

		g.fillRect(WIDTH - (18 + 14), 13, 1, 1);
		g.fillRect(WIDTH - (17 + 14), 13, 1, 1);
		g.fillRect(WIDTH - (14 + 14), 13, 1, 1);
		g.fillRect(WIDTH - (13 + 14), 13, 1, 1);

		g.fillRect(WIDTH - (17 + 14), 12, 1, 1);
		g.fillRect(WIDTH - (16 + 14), 12, 1, 1);
		g.fillRect(WIDTH - (15 + 14), 12, 1, 1);
		g.fillRect(WIDTH - (14 + 14), 12, 1, 1);

		g.setColor(new Color(0X141414));
		g.drawRect(WIDTH - 70, 7, 9, 7);
		g.drawLine(WIDTH - 70, 8, WIDTH - 61, 8);

		g.setColor(new Color(
				
				(int)((minButtonBackCol & 0X00FF0000L) >> 16),
				(int)((minButtonBackCol & 0X0000FF00L) >> 8),
				(int)((minButtonBackCol & 0X000000FFL)),
				(int)((minButtonBackCol) >> 24)
				
				));

		g.fillRect(WIDTH - 105, 1, 26, 20);
		g.setColor(new Color(minButtonCol));
		g.fillRect(WIDTH - 96, 13, 8, 2);

		g.translate(8, 31);
		
	}
	
	public void run() {
		running = true;
		Graphics2D g;
		while (running) {
			if (!buffered) {
				buffered = true;
				createBufferStrategy(2);
				bs = getBufferStrategy();
			}

			g = (Graphics2D) buffer.getGraphics();

			renderWindow(g);
			
			// Clear screen
			g.setColor(Color.black);
			g.fillRect(0, 0, WIDTH-16, HEIGHT-39);
			
			game.render(g);

			game.update();

			// Blit to screen
			bs.getDrawGraphics().drawImage(buffer, 0, 0, null);
			bs.show();

			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		frame.setVisible(false);
		frame = null;
	}
	
	public static void exit() {
		if(!test)System.exit(0);
		else running = false;
	}

	private class Input implements KeyListener, MouseListener,
			MouseMotionListener, FocusListener {
		@Override
		public void keyPressed(KeyEvent e) {
			game.input(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {

		}

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (dragging) {
				frame.setLocation(e.getXOnScreen() - mx, e.getYOnScreen() - my);
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			if (e.getX() >= WIDTH - 52 && e.getX() <= WIDTH - 52 + 45
					&& e.getY() >= 1 && e.getY() <= 21) {
				exitButtonColour = 0XE04343;
				touchingExitButton = true;
			} else {
				touchingExitButton = false;
			}

			if (e.getX() >= WIDTH - 105 && e.getX() <= WIDTH - 105 + 26
					&& e.getY() >= 1 && e.getY() <= 21) {
				minButtonCol = 0XFFFFFF;
				minButtonBackCol = 0XFF3665B3L;
			} else {
				minButtonCol = 0X282828;
			}

		}

		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

		@Override
		public void mousePressed(MouseEvent e) {
			mx = e.getX();
			my = e.getY();

			if (my <= 23 && e.getButton() == MouseEvent.BUTTON1
					&& (mx < WIDTH - 105 || my > 21))
				dragging = true;

			if (e.getButton() == MouseEvent.BUTTON1) {
				if (e.getX() >= WIDTH - 52 && e.getX() <= WIDTH - 52 + 45
						&& e.getY() >= 1 && e.getY() <= 21) {
					exit();
				}

				if (e.getX() >= WIDTH - 105 && e.getX() <= WIDTH - 105 + 26
						&& e.getY() >= 1 && e.getY() <= 21) {
					frame.setExtendedState(JFrame.ICONIFIED);
				}
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1)
				dragging = false;
		}

		@Override
		public void focusGained(FocusEvent e) {
			hasFocus = true;
		}

		@Override
		public void focusLost(FocusEvent e) {
			hasFocus = false;
		}
	}

	public static void main(String[] args) {
		new Main(false);
	}

}
