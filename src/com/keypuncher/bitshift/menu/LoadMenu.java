package com.keypuncher.bitshift.menu;

import java.awt.event.*;
import java.io.File;

import com.keypuncher.bitshift.*;
import com.keypuncher.bitshift.console.Console;
import com.keypuncher.bitshift.interpreter.Interpreter;

public class LoadMenu extends Menu {

	private static String[] info = {
			"Enter a name to load a save file from the default location,",
			"Enter a name followed by a space and a directory to load a specific save file.",
			"", "Press escape to return to the main menu." };

	public LoadMenu() {
		super(info);
	}

	@Override
	public void input(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_ESCAPE)
			Game.setMenu(mainMenu);
		if (key == KeyEvent.VK_BACK_SPACE)
			Console.backspace();
		else if (key == KeyEvent.VK_ENTER) {
			String[] input = Console.getInput().split(" ");
			File saveFile;
			if (input.length < 2) {
				saveFile = new File(input[0] + ".sav");
			} else {
				saveFile = new File(input[0] + "\\" + input[1] + ".sav");
			}
			Console.newLine();
			if (!Game.load(saveFile)) {
				Console.println("Error loading game, (" + saveFile + ").",
						0XFF0000);
			}

			Console.clearInput();
		} else if (Character.isDefined(e.getKeyChar()))
			Console.input(e.getKeyChar());
	}

}
