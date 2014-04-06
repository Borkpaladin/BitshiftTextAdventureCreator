package com.keypuncher.creator.windows;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

import com.keypuncher.bitshift.*;

public class GameOptionsWindow extends JFrame {
	private static final long serialVersionUID = -983911570204230356L;

	private Label titleLabel, aboutLabel, mainMenuLabel, roomLabel;
	private TextField title, room;
	private TextArea about, mainMenu;
	
	private Button apply, close;

	public GameOptionsWindow() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		titleLabel = new Label("Title: ");
		aboutLabel = new Label("About Info: ");
		mainMenuLabel = new Label("Main Menu Text: ");
		roomLabel = new Label("Starting Room: ");
		
		title = new TextField(26);
		room = new TextField(26);
		about = new TextArea(12, 40);
		mainMenu = new TextArea(12, 40);
		
		apply = new Button("apply");
		apply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GlobalVariables.title = title.getText();
				GlobalVariables.startingRoom = room.getText();
				GlobalVariables.mainMenuText = new ArrayList<String>(Arrays.asList(mainMenu.getText().split("\n")));
				GlobalVariables.aboutMenuText = new ArrayList<String>(Arrays.asList(about.getText().split("\n")));
			}
		});
		close = new Button("close");
		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.WEST;
		
		c.gridx = 0;
		c.gridy = 0;
		add(titleLabel, c);
		c.gridx = 1;
		add(title, c);
		c.gridx = 0;
		c.gridy = 1;
		add(roomLabel, c);
		c.gridx = 1;
		add(room, c);
		c.gridx = 0;
		c.gridy = 2;
		
		add(aboutLabel, c);
		c.gridy = 3;
		c.gridwidth = 2;
		add(about, c);
		
		c.gridy = 2;
		c.gridwidth = 1;
		c.gridx = 3;
		add(mainMenuLabel, c);
		c.gridy = 3;
		c.gridwidth = 2;
		add(mainMenu, c);
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 4;
		add(apply, c);
		c.gridx = 1;
		add(close, c);
		
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
	}

	public void reload() {
		GlobalVariables.title = title.getText();
		GlobalVariables.startingRoom = room.getText();
		GlobalVariables.mainMenuText = new ArrayList<String>(Arrays.asList(mainMenu.getText().split("\n")));
		GlobalVariables.aboutMenuText = new ArrayList<String>(Arrays.asList(about.getText().split("\n")));
		title.setText(GlobalVariables.title);
		room.setText(GlobalVariables.startingRoom);
		mainMenu.setText(arrayListToString(GlobalVariables.mainMenuText));
		about.setText(arrayListToString(GlobalVariables.aboutMenuText));
	}
	
	public String arrayListToString(ArrayList<String> as) {
		String a = "";
		for (String s : as) {
			a += s + "\n";
		}
		return a;
	}

}
