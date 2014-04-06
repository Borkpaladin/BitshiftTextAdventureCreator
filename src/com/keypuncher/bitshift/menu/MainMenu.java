package com.keypuncher.bitshift.menu;

import java.awt.event.KeyEvent;
import java.util.Arrays;

import com.keypuncher.bitshift.*;
import com.keypuncher.bitshift.console.Console;

public class MainMenu extends Menu {

	public void printInfo() {
		for(String output : GlobalVariables.mainMenuText) {
			Console.println(output);
		}
		
		for(String output : Arrays.asList("", "1: New Game", "2: Load Game", "3: About", "4: Exit")) {
			Console.println(output);
		}
	}
	
	@Override
	public void input(KeyEvent e) {
		if (Character.isDigit(e.getKeyChar())) {
			int choice = Integer.parseInt(new String(Character.toString(e
					.getKeyChar())));
			switch (choice) {
			case 1:
				Game.newGame();
				break;
			case 2:
				Game.setMenu(loadMenu);
				break;
			case 3:
				Game.setMenu(aboutMenu);
				break;
			case 4:
				Main.exit();
				break;
			}
		}
	}

}
