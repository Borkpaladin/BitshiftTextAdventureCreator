package com.keypuncher.bitshift.menu;

import java.awt.event.*;
import java.util.Arrays;

import com.keypuncher.bitshift.*;
import com.keypuncher.bitshift.console.Console;

public class AboutMenu extends Menu {

	public void printInfo() {
		for (String output : GlobalVariables.aboutMenuText) {
			Console.println(output);
		}

		for (String output : Arrays.asList("", "Press any key to continue...")) {
			Console.println(output);
		}
	}

	@Override
	public void input(KeyEvent e) {
		Game.setMenu(mainMenu);
	}

}
